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
package org.tenje.jtrain.dccpp.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.tenje.jtrain.AccessoryDecoderAddress;
import org.tenje.jtrain.ParameterValidator;
import org.tenje.jtrain.dccpp.PacketBuilder;
import org.tenje.jtrain.dccpp.PacketSensorDefine;

/**
 * This class is a concrete implementation of the {@link PacketSensorDefine}
 * interface.
 * 
 * @author Jonas Tenni�
 */
public class PacketSensorDefineImpl extends PacketSensorImpl
		implements PacketSensorDefine {

	/**
	 * {@link PacketBuilder} to build a {@link PacketSensorDefine}.
	 */
	public static final PacketBuilder<PacketSensorDefine> BUILDER = new PacketBuilder<PacketSensorDefine>() {
		@Override
		public PacketSensorDefine build(List<String> parameters) {
			return new PacketSensorDefineImpl(parameters);
		}
	};

	private final AccessoryDecoderAddress address;
	private final int id;
	private final boolean usePullUp;

	/**
	 * Constructs a new {@link PacketOutputPinDefineImpl} with the specified id,
	 * address and use pull-up value.
	 * 
	 * @param id
	 *            The id in range (0-32767).
	 * @param address
	 *            The address.
	 * @param usePullUp
	 *            Whether a pull-up resister is used or not. Used for the
	 *            Arduino.
	 * @throws IllegalArgumentException
	 *             Thrown if <code>id</code> does not lay in range (0-32767).
	 * @throws NullPointerException
	 *             Thrown if <code>address</code> is <code>null</code>.
	 */
	public PacketSensorDefineImpl(int id, AccessoryDecoderAddress address,
			@Deprecated boolean usePullUp) {
		super(Arrays.asList(String.valueOf(id), String.valueOf(address.getMainAddress()),
				usePullUp ? "1" : "0"));
		this.id = ParameterValidator.validateRegistrationId(id);
		this.address = Objects.requireNonNull(address, "address");
		this.usePullUp = usePullUp;
	}

	/**
	 * Constructs a new {@link PacketOutputPinDefineImpl} with the raw packet
	 * data containing the id, address and use pull-up value.
	 * 
	 * @param parameters
	 *            The raw packet parameters. The first index (0) is the id. The
	 *            second index (1) is the address. The third index (2) is the
	 *            use pull-up value ({@code "1"} for <code>true</code>, other
	 *            value (not <code>null</code>) for <code>false</code>).
	 * @throws IllegalArgumentException
	 *             Thrown if id does not lay in range (0-32767).
	 * @throws NumberFormatException
	 *             Thrown if index 0 or 1 is not a number or is
	 *             <code>null</code>.
	 * @throws IndexOutOfBoundsException
	 *             Thrown if size of <code>parameters</code> less than three.
	 * @throws NullPointerException
	 *             Thrown if index 2 is <code>null</code>.
	 */
	public PacketSensorDefineImpl(List<String> parameters) {
		super(parameters);
		id = ParameterValidator
				.validateRegistrationId(Integer.parseInt(parameters.get(0)));
		address = new AccessoryDecoderAddress(Integer.parseInt(parameters.get(1)), 0);
		usePullUp = parameters.get(2).equals("1");
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public AccessoryDecoderAddress getAddress() {
		return address;
	}

	@Override
	public boolean usePullUp() {
		return usePullUp;
	}

}
