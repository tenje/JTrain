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

import org.tenje.jtrain.Addressable;
import org.tenje.jtrain.OutputPinAddress;

/**
 * A {@link PacketOutputPin} to define an output pin. Default pin state and pin
 * state after power-up are defined.
 * 
 * <table>
 * <tr>
 * <td>Type char:</td>
 * <td>'Z'</td>
 * </tr>
 * <tr>
 * <td valign="top">Format:</td>
 * <td><i>&lt;Z ID Pin Flags&gt;</i><br>
 * Flag bit 0: {@link #isInvertedOperation()}, 0&#8793;<code>false</code>,
 * 1&#8793;<code>true</code><br>
 * Flag bit 1: {@link #isResetOnPowerUp()}, 0&#8793;<code>false</code>,
 * 1&#8793;<code>true</code><br>
 * Flag bit 2: {@link #getDefaultPinState()},
 * 0&#8793;<code>false</code>&#8793;LOW,
 * 1&#8793;<code>true</code>&#8793;HIGH</td>
 * </tr>
 * <tr>
 * <td valign="top">Return packets:</td>
 * <td>{@link PacketOperationSuccessful} on success,<br>
 * {@link PacketOperationFailed} on failure</td>
 * </tr>
 * </table>
 * 
 * @author Jonas Tennié
 */
public interface PacketOutputPinDefine
		extends Addressable, RegistrationIdHolder, PacketOutputPin {

	/**
	 * Returns the output pin number to define.
	 * 
	 * @return The output pin number to define.
	 */
	@Override
	OutputPinAddress getAddress();

	/**
	 * Returns whether the pin is defined as a forward (<code>false</code>,
	 * ACTIVE&#8793;HIGH / INACTIVE&#8793;LOW) or inverted (<code>true</code>,
	 * ACTIVE&#8793;LOW / INACTIVE&#8793;HIGH) operation.
	 * 
	 * @return <code>true</code> if inverted operation, <code>false</code> if
	 *         forward operation.
	 */
	boolean isInvertedOperation();

	/**
	 * Returns whether the pin state is reset to {@link #getDefaultPinState()}
	 * or pin state before power-down after power-up.
	 * 
	 * @return <code>true</code> if reset after power-up, <code>false</code> if
	 *         pin state after power-up is restored.
	 */
	boolean isResetOnPowerUp();

	/**
	 * Returns the default state of the pin. The state after power-up or after
	 * the first initialization.
	 * 
	 * @return <code>true</code> if pin is HIGH by default, <code>false</code>
	 *         if pin is LOW by default.
	 */
	boolean getDefaultPinState();

	/**
	 * Returns the flag value.
	 * 
	 * @return The flag value.
	 */
	int getFlags();

}
