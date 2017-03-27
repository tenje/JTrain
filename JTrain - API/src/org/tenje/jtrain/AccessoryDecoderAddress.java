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

/**
 * A accessory decoder {@link Address} in range (0-32767). The main address lays
 * in range (0-511) (bits 2-10 from right), the sub address lays in range (0-3)
 * (bits 0 and 1 from right).
 * 
 * @author Jonas Tennié
 */
public class AccessoryDecoderAddress implements Address {

	private int address;

	/**
	 * Constructs a new {@link AccessoryDecoderAddress} with the specified
	 * address value. The main address lays in range (0-511) (bits 2-10 from
	 * right), the sub address lays in range (0-3) (bits 0 and 1 from right).
	 * The other bits are ignored.
	 * 
	 * @param address
	 *            The address in range (0-2047).
	 */
	public AccessoryDecoderAddress(int address) {
		this.address = address & 0b11_111_111_111; // No validation required
	}

	/**
	 * Constructs a new {@link AccessoryDecoderAddress} with the specified
	 * address and sub address.
	 * 
	 * @param mainAddress
	 *            The main address in range (0-511).
	 * @param subAddress
	 *            The sub address in range (0-3).
	 * @throws IllegalArgumentException
	 *             Thrown if <code>mainAddress</code> does not lay in range
	 *             (0-511) or <code>subAddress</code> does not lay in range
	 *             (0-3).
	 */
	public AccessoryDecoderAddress(int mainAddress, int subAddress) {
		if (mainAddress < 0 || mainAddress > 511) {
			throw new IllegalArgumentException(
					"main addres value out of valid range: " + mainAddress);
		}
		if (subAddress < 0 || subAddress > 3) {
			throw new IllegalArgumentException(
					"sub addres value out of valid range: " + subAddress);
		}
		address = mainAddress << 2 | subAddress;
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
		if (!(obj instanceof AccessoryDecoderAddress)) {
			return false;
		}
		AccessoryDecoderAddress other = (AccessoryDecoderAddress) obj;
		if (address != other.address) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return getClass().getName() + "[mainAddress=" + getMainAddress() + ", subAddress="
				+ getSubAddress() + "]";
	}

	@Override
	public int getAddress() {
		return address;
	}

	@Override
	public int getMainAddress() {
		return address >> 2;
	}

	@Override
	public int getSubAddress() {
		return address & 0b11;
	}

}
