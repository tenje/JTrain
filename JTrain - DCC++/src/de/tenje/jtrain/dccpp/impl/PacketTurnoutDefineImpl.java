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

import org.tenje.jtrain.AccessoryDecoderAddress;
import org.tenje.jtrain.ParameterValidator;

import de.tenje.jtrain.dccpp.PacketBuilder;
import de.tenje.jtrain.dccpp.PacketTurnoutDefine;

/**
 * This class is a concrete implementation of the {@link PacketTurnoutDefine}
 * interface.
 * 
 * @author Jonas Tennié
 */
public class PacketTurnoutDefineImpl extends PacketTurnoutImpl implements PacketTurnoutDefine {

	/**
	 * {@link PacketBuilder} to build a {@link PacketTurnoutDefine}.
	 */
	public static final PacketBuilder<PacketTurnoutDefine> BUILDER = new PacketBuilder<PacketTurnoutDefine>() {
		@Override
		public PacketTurnoutDefine build(List<String> parameters) {
			return new PacketTurnoutDefineImpl(parameters);
		}
	};

	private final AccessoryDecoderAddress address;
	private final int id;

	/**
	 * Constructs a new {@link PacketTurnoutDefine} with the specified id,
	 * address and sub address.
	 * 
	 * @param id
	 *            The ID in range (0-32767).
	 * @param address
	 *            The address.
	 * @throws IllegalArgumentException
	 *             Thrown if id does not lay in range (0-32767).
	 * @throws NullPointerException
	 *             Thrown if <code>address</code> is <code>null</code>.
	 */
	public PacketTurnoutDefineImpl(int id, AccessoryDecoderAddress address) {
		super(Arrays.asList(String.valueOf(id), String.valueOf(address.getMainAddress()),
				String.valueOf(address.getSubAddress())));
		this.id = ParameterValidator.validateRegistrationId(id);
		this.address = Objects.requireNonNull(address, "address");
	}

	/**
	 * Constructs a new {@link PacketTurnoutDefineImpl} with the raw packet data
	 * containing the id, address and sub address.
	 * 
	 * @param parameters
	 *            The raw packet parameters. The first index (0) is the id. The
	 *            second index (1) is the address number. The third (2) index is
	 *            the sub address.
	 * @throws IllegalArgumentException
	 *             Thrown if id does not lay in range (0-32767), address does
	 *             not lay in range (0-511) or sub address does not lay in range
	 *             (0-3).
	 * @throws NumberFormatException
	 *             Thrown if one of the indexes 0-2 is not a number or is
	 *             <code>null</code>.
	 * @throws IndexOutOfBoundsException
	 *             Thrown if size of <code>parameters</code> less than three.
	 */
	public PacketTurnoutDefineImpl(List<String> parameters) {
		super(parameters);
		id = Integer.parseInt(parameters.get(0));
		address = new AccessoryDecoderAddress(Integer.parseInt(parameters.get(1)), Integer.parseInt(parameters.get(2)));
		ParameterValidator.validateRegistrationId(id);
	}

	@Override
	public AccessoryDecoderAddress getAddress() {
		return address;
	}

	@Override
	public int getId() {
		return id;
	}

}
