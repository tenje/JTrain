package org.tenje.jtrain.dccpp.server;

/**
 * The type of a {@link SocketEvent}.
 * 
 * @author Jonas Tennié
 */
public enum SocketEventType {

	/**
	 * A broker connected.
	 */
	BROKER_CONNECT,

	/**
	 * A broker disconnected.
	 */
	BROKER_DISCONNECT,

	/**
	 * Socket was closed.
	 */
	SOCKET_CLOSE;

}
