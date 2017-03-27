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

import java.util.Map;

/**
 * A registry for a specific type of objects which input/receive data from other
 * components. The objects are stored with an {@link Address} as key. An object
 * can only be registered with one address and the same address can only refer
 * to one registered object.
 * 
 * @author Jonas Tennié
 * @param <T>
 *            The type of object handled by this registry.
 */
public interface InputRegistry<T extends Addressable> {

	/**
	 * Returns an unmodifiable map of all entries in this registry. The map
	 * always represents the current state of the registry. The map key is the
	 * address of the registered objects, the value is the object registered for
	 * the key address.
	 * 
	 * @return Am unmodifiable map of all entries in this registry.
	 */
	Map<Address, T> getEntries();

	/**
	 * Registers an object for its {@link Address}. The address returned by
	 * {@link Addressable#getAddress()} is used as key.
	 * 
	 * @param object
	 *            The object to register.
	 * @return The previous object associated with the address, or <code>null</code>
	 *         if no object was registered for the address.
	 */
	T register(T object);

	/**
	 * Removes an object using {@link Object#equals(Object)} to find the object.
	 * 
	 * @param object
	 *            The object to remove.
	 * @return <code>true</code> if registry changed as a result of the call (object
	 *         removed), <code>false</code> if nothing changed (object was not
	 *         registered).
	 * @throws NullPointerException
	 *             Thrown if <code>object</code> is <code>null</code>.
	 */
	boolean remove(T object);

}
