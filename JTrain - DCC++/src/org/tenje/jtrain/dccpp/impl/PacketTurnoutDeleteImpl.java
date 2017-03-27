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

import org.tenje.jtrain.dccpp.PacketBuilder;
import org.tenje.jtrain.dccpp.PacketTurnoutDelete;

/**
 * This class is a concrete implementation of the {@link PacketTurnoutDelete}
 * interface.
 * 
 * @author Jonas Tennié
 */
public class PacketTurnoutDeleteImpl extends PacketTurnoutImpl implements PacketTurnoutDelete {

	/**
	 * {@link PacketBuilder} to build a {@link PacketTurnoutDelete}.
	 */
	public static final PacketBuilder<PacketTurnoutDelete> BUILDER = new PacketBuilder<PacketTurnoutDelete>() {
		@Override
		public PacketTurnoutDelete build(List<String> parameters) {
			return new PacketTurnoutDeleteImpl(parameters);
		}
	};

	private final int id;

	/**
	 * Constructs a new {@link PacketTurnoutDeleteImpl} with the specified id.
	 * 
	 * @param id
	 *            The id to remove in range (0-32767).
	 */
	public PacketTurnoutDeleteImpl(int id) {
		super(Arrays.asList(String.valueOf(id)));
		if (id < 0 || id > 32767) {
			throw new IllegalArgumentException("id value out of valid range: " + id);
		}
		this.id = id;
	}

	/**
	 * Constructs a new {@link PacketTurnoutDeleteImpl} with the raw packet data
	 * containing the id.
	 * 
	 * @param parameters
	 *            The raw packet parameters. The first index (0) is the id.
	 * @throws IllegalArgumentException
	 *             Thrown if id does not lay in range (0-32767).
	 * @throws NumberFormatException
	 *             Thrown if the first index is not a number or is <code>null</code>.
	 * @throws IndexOutOfBoundsException
	 *             Thrown if <code>parameters</code> is empty.
	 */
	public PacketTurnoutDeleteImpl(List<String> parameters) {
		super(parameters);
		id = Integer.parseInt(parameters.get(0));
		if (id < 0 || id > 32767) {
			throw new IllegalArgumentException("id value out of valid range: " + id);
		}
	}

	@Override
	public int getId() {
		return id;
	}

}
