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
import java.util.Map;
import java.util.TreeMap;

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
import org.tenje.jtrain.dccpp.impl.PacketStationInfoImpl;

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
	private final Map<Integer, PacketTurnoutDefine> turnuts = new TreeMap<>();
	private final Map<Integer, PacketOutputPinDefine> outputPins = new TreeMap<>();
	private final Map<Integer, PacketSensorDefine> sensors = new TreeMap<>();

	/**
	 * Constructs a new {@link DccppStation} and starts a server socket for
	 * controllers and a server socket for accessories on the specified ports.
	 * Creates a new {@link PacketFactory} object.
	 * 
	 * @param controllerPort
	 *            The port for controllers.
	 * @param accessoryPort
	 *            The port for accessories.
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
		this(controllerPort, accessoryPort, null);
	}

	/**
	 * Constructs a new {@link DccppStation} and starts a server socket for
	 * controllers and a server socket for accessories on the specified ports.
	 * 
	 * @param controllerPort
	 *            The port for controllers.
	 * @param accessoryPort
	 *            The port for accessories.
	 * @param packetFactory
	 *            The packet factory to use. <code>null</code> to create a new
	 *            object.
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
	public DccppStation(int controllerPort, int accessoryPort,
			PacketFactory packetFactory) throws IOException {
		if (packetFactory == null) {
			packetFactory = new PacketFactoryImpl();
		}
		PacketFactoryImpl.regiserDefaultPackets(packetFactory);
		controllerSocket = new DccppServerSocket(controllerPort, packetFactory);
		try {
			accessorySocket = new DccppServerSocket(accessoryPort, packetFactory);
		}
		catch (IOException ex) { // Close controller socket on failure
			controllerSocket.close();
			throw ex;
		}
		controllerSocket.addPacketListener(this);
		accessorySocket.addPacketListener(this);
		accessorySocket.addSocketListener(new SocketListener() {
			@Override
			public void socketEvent(SocketEvent event) {
				try {
					for (PacketTurnoutDefine packet : turnuts.values()) {
						accessorySocket.sendPacket(packet, event.getBroker());
					}
					for (PacketOutputPinDefine packet : outputPins.values()) {
						accessorySocket.sendPacket(packet, event.getBroker());
					}
					for (PacketSensorDefine packet : sensors.values()) {
						accessorySocket.sendPacket(packet, event.getBroker());
					}
				}
				catch (IOException ex) {}
			}
		});
	}

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
				return; // Do not redirect
			}
			case PacketReadStationState.TYPE_CHAR: {
				// TODO Send power state (and other status packets?)
				receiver.sendPacket(new PacketStationInfoImpl("Java DCC++ Base Station"),
						sender);
				return; // Do not redirect
			}
			case PacketTurnout.TYPE_CHAR: {
				if (receiver == controllerSocket
						&& packet instanceof PacketTurnoutDefine) {
					PacketTurnoutDefine defPacket = (PacketTurnoutDefine) packet;
					turnuts.put(defPacket.getId(), defPacket);
				}
			}
			break;
			case PacketOutputPin.TYPE_CHAR: {
				if (receiver == controllerSocket
						&& packet instanceof PacketOutputPinDefine) {
					PacketOutputPinDefine defPacket = (PacketOutputPinDefine) packet;
					outputPins.put(defPacket.getId(), defPacket);
				}
			}
			break;
			case PacketSensor.TYPE_CHAR: {
				if (receiver == controllerSocket
						&& packet instanceof PacketSensorDefine) {
					PacketSensorDefine defPacket = (PacketSensorDefine) packet;
					sensors.put(defPacket.getId(), defPacket);
				}
			}
			break;
		}
		if (receiver == controllerSocket) {
			for (PacketBroker accessoryBroker : accessorySocket.getConnectedBrokers()) {
				try {
					accessorySocket.sendPacket(packet, accessoryBroker);
				}
				catch (IOException ex) {}
			}
		}
		else if (receiver == accessorySocket) {
			for (PacketBroker controllerBroker : controllerSocket.getConnectedBrokers()) {
				try {
					controllerSocket.sendPacket(packet, controllerBroker);
				}
				catch (IOException ex) {}
			}
		}
	}

}
