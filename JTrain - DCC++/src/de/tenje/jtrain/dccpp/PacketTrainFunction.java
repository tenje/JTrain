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

import java.util.Collections;
import java.util.Map;

import org.tenje.jtrain.Addressable;
import org.tenje.jtrain.LongTrainAddress;

/**
 * A {@link Packet} to enable or disable a train/loco function. The packet data
 * consists of one or two bytes.
 * <table>
 * <tr>
 * <td>Type char:</td>
 * <td>'f'</td>
 * </tr>
 * <tr>
 * <td valign="top">Packet format:</td>
 * <td ><i>&lt;f Address Byte0 Byte1&gt;</i><br>
 * <i>Address:</i> Train decoder address in range (1-10293)<br>
 * <i>Byte0:</i> Fist data byte as described below<br>
 * <i>Byte1:</i> Second (optinal) data byte as described below</td>
 * </tr>
 * <tr>
 * <td>Return packet:</td>
 * <td>none</td>
 * </tr>
 * </table>
 * In the following table disabled means <code>0</code> and enabled means
 * <code>1</code>:<br>
 * <table>
 * <tr>
 * <th style="text-align: left;">Functions</th>
 * <th style="text-align: left;">Byte 1</th>
 * <th style="text-align: left;">Byte 2</th>
 * </tr>
 * <tr>
 * <td>F0-F4</td>
 * <td>128 + F1*1 + F2*2 + F3*4 + F4*8 + F0*16</td>
 * <td>omitted</td>
 * </tr>
 * <tr>
 * <td>F5-F8</td>
 * <td>176 + F5*1 + F6*2 + F7*4 + F8*8</td>
 * <td>omitted</td>
 * </tr>
 * <tr>
 * <td>F9-F12</td>
 * <td>160 + F9*1 +F10*2 + F11*4 + F12*8</td>
 * <td>omitted</td>
 * </tr>
 * <tr>
 * <td>F13-F20</td>
 * <td>222</td>
 * <td>F13*1 + F14*2 + F15*4 + F16*8 + F17*16 + F18*32 + F19*64 + F20*128</td>
 * </tr>
 * <tr>
 * <td>F21-F28</td>
 * <td>223</td>
 * <td>F21*1 + F22*2 + F23*4 + F24*8 + F25*16 + F26*32 + F27*64 + F28*128</td>
 * </tr>
 * </table>
 * 
 * @author Jonas Tennié
 */
public interface PacketTrainFunction extends Addressable, Packet {

	/**
	 * The type char of the <code>PacketFunction</code>.
	 */
	public static final char TYPE_CHAR = 'f';

	@Override
	LongTrainAddress getAddress();

	/**
	 * Returns the first function byte of the packet.
	 * 
	 * @return The first byte of the packet.
	 */
	int getFirstByte();

	/**
	 * Returns the second byte of the packet.
	 * 
	 * @return The second byte of the packet. {@code -1}, if not
	 *         defined/omitted.
	 */
	int getSecondByte();

	/**
	 * Returns if the function is enabled or disabled.
	 * 
	 * @param functionId
	 *            The id of the function to check.
	 * @return <code>true</code> if enabled, else <code>false</code>.
	 * @throws IllegalStateException
	 *             Thrown if this packet does not hold any data about the
	 *             function with the passed <code>functionId</code>.
	 */
	boolean isEnabled(int functionId);

	/**
	 * Returns an unmodifiable map containing all function values contained in
	 * the packet data. The map's key is the function id; the value is the
	 * function value.
	 * 
	 * @return An unmodifiable map containing all function values contained in
	 *         the packet data.
	 * @see Collections#unmodifiableMap(Map)
	 */
	Map<Integer, Boolean> getFunctionValues();

}
