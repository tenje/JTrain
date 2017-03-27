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
