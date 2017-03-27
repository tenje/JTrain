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
 * An object that holds two callback variables called <i>num</i> and <i>sub</i>.
 * Both have range (0-32767).
 * 
 * @author Jonas Tennié
 */
public interface CallbackData {

	/**
	 * Returns the value of <i>num</i>.
	 * 
	 * @return The value of <i>num</i>.
	 */
	int getCallbackNumber();

	/**
	 * Returns the value of <i>sub</i>.
	 * 
	 * @return The value of <i>sub</i>.
	 */
	int getCallbackSum();

}
