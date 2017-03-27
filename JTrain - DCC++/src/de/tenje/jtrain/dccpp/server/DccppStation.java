package de.tenje.jtrain.dccpp.server;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import de.tenje.jtrain.dccpp.LocalPacketBroker;
import de.tenje.jtrain.dccpp.Packet;
import de.tenje.jtrain.dccpp.PacketBroker;
import de.tenje.jtrain.dccpp.PacketBuilder;
import de.tenje.jtrain.dccpp.PacketFactory;
import de.tenje.jtrain.dccpp.PacketListener;
import de.tenje.jtrain.dccpp.PacketReadCurrent;
import de.tenje.jtrain.dccpp.PacketReadStationState;
import de.tenje.jtrain.dccpp.impl.AbstractPacket;
import de.tenje.jtrain.dccpp.impl.PacketReadCurrentImpl;
import de.tenje.jtrain.dccpp.impl.PacketReadStationStateImpl;
import de.tenje.jtrain.dccpp.impl.PacketStationInfoImpl;

/**
 * A {@link DccppStation} is the connection between a DCC++ controller (e.g.
 * <a href="http://wiki.rocrail.net">Rocrail</a>, ...) and DCC++ listening
 * accessories (e.g. trains, turnouts, ...). {@link Packet}s received from a
 * controller are forwarded to each connected accessory. {@link Packet}s
 * received from an accessory are forwarded to each connected controller. The
 * packet data is not validated.
 * 
 * @author Jonas Tenni�
 */
public class DccppStation implements PacketListener {

	private static final PacketFactory PACKET_FACTORY = new PacketFactory() {

		@Override // Not required
		public <P extends Packet> void registerBuilder(PacketBuilder<P> builder,
				Class<P> clazz, char typeChar) {}

		@Override
		public Packet buildPacket(final char typeChar, List<String> parameters) {
			if (typeChar == 'c') {
				return new PacketReadCurrentImpl();
			}
			else if (typeChar == 's') {
				return new PacketReadStationStateImpl();
			}
			return new AbstractPacket(typeChar, parameters) {};
		}

		@Override // Not required
		public <P extends Packet> P buildPacket(Class<P> packetClass,
				List<String> parameters) {
			return null;
		}
	};

	private final DccppServerSocket controllerSocket, accessorySocket;

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
		controllerSocket.addPacketListener(this);
		accessorySocket.addPacketListener(this);
		controllerSocket.setPacketFactory(PACKET_FACTORY);
		accessorySocket.setPacketFactory(PACKET_FACTORY);
	}

	@Override
	public void packetReceived(Packet packet, PacketBroker sender,
			LocalPacketBroker receiver) throws IOException {
		System.out.println(packet);
		if (packet.getTypeChar() == PacketReadCurrent.TYPE_CHAR) {
			// TODO Fix
			receiver.sendPacket(new AbstractPacket('a', Arrays.asList("0")) {}, sender);
		}
		else if (packet.getTypeChar() == PacketReadStationState.TYPE_CHAR) {
			// TODO Send power state (and other status packets?)
			receiver.sendPacket(new PacketStationInfoImpl("Java DCC++ Base Station"),
					sender);
		}
		else {
			if (receiver == controllerSocket) {
				for (PacketBroker accessoryBroker : accessorySocket
						.getConnectedBrokers()) {
					accessorySocket.sendPacket(packet, accessoryBroker);
				}
			}
			else if (receiver == accessorySocket) {
				for (PacketBroker controllerBroker : controllerSocket
						.getConnectedBrokers()) {
					controllerSocket.sendPacket(packet, controllerBroker);
				}
			}
		}
	}

}
