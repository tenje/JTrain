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
package org.tenje.jtrain.dccpp.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.tenje.jtrain.OutputPinAddress;
import org.tenje.jtrain.dccpp.PacketBuilder;
import org.tenje.jtrain.dccpp.PacketOutputPinDefine;

/**
 * This class is a concrete implementation of the {@link PacketOutputPinDefine}
 * interface.
 * 
 * @author Jonas Tennié
 */
public class PacketOutputPinDefineImpl extends PacketOutputPinImpl
		implements PacketOutputPinDefine {

	/**
	 * {@link PacketBuilder} to build a {@link PacketOutputPinDefine}.
	 */
	public static final PacketBuilder<PacketOutputPinDefine> BUILDER = new PacketBuilder<PacketOutputPinDefine>() {
		@Override
		public PacketOutputPinDefine build(List<String> parameters) {
			return new PacketOutputPinDefineImpl(parameters);
		}
	};

	private final OutputPinAddress address;
	private final int id;
	private final int flags;

	/**
	 * Constructs a new {@link PacketOutputPinDefineImpl} with the specified id,
	 * pin and flags.
	 * 
	 * @param id
	 *            The ID in range (0-32767).
	 * @param address
	 *            The pin address.
	 * @param isInvertedOperation
	 *            Return value for
	 *            {@link PacketOutputPinDefine#isInvertedOperation()}.
	 * @param isResetOnPowerUp
	 *            Return value for
	 *            {@link PacketOutputPinDefine#isResetOnPowerUp()}.
	 * @param defaultPinState
	 *            Return value for
	 *            {@link PacketOutputPinDefine#getDefaultPinState()}.
	 * @throws IllegalArgumentException
	 *             Thrown if <code>id</code> does not lay in range (0-32767).
	 */
	public PacketOutputPinDefineImpl(int id, OutputPinAddress address,
			boolean isInvertedOperation, boolean isResetOnPowerUp,
			boolean defaultPinState) {
		super(Arrays.asList(String.valueOf(id), String.valueOf(address.getAddress()),
				String.valueOf(flagsToInt(isInvertedOperation, isResetOnPowerUp,
						defaultPinState))));
		if (id < 0 || id > 32767) {
			throw new IllegalArgumentException("id value out of valid range: " + id);
		}
		this.id = id;
		this.address = Objects.requireNonNull(address, "address");
		flags = flagsToInt(isInvertedOperation, isResetOnPowerUp, defaultPinState);
	}

	private static int flagsToInt(boolean f0, boolean f1, boolean f2) {
		int v0 = f0 ? 1 : 0;
		int v1 = f1 ? 1 : 0;
		int v2 = f2 ? 1 : 0;
		return v0 | v1 << 1 | v2 << 2;
	}

	/**
	 * Constructs a new {@link PacketOutputPinDefineImpl} with the raw packet
	 * data containing the id, pin number and flags.
	 * 
	 * @param parameters
	 *            The raw packet parameters. The first index (0) is the id. The
	 *            second index (1) is the pin number. The third (2) index
	 *            contains the flags.
	 * @throws IllegalArgumentException
	 *             Thrown if id does not lay in range (0-32767) or address is
	 *             smaller than zero.
	 * @throws NumberFormatException
	 *             Thrown if one of the indexes 0-2 is not a number or is
	 *             <code>null</code>.
	 * @throws IndexOutOfBoundsException
	 *             Thrown if size of <code>parameters</code> less than three.
	 */
	public PacketOutputPinDefineImpl(List<String> parameters) {
		super(parameters);
		id = Integer.parseInt(parameters.get(0));
		if (id < 0 || id > 32767) {
			throw new IllegalArgumentException("id value out of valid range: " + id);
		}
		this.address = new OutputPinAddress(Integer.parseInt(parameters.get(1)));
		this.flags = Integer.parseInt(parameters.get(2)) & 0b111;
	}

	/**
	 * Constructs a new {@link PacketOutputPinDefineImpl} with the specified id,
	 * address and flags.
	 * 
	 * @param id
	 *            The ID in range (0-32767).
	 * @param address
	 *            The pin address.
	 * @param flags
	 *            The flags. Bit 0 is {@link #isInvertedOperation()}, bit 1 is
	 *            {@link #isResetOnPowerUp()} and bit 2 is
	 *            {@link #getDefaultPinState()}.
	 */
	public PacketOutputPinDefineImpl(int id, OutputPinAddress address, int flags) {
		super(Arrays.asList(String.valueOf(id), String.valueOf(address.getAddress()),
				String.valueOf(flagsToInt((flags & 1) == 1, (flags >> 1 & 1) == 1,
						(flags >> 2 & 1) == 1))));
		if (id < 0 || id > 32767) {
			throw new IllegalArgumentException("id value out of valid range: " + id);
		}
		this.id = id;
		this.address = Objects.requireNonNull(address, "address");
		this.flags = flags & 0b111;
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public OutputPinAddress getAddress() {
		return address;
	}

	@Override
	public boolean isInvertedOperation() {
		return (flags & 1) == 1;
	}

	@Override
	public boolean isResetOnPowerUp() {
		return (flags >> 1 & 1) == 1;
	}

	@Override
	public boolean getDefaultPinState() {
		return (flags >> 2 & 1) == 1;
	}

	@Override
	public int getFlags() {
		return flags;
	}

}
