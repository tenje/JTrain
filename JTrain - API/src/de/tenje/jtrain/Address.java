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
 * An address to address/identify an object. Each address has a minimum value of
 * zero, the maximum value is specified by the implementation. Some values may
 * be forbidden. The address may be splited in a main address (first/right bits)
 * and sub address (last/left bits).<br>
 * {@link Object#equals(Object)} and {@link Object#hashCode()} have to be
 * overridden by the implementation to ensure that different objects can be used
 * as same key for collections (using hashing). {@link Object#equals(Object)}
 * only returns <code>true</code> if {@link #getAddress()} returns the same value.
 * 
 * @author Jonas Tennié
 */
public interface Address {

	/**
	 * Returns the whole address.
	 * 
	 * @return The whole address.
	 */
	int getAddress();

	/**
	 * Returns the main address. The main address is represented by the
	 * first/right bits of the whole address. The value is equal to the return
	 * value of {@link #getAddress()} if no sub address exists.
	 * 
	 * @return The main address.
	 */
	int getMainAddress();

	/**
	 * Returns the sub address. The main address is represented by the last/left
	 * bits of the whole address. The value is <code>0</code> if this address does
	 * not have a sub address.
	 * 
	 * @return The sub address.
	 */
	int getSubAddress();

}
