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
package org.tenje.jtrain;

/**
 * An electrical output pin of the machine the program is running (e.g. Arduino
 * or Raspberry Pi output pins).
 * 
 * @author Jonas Tennié
 */
public interface OutputPin extends Addressable, Switchable {

	@Override
	OutputPinAddress getAddress();

	/**
	 * Sets the state of the pin.
	 * 
	 * @param state
	 *            The state to set for the output pin. <code>true</code> for HIGH,
	 *            <code>false</code> for LOW.
	 */
	@Override
	void setSwitched(boolean state);

	/**
	 * Returns the state of the pin.
	 * 
	 * @return The state of the output pin. <code>true</code> for HIGH, <code>false</code>
	 *         for LOW.
	 */
	@Override
	boolean isSwitched();

}
