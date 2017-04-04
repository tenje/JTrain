package org.tenje.jtrain.dccpp.server;

import org.tenje.jtrain.dccpp.PacketBroker;

/**
 * An event that happens to a socket.
 * 
 * @author Jonas Tennié
 */
public interface SocketEvent {

	/**
	 * Returns the type of this event.
	 * 
	 * @return The type of this event.
	 */
	SocketEventType getType();

	/**
	 * Returns the involved packet broker.
	 * 
	 * @return The involved packet broker, <code>null</code> if no broker is
	 *         involved.
	 */
	PacketBroker getBroker();

}
