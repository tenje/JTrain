/*******************************************************************************
 * Copyright (c): Jonas Tenni� 2017
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

/**
 * An train {@link Address} in range (0-127) with no sub address.
 * 
 * @author Jonas Tenni�
 */
public class ShortTrainAddress implements Address {

	private final int address;

	/**
	 * Constructs a new {@link ShortTrainAddress} with the specified address
	 * value.
	 * 
	 * @param address
	 *            The address value.
	 * @throws IllegalArgumentException
	 *             Thrown if <code>address</code> does not lay in range (0-127).
	 */
	public ShortTrainAddress(int address) {
		if (address < 0 || address > 127) {
			throw new IllegalArgumentException(
					"addres value out of valid range: " + address);
		}
		this.address = address;
	}

	@Override
	public int hashCode() {
		return address;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof ShortTrainAddress)) {
			return false;
		}
		ShortTrainAddress other = (ShortTrainAddress) obj;
		if (address != other.address) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return getClass().getName() + "[mainAddress=" + getMainAddress()
				+ ", subAddress=0]";
	}

	@Override
	public int getAddress() {
		return address;
	}

	@Override
	public int getMainAddress() {
		return getAddress();
	}

	@Override
	public int getSubAddress() {
		return 0;
	}

}
