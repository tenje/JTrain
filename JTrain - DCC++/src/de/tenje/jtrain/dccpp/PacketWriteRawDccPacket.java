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
package de.tenje.jtrain.dccpp;

/**
 * {@link Packet} to write a raw DCC packet containing 2-5 bytes.
 * 
 * @author Jonas Tennié
 */
public interface PacketWriteRawDccPacket extends Packet, Registrable {

	/**
	 * The type char of the <code>PacketWriteRawDccPacket</code>.
	 */
	public static final char TYPE_CHAR = 'M';

	/**
	 * Returns the byte at the given <code>index</code>. The returned byte has range
	 * (0-255).
	 * 
	 * @param index
	 *            The index of the byte to get.
	 * @return The byte at the given <code>index</code>.
	 * @throws IndexOutOfBoundsException
	 *             If no byte is defined for the given <code>index</code>.
	 */
	int getByte(int index);

}
