/*******************************************************************************
 * Copyright (c): Jonas Tenni� 2017
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
 * {@link PacketOutputPin} to set the state of an output pin.
 * 
 * <table>
 * <tr>
 * <td>Type char:</td>
 * <td>'Z'</td>
 * </tr>
 * <tr>
 * <td valign="top">Format:</td>
 * <td><i>&lt;Z ID State&gt;</i></td>
 * </tr>
 * <tr>
 * <td valign="top">Return packets:</td>
 * <td>{@link PacketOutputPinState} on success,<br>
 * {@link PacketOperationFailed} on failure</td>
 * </tr>
 * </table>
 * 
 * @author Jonas Tenni�
 */
public interface PacketOutputPinSetState extends RegistrationIdHolder, PacketOutputPin {

	/**
	 * Returns the state to set for the output pin.
	 * 
	 * @return The state to set for the output pin. <code>true</code> for HIGH,
	 *         <code>false</code> for LOW.
	 */
	boolean getState();

}
