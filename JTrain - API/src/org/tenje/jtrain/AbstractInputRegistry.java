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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * This class provides a skeletal implementation of the {@link InputRegistry}
 * interface to minimize the effort required to implement this interface.
 * 
 * @author Jonas Tennié
 * @param <T>
 *            The type of object handled by this registry.
 */
public abstract class AbstractInputRegistry<T extends Addressable> implements InputRegistry<T> {

	/**
	 * The addresses and their related objects.
	 */
	protected final Map<Address, T> objectsByAddress = new HashMap<>();
	private final Map<Address, T> unmodifiableObjectsByAddress = Collections.unmodifiableMap(objectsByAddress);

	/**
	 * Constructs a new {@link AbstractInputRegistry} with no entries.
	 */
	public AbstractInputRegistry() {}

	@Override
	public Map<Address, T> getEntries() {
		return unmodifiableObjectsByAddress;
	}

	@Override
	public T register(T object) {
		Objects.requireNonNull(object, "object");
		return objectsByAddress.put(object.getAddress(), object);
	}

	@Override
	public boolean remove(T object) {
		Objects.requireNonNull(object, "object");
		T toRemove = objectsByAddress.get(object.getAddress());
		if (toRemove != null && toRemove.equals(object)) {
			objectsByAddress.remove(object.getAddress());
			return true;
		}
		return false;
	}

}
