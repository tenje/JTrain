package de.tenje.jtrain.dccpp;

import java.io.IOException;

/**
 * A listener to listen to incoming packets.
 * 
 * @author Jonas Tennié
 */
public interface PacketListener {

	/**
	 * Called when a packet was received.
	 * 
	 * @param packet
	 *            The received packet.
	 * @param sender
	 *            An object that represents the packet sender.
	 * @param receiver
	 *            The local broker that received the packet.
	 * @throws IOException
	 *             Thrown if an I/O error occurs while handling the packet.
	 */
	void packetReceived(Packet packet, PacketBroker sender, LocalPacketBroker receiver) throws IOException;

}
