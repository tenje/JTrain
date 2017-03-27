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
 * The {@link Address} of an analog output pin (e.g. Arduino or Raspberry Pi
 * output pins). An analog output pin has one of two states: High or low. Some
 * pins may be forbidden to use. If the address is passed as parameter, but the
 * address value is invalid, then the address will be ignored. No
 * {@link Exception} will be thrown.
 * 
 * @author Jonas Tennié
 */
public class OutputPinAddress implements Address {

	private final int address;

	/**
	 * Constructs a new {@link OutputPinAddress} with the specified address
	 * value.
	 * 
	 * @param address
	 *            The address value.
	 */
	public OutputPinAddress(int address) {
		if (address < 0 || address > 127) {
			throw new IllegalArgumentException("addres value out of valid range: " + address);
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
		if (!(obj instanceof OutputPinAddress)) {
			return false;
		}
		OutputPinAddress other = (OutputPinAddress) obj;
		if (address != other.address) {
			return false;
		}
		return true;
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
