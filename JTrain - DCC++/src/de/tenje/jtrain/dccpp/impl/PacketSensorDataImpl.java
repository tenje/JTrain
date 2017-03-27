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
import java.util.List;
import java.util.Objects;

import de.tenje.jtrain.AccessoryDecoderAddress;
import de.tenje.jtrain.ParameterValidator;
import de.tenje.jtrain.dccpp.PacketBuilder;
import de.tenje.jtrain.dccpp.PacketSensorData;
import de.tenje.jtrain.dccpp.PacketSensorState;

/**
 * This class is a concrete implementation of the {@link PacketSensorData}
 * interface.
 * 
 * @author Jonas Tennié
 */
public class PacketSensorDataImpl extends AbstractPacket implements PacketSensorData {

	/**
	 * {@link PacketBuilder} to build a {@link PacketSensorData}.
	 */
	public static final PacketBuilder<PacketSensorData> BUILDER = new PacketBuilder<PacketSensorData>() {
		@Override
		public PacketSensorData build(List<String> parameters) {
			return new PacketSensorDataImpl(parameters);
		}
	};

	private final int id;
	private final AccessoryDecoderAddress address;
	private final boolean triggered;

	/**
	 * Constructs a new {@link PacketOutputPinDefineImpl} with the specified id,
	 * address and state.
	 * 
	 * @param id
	 *            The id in range (0-32767).
	 * @param address
	 *            The address.
	 * @param triggered
	 *            Whether sensor is triggered.
	 * @throws IllegalArgumentException
	 *             Thrown if <code>id</code> does not lay in range (0-32767).
	 * @throws NullPointerException
	 *             Thrown if <code>address</code> is <code>null</code>.
	 */
	public PacketSensorDataImpl(int id, AccessoryDecoderAddress address, boolean triggered) {
		super(PacketSensorState.TYPE_CHAR,
				Arrays.asList(String.valueOf(id), String.valueOf(address.getAddress()), triggered ? "1" : "0"));
		this.id = ParameterValidator.validateRegistrationId(id);
		this.address = Objects.requireNonNull(address, "address");
		this.triggered = triggered;
	}

	/**
	 * Constructs a new {@link PacketOutputPinDefineImpl} with the raw packet
	 * data containing the id, address and sensor state.
	 * 
	 * @param parameters
	 *            The raw packet parameters. The first index (0) is the id. The
	 *            second index (1) is the address. The third index (2) is the
	 *            sensor state ({@code "1"} for <code>true</code>, other value (not
	 *            <code>null</code>) for <code>false</code>).
	 * @throws IllegalArgumentException
	 *             Thrown if id does not lay in range (0-32767).
	 * @throws NumberFormatException
	 *             Thrown if index 0 or 1 is not a number or is <code>null</code>.
	 * @throws IndexOutOfBoundsException
	 *             Thrown if size of <code>parameters</code> less than three.
	 * @throws NullPointerException
	 *             Thrown if index 2 is <code>null</code>.
	 */
	public PacketSensorDataImpl(List<String> parameters) {
		super(PacketSensorState.TYPE_CHAR, parameters);
		id = ParameterValidator.validateRegistrationId(Integer.parseInt(parameters.get(0)));
		address = new AccessoryDecoderAddress(Integer.parseInt(parameters.get(1)));
		triggered = parameters.get(2).equals("1");
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
	public boolean isTriggered() {
		return triggered;
	}

}
