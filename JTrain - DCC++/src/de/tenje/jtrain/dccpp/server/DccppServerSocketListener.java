package de.tenje.jtrain.dccpp.server;

import de.tenje.jtrain.dccpp.LocalPacketBroker;
import de.tenje.jtrain.dccpp.PacketBroker;

/**
 * Listens to incoming connections to a {@link DccppServerSocket}.
 * 
 * @author Jonas Tennié
 */
public interface DccppServerSocketListener {

	/**
	 * Called when a client connected to the server socket.
	 * 
	 * @param client
	 *            The connected client.
	 * @param server
	 *            The server.
	 */
	void connectionAccepted(PacketBroker client, LocalPacketBroker server);

}
