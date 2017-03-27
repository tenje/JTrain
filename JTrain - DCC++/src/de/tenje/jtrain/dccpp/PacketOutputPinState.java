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
 * {@link Packet} to return an output pin's state.
 * 
 * @author Jonas Tennié
 */
public interface PacketOutputPinState extends RegistrationIdHolder, Packet {

	/**
	 * The type char of the {@link PacketOutputPinState}.
	 */
	public static final char TYPE_CHAR = 'Y';

	/**
	 * Returns the state of the output pin.
	 * 
	 * @return The state of the output pin. <code>true</code> for HIGH, <code>false</code>
	 *         for LOW.
	 */
	boolean getState();

}
