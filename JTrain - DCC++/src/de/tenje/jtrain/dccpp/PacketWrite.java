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
 * A super interface for all {@link Packet}s to write to a decoder.
 * 
 * @author Jonas Tennié
 */
public interface PacketWrite extends Packet {

	/**
	 * Returns the memory location of the variable to write.
	 * 
	 * @return The memory location of the variable to write.
	 */
	int getVariableLocation();

	/**
	 * Returns the value of the variable to write in range (0-255) when writing
	 * a byte or 0 or 1 when writing a bit.
	 * 
	 * @return The value of the variable to write.
	 */
	int getValue();

}
