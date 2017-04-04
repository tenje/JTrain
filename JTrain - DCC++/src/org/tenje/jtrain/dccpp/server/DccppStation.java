/*******************************************************************************
 * Copyright (c): Jonas Tennié 2017
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Lesser Public License for more
 * details.
 * You should have received a copy of the GNU General Lesser Public License
 * along with this program. If not, see
 * <http://www.gnu.org/licenses/lgpl-3.0.html>.
 *******************************************************************************/
package org.tenje.jtrain.dccpp.server;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.tenje.jtrain.AccessoryDecoderAddress;
import org.tenje.jtrain.OutputPinAddress;
import org.tenje.jtrain.dccpp.LocalPacketBroker;
import org.tenje.jtrain.dccpp.Packet;
import org.tenje.jtrain.dccpp.PacketBroker;
import org.tenje.jtrain.dccpp.PacketFactory;
import org.tenje.jtrain.dccpp.PacketListener;
import org.tenje.jtrain.dccpp.PacketOutputPin;
import org.tenje.jtrain.dccpp.PacketOutputPinDefine;
import org.tenje.jtrain.dccpp.PacketReadCurrent;
import org.tenje.jtrain.dccpp.PacketReadStationState;
import org.tenje.jtrain.dccpp.PacketSensor;
import org.tenje.jtrain.dccpp.PacketSensorDefine;
import org.tenje.jtrain.dccpp.PacketTurnout;
import org.tenje.jtrain.dccpp.PacketTurnoutDefine;
import org.tenje.jtrain.dccpp.impl.AbstractPacket;
import org.tenje.jtrain.dccpp.impl.PacketFactoryImpl;
import org.tenje.jtrain.dccpp.impl.PacketOutputPinDefineImpl;
import org.tenje.jtrain.dccpp.impl.PacketSensorDefineImpl;
import org.tenje.jtrain.dccpp.impl.PacketStationInfoImpl;
import org.tenje.jtrain.dccpp.impl.PacketTurnoutDefineImpl;

/**
 * A {@link DccppStation} is the connection between a DCC++ controller (e.g.
 * <a href="http://wiki.rocrail.net">Rocrail</a>, ...) and DCC++ listening
 * accessories (e.g. trains, turnouts, ...). {@link Packet}s received from a
 * controller are forwarded to each connected accessory. {@link Packet}s
 * received from an accessory are forwarded to each connected controller. The
 * packet data is not validated.<br />
 * Data of {@link PacketTurnoutDefine}, {@link PacketOutputPinDefine} and
 * {@link PacketSensorDefine} received from controller brokers are stored and
 * sent to accessory brokers after connecting to the station.
 * 
 * @author Jonas Tennié
 */
public class DccppStation implements PacketListener {

	private final DccppServerSocket controllerSocket, accessorySocket;
	private final PacketFactory packetFactory = new PacketFactoryImpl();
	private final Set<TurnoutEntry> turnuts = new HashSet<>();
	private final Set<OutputPinEntry> outputPins = new HashSet<>();
	private final Set<SensorEntry> sensors = new HashSet<>();

	/**
	 * Constructs a new {@link DccppStation} and starts a server socket for
	 * controllers and a server socket for accessories on the specified ports.
	 * 
	 * @param controllerPort
	 *            The port for controllers.
	 * @param accessoryPort
	 *            The port for accessories
	 * @throws IOException
	 *             Thrown if an I/O error occurs when opening one of the
	 *             sockets.
	 * @throws IllegalArgumentException
	 *             Thrown if one of the port parameters is outside the specified
	 *             range of valid port values, which is between 0 and 65535,
	 *             inclusive.
	 * @throws SecurityException
	 *             Thrown if a security manager exists and its
	 *             {@code SecurityManager#checkConnect(String, int)} method
	 *             doesn't allow the operation.
	 */
	public DccppStation(int controllerPort, int accessoryPort) throws IOException {
		controllerSocket = new DccppServerSocket(controllerPort);
		try {
			accessorySocket = new DccppServerSocket(accessoryPort);
		}
		catch (IOException ex) { // Close controller socket on failure
			controllerSocket.close();
			throw ex;
		}
		PacketFactoryImpl.regiserDefaultPackets(packetFactory);
		controllerSocket.addPacketListener(this);
		accessorySocket.addPacketListener(this);
		controllerSocket.setPacketFactory(packetFactory);
		accessorySocket.setPacketFactory(packetFactory);
		accessorySocket.addSocketListener(new SocketListener() {
			@Override
			public void socketEvent(SocketEvent event) {
				System.out.println("Con");
				try {
					System.out.println(sensors);
					for (TurnoutEntry entry : turnuts) {
						accessorySocket.sendPacket(
								new PacketTurnoutDefineImpl(entry.id, entry.address),
								event.getBroker());
					}
					for (OutputPinEntry entry : outputPins) {
						accessorySocket.sendPacket(new PacketOutputPinDefineImpl(entry.id,
								entry.address, entry.flag), event.getBroker());
					}
					for (SensorEntry entry : sensors) {
						accessorySocket.sendPacket(new PacketSensorDefineImpl(entry.id,
								entry.address, entry.pullUp), event.getBroker());
					}
				}
				catch (IOException ex) {}
			}
		});
	}

