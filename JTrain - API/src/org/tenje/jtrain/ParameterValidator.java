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
 * This class contains some static utility methods to validate different train
 * control parameters.
 * 
 * @author Jonas Tennié
 */
public final class ParameterValidator {

	private ParameterValidator() {}

	/**
	 * Validates an address in range (0-10293) and returns it if valid.
	 * 
	 * @param address
	 *            The address to validate.
	 * @return The validated <code>address</code>.
	 * @throws IllegalArgumentException
	 *             Thrown if <code>address</code> does not lay in range (0-10293).
	 */
	public static int validateAddress(int address) {
		if (address < 0 || address > 10293) {
			throw new IllegalArgumentException("address out of valid range: " + address);
		}
		return address;
	}

	/**
	 * Validates a registration id. A register id is at least one.
	 * 
	 * @param registrationId
	 *            The registration id to validate.
	 * @return The validated register id.
	 * @throws IllegalArgumentException
	 *             Thrown if <code>registerId</code> is smaller than one.
	 */
	public static int validateRegistrationId(int registrationId) {
		if (registrationId < 0) {
			throw new IllegalArgumentException("registration id not valid: " + registrationId);
		}
		return registrationId;
	}

	/**
	 * Validates a 128-step speed value in range ((-1)-126) and returns it if
	 * valid.
	 * 
	 * @param speed
	 *            The speed to validate.
	 * @return The validated speed.
	 * @throws IllegalArgumentException
	 *             Thrown if <code>speed</code> does not lay in range ((-1)-126).
	 */
	public static int validateSpeed(int speed) {
		if (speed < -1 || speed > 126) {
			throw new IllegalArgumentException("speed out of valid range: " + speed);
		}
		return speed;
	}

	/**
	 * Validates a callback number in range (0-32767).
	 * 
	 * @param callbackNumber
	 *            The callback number to validate.
	 * @return The validated callback number.
	 * @throws IllegalArgumentException
	 *             Thrown if <code>callbackNumber</code> does not lay in range
	 *             (0-32767).
	 */
	public static int validateCallbackNumber(int callbackNumber) {
		if (callbackNumber < 0 || callbackNumber > 32767) {
			throw new IllegalArgumentException("callback num/sum out of valid range: " + callbackNumber);
		}
		return callbackNumber;
	}

	/**
	 * Validates a byte value in range (0-255).
	 * 
	 * @param value
	 *            The value to validate.
	 * @return The validated value.
	 * @throws IllegalArgumentException
	 *             Thrown if <code>value</code> does not lay in range (0-255).
	 */
	public static int validateByteValue(int value) {
		if (value < 0 || value > 255) {
			throw new IllegalArgumentException("value out of range: " + value);
		}
		return value;
	}

	/**
	 * Validates a variable location in range (1-1024).
	 * 
	 * @param variableLocation
	 *            The variable location to validate.
	 * @return The validated variable location.
	 * @throws IllegalArgumentException
	 *             Thrown if <code>variableLocation</code> does not lay in range
	 *             (1-1024).
	 */
	public static int validateVariableLocation(int variableLocation) {
		if (variableLocation < 1 || variableLocation > 1024) {
			throw new IllegalArgumentException("variable location out of valid range: " + variableLocation);
		}
		return variableLocation;
	}
}
