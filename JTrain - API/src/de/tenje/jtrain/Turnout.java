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
 * A railroad turnout. A turnout can have two states: Thrown or straight.
 * 
 * @author Jonas Tennié
 */
public interface Turnout extends AddressableSwitchable {

	@Override
	AccessoryDecoderAddress getAddress();

	/**
	 * Sets the throw state of the turnout.
	 * 
	 * @param switched
	 *            The throw state to set. <code>true</code> to throw,
	 *            <code>false</code> for straight.
	 */
	@Override
	void setSwitched(boolean switched);

	/**
	 * Returns whether the turnout is thrown or not.
	 * 
	 * @return <code>true</code> if thrown, else <code>false</code>.
	 */
	@Override
	boolean isSwitched();

}
