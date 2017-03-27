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
 * The function of a train. A train may have up to 29 functions (F0-F28). Some
 * functions can only be switched on/enabled (e.g. to play a short sound).
 * Others can be switched on/enabled and off/disabled (e.g. a light).
 * 
 * @author Jonas Tennié
 */
public interface TrainFunction extends Switchable {

	/**
	 * {@inheritDoc} Enables this function if this function cannot be disabled.
	 */
	@Override
	void toggle();

	/**
	 * Enables or disables this function. Does nothing if <code>switched</code> is
	 * <code>true</code> and the function is already enabled or if <code>switched</code>
	 * is <code>false</code> and the function is not enabled or cannot be disabled.
	 * 
	 * @param switched
	 *            <code>true</code> to enabled function, <code>false</code> to disable.
	 */
	@Override
	void setSwitched(boolean switched);

	/**
	 * Returns whether the function is enabled.
	 * 
	 * @return <code>true</code> if enabled, <code>false</code> if disabled, also
	 *         <code>false</code> if function cannot be disabled.
	 */
	@Override
	boolean isSwitched();

}
