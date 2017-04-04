package org.tenje.jtrain.dccpp.server;

/**
 * A listener to listen to {@link SocketEvent}s.
 * 
 * @author Jonas Tenni�
 */
public interface SocketListener {

	/**
	 * Called on {@link SocketEvent}.
	 * 
	 * @param event
	 *            The event.
	 */
	void socketEvent(SocketEvent event);

}
