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
