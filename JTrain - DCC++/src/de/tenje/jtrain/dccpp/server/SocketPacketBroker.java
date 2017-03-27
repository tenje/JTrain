package de.tenje.jtrain.dccpp.server;

import java.net.InetAddress;

import de.tenje.jtrain.dccpp.Packet;
import de.tenje.jtrain.dccpp.PacketBroker;

/**
 * A {@link PacketBroker} that receives and sends {@link Packet}s via a socket.
 * 
 * @author Jonas Tennié
 */
public interface SocketPacketBroker extends PacketBroker {

	/**
	 * Returns the Inet Address of the broker.
	 * 
	 * @return The Inet Address of the broker.
	 */
	InetAddress getInetAddress();

	/**
	 * Returns the port used by the broker.
	 * 
	 * @return The port used by the broker.
	 */
	int getPort();

}
