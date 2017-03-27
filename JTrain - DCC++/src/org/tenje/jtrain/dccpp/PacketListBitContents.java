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

/**
 * Packet to list the packet contents of the main operations track registers and
 * the programming track registers. For diagnostic and testing use only.
 * 
 * @author Jonas Tennié
 */
public interface PacketListBitContents extends Packet {

	/**
	 * The type char of the <code>PacketListBitContents</code>.
	 */
	public static final char TYPE_CHAR = 'L';

}