	@SuppressWarnings("deprecation")
	@Override
	public void packetReceived(Packet packet, PacketBroker sender,
			LocalPacketBroker receiver) throws IOException {
		System.out.println("Packet received: " + packet.getTypeChar() + ": "
				+ packet.getRawParameters());
		switch (packet.getTypeChar()) {
			case PacketReadCurrent.TYPE_CHAR: {
				// TODO Fix
				receiver.sendPacket(new AbstractPacket('a', Arrays.asList("0")) {},
						sender);
				return;
			}
			case PacketReadStationState.TYPE_CHAR: {
				// TODO Send power state (and other status packets?)
				receiver.sendPacket(new PacketStationInfoImpl("Java DCC++ Base Station"),
						sender);
				return;
			}
			case PacketTurnout.TYPE_CHAR: {
				if (receiver == controllerSocket
						&& packet instanceof PacketTurnoutDefine) {
					PacketTurnoutDefine defPacket = (PacketTurnoutDefine) packet;
					TurnoutEntry entry = new TurnoutEntry();
					entry.id = defPacket.getId();
					entry.address = defPacket.getAddress();
					turnuts.add(entry);
				}
			}
			break;
			case PacketOutputPin.TYPE_CHAR: {
				if (receiver == controllerSocket
						&& packet instanceof PacketOutputPinDefine) {
					PacketOutputPinDefine defPacket = (PacketOutputPinDefine) packet;
					OutputPinEntry entry = new OutputPinEntry();
					entry.id = defPacket.getId();
					entry.address = defPacket.getAddress();
					entry.flag = defPacket.getFlags();
					outputPins.add(entry);
				}
			}
			break;
			case PacketSensor.TYPE_CHAR: {
				if (receiver == controllerSocket) {
					if (packet instanceof PacketSensorDefine) {
						PacketSensorDefine defPacket = (PacketSensorDefine) packet;
						SensorEntry entry = new SensorEntry();
						entry.id = defPacket.getId();
						entry.address = defPacket.getAddress();
						entry.pullUp = defPacket.usePullUp();
						sensors.add(entry);
					}
				}
			}
			break;
		}
		if (receiver == controllerSocket) {
			for (PacketBroker accessoryBroker : accessorySocket.getConnectedBrokers()) {
				accessorySocket.sendPacket(packet, accessoryBroker);
			}
		}
		else if (receiver == accessorySocket) {
			for (PacketBroker controllerBroker : controllerSocket.getConnectedBrokers()) {
				controllerSocket.sendPacket(packet, controllerBroker);
			}
		}
	}

	private static class Entry {
		int id;

		@Override
		public int hashCode() {
			return id;
		}

		// No null check or type check required
		@Override
		public boolean equals(Object obj) {
			return id == ((Entry) obj).id;
		}
	}

	private static class TurnoutEntry extends Entry {
		AccessoryDecoderAddress address;
	}

	private static class OutputPinEntry extends Entry {
		OutputPinAddress address;
		int flag;
	}

	private static class SensorEntry extends Entry {
		AccessoryDecoderAddress address;
		boolean pullUp;
	}

}
