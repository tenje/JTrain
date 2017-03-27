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

import org.tenje.jtrain.Addressable;

/**
 * A {@link Packet} to write a one-bit configuration variable to the decoder
 * without verifying. The packet is sent to trains/locos on the main track. A
 * byte number in range (1-1024) and a bit number in range (0-7) defines the
 * memory location to write the variable to.
 * 
 * @author Jonas Tennié
 */
public interface PacketWriteConfigurationBitMainTrack extends Addressable, PacketWrite {

	/**
	 * The type char of the <code>PacketWriteConfigurationBitMainTrack</code>.
	 */
	public static final char TYPE_CHAR = 'b';

	/**
	 * Returns the bit location in the data byte returned by
	 * {@link #getVariableLocation()} of the value.
	 * 
	 * @return The bit location of the value.
	 */
	int getBit();

}
