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

import org.tenje.jtrain.AccessoryDecoderAddress;
import org.tenje.jtrain.Address;
import org.tenje.jtrain.ParameterValidator;
import org.tenje.jtrain.dccpp.PacketBuilder;
import org.tenje.jtrain.dccpp.PacketTurnoutState;

/**
 * This class is a concrete implementation of the {@link PacketTurnoutState}
 * interface.
 * 
 * @author Jonas Tennié
 */
public class PacketTurnoutStateImpl extends AbstractPacket implements PacketTurnoutState {

	/**
	 * {@link PacketBuilder} to build a {@link PacketTurnoutState}.
	 */
	public static final PacketBuilder<PacketTurnoutState> BUILDER = new PacketBuilder<PacketTurnoutState>() {
		@Override
		public PacketTurnoutState build(List<String> parameters) {
			return new PacketTurnoutStateImpl(parameters);
		}
	};

	private final int id;
	private final AccessoryDecoderAddress address;
	private final boolean thrown;

	/**
	 * Constructs a new {@link PacketTurnoutStateImpl} with the specified id,
	 * address and throw state.
	 * 
	 * @param id
	 *            The ID in range (0-32767).
	 * @param address
	 *            The address.
	 * @param thrown
	 *            The throw state of the turnout.
	 * @throws IllegalArgumentException
	 *             Thrown if id does not lay in range (0-32767).
	 * @throws NullPointerException
	 *             Thrown if <code>address</code> is <code>null</code>.
	 */
	public PacketTurnoutStateImpl(int id, AccessoryDecoderAddress address, boolean thrown) {
		super(PacketTurnoutState.TYPE_CHAR, Arrays.asList(String.valueOf(id), String.valueOf(address.getMainAddress()),
				String.valueOf(address.getSubAddress()), thrown ? "1" : "0"));
		this.id = ParameterValidator.validateRegistrationId(id);
		this.address = Objects.requireNonNull(address, "address");
		this.thrown = thrown;
	}

	/**
	 * Constructs a new {@link PacketTurnoutStateImpl} with the raw packet data
	 * containing the id and throw state.
	 * 
	 * @param parameters
	 *            The raw packet parameters. The first index (0) is the id. The
	 *            second index (1) and third (2) index is the address. The
	 *            fourth index (3) is the throw state ({@code "1"} for
	 *            <code>true</code>, other value (not <code>null</code>) for
	 *            <code>false</code>).
	 * @throws IllegalArgumentException
	 *             Thrown if id does not lay in range (0-32767) or address does
	 *             not lay in range.
	 * @throws NumberFormatException
	 *             Thrown if one of the indexes 0-2 is not a number or is
	 *             <code>null</code>.
	 * @throws IndexOutOfBoundsException
	 *             Thrown if size of <code>parameters</code> less than three.
	 * @throws NullPointerException
	 *             Thrown if second index (1) is <code>null</code>.
	 */
	public PacketTurnoutStateImpl(List<String> parameters) {
		super(PacketTurnoutState.TYPE_CHAR, parameters);
		id = ParameterValidator.validateRegistrationId(Integer.parseInt(parameters.get(0)));
		address = new AccessoryDecoderAddress(Integer.parseInt(parameters.get(1)), Integer.parseInt(parameters.get(2)));
		thrown = parameters.get(3).equals("1");
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public Address getAddress() {
		return address;
	}

	@Override
	public boolean isThrown() {
		return thrown;
	}

}
