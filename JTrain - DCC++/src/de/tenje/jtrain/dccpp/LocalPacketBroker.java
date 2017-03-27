package de.tenje.jtrain.dccpp;

import java.io.IOException;

/**
 * Represents a local {@link PacketBroker} which can send {@link Packet}s to
 * other {@link PacketBroker}s via the current runtime.
 * 
 * @author Jonas Tennié
 */
public interface LocalPacketBroker extends PacketBroker {

	/**
	 * Sends a {@link Packet} to an other {@link PacketBroker}. A new connection
	 * may be created. The packet's type char and parameters are ASCII encoded
	 * (each character is one byte).
	 * 
	 * @param packet
	 *            The packet to send.
	 * @param receiver
	 *            The receiver of the packet. May be <code>null</code> if this broker
	 *            is only connected to one other broker.
	 * @throws IOException
	 *             Thrown if an I/O error occurs while sending the packet.
	 * @throws NullPointerException
	 *             Thrown if <code>packet</code> or <code>receiver</code> is <code>null</code>,
	 *             but required.
	 * @throws ClassCastException
	 *             Thrown if this broker cannot send packets to the specified
	 *             <code>receiver</code> as the receiver's implementation is unknown.
	 * @throws UnsupportedOperationException
	 *             Thrown if this broker cannot send data to the
	 *             <code>receiver</code> as this broker is only connected to an other
	 *             broker and does not support multiple connections.
	 */
	void sendPacket(Packet packet, PacketBroker receiver) throws IOException;

}
