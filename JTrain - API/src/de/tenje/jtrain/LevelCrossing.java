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
 * A level crossing. The representation of a level crossing is as simple as
 * possible to make it universally usable.
 * 
 * @author Jonas Tennié
 */
public interface LevelCrossing extends Switchable {

	/**
	 * Returns whether the barriers are closed or not.
	 * 
	 * @return <code>true</code> if closed or closing, <code>false</code> if open or
	 *         opening.
	 */
	@Override
	boolean isSwitched();

	/**
	 * Returns whether the barriers are closing. If the barriers are closing,
	 * they are not fully open and not fully closed. If this method returns
	 * <code>true</code>, then {@link #isSwitched()} returns <code>true</code>, too. If
	 * this level crossing has no barriers, then this method will always return
	 * <code>false</code>.
	 * 
	 * @return <code>true</code> if closing, else <code>false</code>.
	 */
	boolean isClosing();

	/**
	 * Returns whether the barriers are opening. If the barriers are opening,
	 * they are not fully open and not fully closed. If this method returns
	 * <code>true</code>, then {@link #isSwitched()} returns <code>false</code>. If this
	 * level crossing has no barriers, then this method will always return
	 * <code>false</code>.
	 * 
	 * @return <code>true</code> if closing, else <code>false</code>.
	 */
	boolean isOpening();

	/**
	 * Closes or opens the barriers.<br>
	 * <b>Close:</b> Starts a barrier closing. The warning lights are turned on
	 * and (after a time) the barriers close.<br>
	 * <b>Open:</b> Starts barrier opening. The barriers open and the warning
	 * lights are turned off.
	 * 
	 * @param switched
	 *            <code>true</code> to close barriers, <code>false</code> to open.
	 */
	@Override
	void setSwitched(boolean switched);

}
