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
package org.tenje.jtrain.dccpp;

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
