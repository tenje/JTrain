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
import java.util.Map.Entry;

/**
 * This class provides a skeletal implementation of the {@link AddressRegistry}
 * interface to minimize the effort required to implement this interface.
 * 
 * @author Jonas Tennié
 */
public class SimpleAddressRegistry implements AddressRegistry {

	/**
	 * The ids and their related addresses,
	 */
	protected final Map<Integer, Address> addressesById = new HashMap<>();
	private final Map<Integer, Address> unmodifiableAddressesById = Collections
			.unmodifiableMap(addressesById);

	/**
	 * Constructs a new {@link SimpleAddressRegistry} with no entries.
	 */
	public SimpleAddressRegistry() {}

	@Override
	public Map<Integer, Address> getRegistrations() {
		return unmodifiableAddressesById;
	}

	@Override
	public boolean defineAddress(int registrationId, Address address) {
		ParameterValidator.validateRegistrationId(registrationId);
		if (address == null) {
			return addressesById.remove(registrationId) != null;
		}
		else {
			return !address.equals(addressesById.put(registrationId, address));
		}
	}

	@Override
	public Address getAddress(int registrationId) {
		ParameterValidator.validateRegistrationId(registrationId);
		return addressesById.get(registrationId);
	}

	@Override
	public int getId(Address address) {
		for (Entry<Integer, Address> entry : addressesById.entrySet()) {
			if (entry.getValue().equals(address)) {
				return entry.getKey();
			}
		}
		return -1;
	}

}
