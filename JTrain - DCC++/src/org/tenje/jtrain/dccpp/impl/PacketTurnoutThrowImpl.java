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

import org.tenje.jtrain.ParameterValidator;
import org.tenje.jtrain.dccpp.PacketBuilder;
import org.tenje.jtrain.dccpp.PacketTurnoutThrow;

/**
 * This class is a concrete implementation of the {@link PacketTurnoutThrow}
 * interface.
 * 
 * @author Jonas Tennié
 */
public class PacketTurnoutThrowImpl extends PacketTurnoutImpl implements PacketTurnoutThrow {

	/**
	 * {@link PacketBuilder} to build a {@link PacketTurnoutThrow}.
	 */
	public static final PacketBuilder<PacketTurnoutThrow> BUILDER = new PacketBuilder<PacketTurnoutThrow>() {
		@Override
		public PacketTurnoutThrow build(List<String> parameters) {
			return new PacketTurnoutThrowImpl(parameters);
		}
	};

	private final int id;
	private final boolean thrown;

	/**
	 * Constructs a new {@link PacketTurnoutThrowImpl} with the specified id and
	 * throw state.
	 * 
	 * @param id
	 *            The ID in range (0-32767).
	 * @param thrown
	 *            The throw state of the turnout.
	 * @throws IllegalArgumentException
	 *             Thrown if id does not lay in range (0-32767).
	 */
	public PacketTurnoutThrowImpl(int id, boolean thrown) {
		super(Arrays.asList(String.valueOf(id), thrown ? "1" : "0"));
		this.id = ParameterValidator.validateRegistrationId(id);
		this.thrown = thrown;
	}

	/**
	 * Constructs a new {@link PacketTurnoutThrowImpl} with the raw packet data
	 * containing the id and throw state.
	 * 
	 * @param parameters
	 *            The raw packet parameters. The first index (0) is the id. The
	 *            second index (1) is the throw state ({@code "1"} for
	 *            <code>true</code>, other value (not <code>null</code>) for
	 *            <code>false</code>).
	 * @throws IllegalArgumentException
	 *             Thrown if id does not lay in range (0-32767).
	 * @throws NumberFormatException
	 *             Thrown if index 0 is not a number or is <code>null</code>.
	 * @throws IndexOutOfBoundsException
	 *             Thrown if size of <code>parameters</code> less than two.
	 * @throws NullPointerException
	 *             Thrown if second index (1) is <code>null</code>.
	 */
	public PacketTurnoutThrowImpl(List<String> parameters) {
		super(parameters);
		id = ParameterValidator.validateRegistrationId(Integer.parseInt(parameters.get(0)));
		thrown = parameters.get(1).equals("1");
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public boolean isThrown() {
		return thrown;
	}

}
