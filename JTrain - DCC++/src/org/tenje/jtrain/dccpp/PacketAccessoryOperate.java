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

import org.tenje.jtrain.AccessoryDecoderAddress;
import org.tenje.jtrain.Addressable;

/**
 * A {@link Packet} to turn an accessory (stationary) decoder's output on or
 * off.
 * <table>
 * <tr>
 * <td>Type char:</td>
 * <td>'a'</td>
 * </tr>
 * <tr>
 * <td valign="top">Packet format:</td>
 * <td ><i>&lt;a Address Sub-Address Activate&gt;</i><br>
 * <i>Address:</i> Decoder address in range (0-511)<br>
 * <i>Sub-Address:</i> Decoder Sub-Address in range (0-3)<br>
 * <i>Activate:</i> Whether decoder output should be activated (<i>1</i>) or
 * deactivated (<i>0</i>)</td>
 * </tr>
 * <tr>
 * <td>Return packet:</td>
 * <td>none</td>
 * </tr>
 * </table>
 * 
 * @author Jonas Tennié
 */
public interface PacketAccessoryOperate extends Addressable, Packet {

	/**
	 * The type char of the <code>PacketAccessory</code>.
	 */
	public static final char TYPE_CHAR = 'a';

	@Override
	AccessoryDecoderAddress getAddress();

	/**
	 * Returns whether the decoder should turn on (active) or off (inactive)
	 * 
	 * @return <code>true</code> to activate decoder output, <code>false</code> to
	 *         deactivate decoder output.
	 */
	boolean isActive();

}
