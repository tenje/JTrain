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
 * A {@link AddressRegistry} is a registry that holds {@link Address}es related
 * to a registration id.
 * 
 * @author Jonas Tennié
 */
public interface AddressRegistry {

	/**
	 * Returns an unmodifiable map of all registration id/address pairs in this
	 * registry. The map always represents the current state of the registry.
	 * The map key is the registration id, the value is is the address related
	 * to the key registration id.
	 * 
	 * @return Am unmodifiable map of all registration id/address pairs in this
	 *         registry.
	 */
	Map<Integer, Address> getRegistrations();

	/**
	 * Defines an {@link Address} for a specified registration id.
	 * 
	 * @param registrationId
	 *            The registration id.
	 * @param address
	 *            The address. <code>null</code> to clear address for
	 *            <code>registrationId</code>.
	 * @return <code>true</code> if registry changed as a result of the call
	 *         (e.g. re/new defined id/address), <code>false</code> if nothing
	 *         changed (e.g. id/address pair already registered or id not
	 *         defined, but tried to remove).
	 * @throws IllegalArgumentException
	 *             Thrown if <code>registrationId</code> does not lay in range
	 *             (0-32767).
	 */
	boolean defineAddress(int registrationId, Address address);

	/**
	 * Returns the related address to a specified registration id.
	 * 
	 * @param registrationId
	 *            The registration id.
	 * @return The address related to the <code>registrationId</code>.
	 * @throws IllegalArgumentException
	 *             Thrown if <code>registrationId</code> does not lay in range
	 *             (0-32767).
	 */
	Address getAddress(int registrationId);

	/**
	 * Returns the related registration id to a specified {@link Address}.
	 * 
	 * @param address
	 *            The address.
	 * @return The registration id related to the <code>address</code>.
	 *         <code>-1</code> if address is not registered.
	 * @throws NullPointerException
	 *             Thrown if <code>address</code> is <code>null</code>.
	 */
	int getId(Address address);

}
