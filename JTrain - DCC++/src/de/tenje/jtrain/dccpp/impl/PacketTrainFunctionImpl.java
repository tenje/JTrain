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
package de.tenje.jtrain.dccpp.impl;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.tenje.jtrain.LongTrainAddress;

import de.tenje.jtrain.dccpp.PacketBuilder;
import de.tenje.jtrain.dccpp.PacketTrainFunction;

/**
 * This class is a concrete implementation of the {@link PacketTrainFunction}
 * interface.
 * 
 * @author Jonas Tennié
 */
public class PacketTrainFunctionImpl extends AbstractPacket implements PacketTrainFunction {

	/**
	 * {@link PacketBuilder} to build a {@link PacketTrainFunction}.
	 */
	public static final PacketBuilder<PacketTrainFunction> BUILDER = new PacketBuilder<PacketTrainFunction>() {
		@Override
		public PacketTrainFunction build(List<String> parameters) {
			return new PacketTrainFunctionImpl(parameters);
		}
	};

	private final LongTrainAddress address;
	private final int firstByte, secondByte;
	private final Map<Integer, Boolean> functionValues;

	/**
	 * Constructs a new {@link PacketTrainFunctionImpl} with the raw packet data
	 * containing the address and function byte(s).
	 * 
	 * @param parameters
	 *            The raw packet parameters. The first index (0) is the
	 *            adresse's index. The second index (1) is the first data byte.
	 *            The third (2, optional) index is the second optional data
	 *            byte.
	 * @throws IllegalArgumentException
	 *             Thrown if length of <code>data</code> is zero or address does not
	 *             lay in range (0-10293). Also thrown if the function bytes are
	 *             invalid.
	 * @throws NumberFormatException
	 *             Thrown if first (0) or second (1) (or third [2]) index is not
	 *             a number (or <code>null</code>).
	 */
	public PacketTrainFunctionImpl(List<String> parameters) {
		super(PacketTrainFunction.TYPE_CHAR, parameters);
		if (parameters.size() < 2) {
			throw new IllegalArgumentException("illegal/missing value(s) for funtion byte(s) or address");
		}
		address = new LongTrainAddress(Integer.parseInt(parameters.get(0)));
		firstByte = Integer.parseInt(parameters.get(1));
		if (firstByte >= 128) {
			Map<Integer, Boolean> functionValues;
			if (firstByte < 160) { // F0-F4
				int functionBits = firstByte - 128;
				secondByte = -1;
				functionValues = new HashMap<>(5);
				functionValues.put(0, (functionBits >> 4 & 1) == 1);
				functionValues.put(1, (functionBits & 1) == 1);
				functionValues.put(2, (functionBits >> 1 & 1) == 1);
				functionValues.put(3, (functionBits >> 2 & 1) == 1);
				functionValues.put(4, (functionBits >> 3 & 1) == 1);
			}
			else if (firstByte < 176) { // F9-F12
				int functionBits = firstByte - 160;
				secondByte = -1;
				functionValues = new HashMap<>(4);
				functionValues.put(9, (functionBits & 1) == 1);
				functionValues.put(10, (functionBits >> 1 & 1) == 1);
				functionValues.put(11, (functionBits >> 2 & 1) == 1);
				functionValues.put(12, (functionBits >> 3 & 1) == 1);
			}
			else if (firstByte < 191) { // F5-F8
				int functionBits = firstByte - 176;
				secondByte = -1;
				functionValues = new HashMap<>(4);
				functionValues.put(5, (functionBits & 1) == 1);
				functionValues.put(6, (functionBits >> 1 & 1) == 1);
				functionValues.put(7, (functionBits >> 2 & 1) == 1);
				functionValues.put(8, (functionBits >> 3 & 1) == 1);
			}
			else if (firstByte == 222) { // F13-F20
				if (parameters.size() == 2) {
					throw new IllegalArgumentException("illegal/missing value(s) for funtion byte(s)");
				}
				secondByte = Integer.parseInt(parameters.get(2));
				functionValues = new HashMap<>(8);
				functionValues.put(13, (secondByte & 1) == 1);
				functionValues.put(14, (secondByte >> 1 & 1) == 1);
				functionValues.put(15, (secondByte >> 2 & 1) == 1);
				functionValues.put(16, (secondByte >> 3 & 1) == 1);
				functionValues.put(17, (secondByte >> 4 & 1) == 1);
				functionValues.put(18, (secondByte >> 5 & 1) == 1);
				functionValues.put(19, (secondByte >> 6 & 1) == 1);
				functionValues.put(20, (secondByte >> 7 & 1) == 1);
			}
			else if (firstByte == 223) { // F21-F28
				if (parameters.size() == 2) {
					throw new IllegalArgumentException("illegal/missing value(s) for funtion byte(s)");
				}
				secondByte = Integer.parseInt(parameters.get(2));
				functionValues = new HashMap<>(8);
				functionValues.put(21, (secondByte & 1) == 1);
				functionValues.put(22, (secondByte >> 1 & 1) == 1);
				functionValues.put(23, (secondByte >> 2 & 1) == 1);
				functionValues.put(24, (secondByte >> 3 & 1) == 1);
				functionValues.put(25, (secondByte >> 4 & 1) == 1);
				functionValues.put(26, (secondByte >> 5 & 1) == 1);
				functionValues.put(27, (secondByte >> 6 & 1) == 1);
				functionValues.put(28, (secondByte >> 7 & 1) == 1);
			}
			else {
				throw new IllegalArgumentException("illegal/missing value(s) for funtion byte(s)");
			}
			this.functionValues = Collections.unmodifiableMap(functionValues);
		}
		else {
			throw new IllegalArgumentException("illegal/missing value(s) for funtion byte(s)");
		}
	}

	/**
	 * Constructs a new {@link PacketTrainFunctionImpl} with the specified
	 * address and function bytes.
	 * 
	 * @param address
	 *            The packet address.
	 * @param firstByte
	 *            The first function byte.
	 * @param secondByte
	 *            The second function byte. Ignored for F0-F12.
	 * @throws IllegalArgumentException
	 *             Thrown if address does not lay in range (0-10293) or if the
	 *             function bytes are invalid.
	 */
	public PacketTrainFunctionImpl(LongTrainAddress address, int firstByte, int secondByte) {
		this(Arrays.asList(String.valueOf(address.getAddress()), String.valueOf(firstByte),
				String.valueOf(secondByte)));
	}

	@Override
	public LongTrainAddress getAddress() {
		return address;
	}

	@Override
	public int getFirstByte() {
		return firstByte;
	}

	@Override
	public int getSecondByte() {
		return secondByte;
	}

	@Override
	public boolean isEnabled(int functionId) {
		Boolean enabled = functionValues.get(functionId);
		if (enabled != null) {
			return enabled;
		}
		throw new IllegalStateException("packet does not hold data for function id: " + functionId);
	}

	@Override
	public Map<Integer, Boolean> getFunctionValues() {
		return functionValues;
	}

}
