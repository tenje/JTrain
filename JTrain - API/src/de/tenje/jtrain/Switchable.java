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
package de.tenje.jtrain;

/**
 * A switchable can have one of the two states: Not switched or switched. These
 * two states are specified by the implementation.
 * 
 * @author Jonas Tennié
 */
public interface Switchable {

	/**
	 * Toggles the switch state. Sets to switched if not switched, sets to not
	 * switched if switched.
	 */
	void toggle();

	/**
	 * Sets the switch state.
	 * 
	 * @param switched
	 *            <code>true</code> for switched, <code>false</code> for not switched.
	 */
	void setSwitched(boolean switched);

	/**
	 * Returns the switch state.
	 * 
	 * @return <code>true</code> for switched, <code>false</code> for not switched.
	 */
	boolean isSwitched();

}
