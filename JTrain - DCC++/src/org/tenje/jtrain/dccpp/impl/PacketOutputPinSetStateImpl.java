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

import java.util.List;

import org.tenje.jtrain.dccpp.PacketBuilder;
import org.tenje.jtrain.dccpp.PacketOutputPinSetState;

/**
 * This class is a concrete implementation of the
 * {@link PacketOutputPinSetState} interface.
 * 
 * @author Jonas Tennié
 */
public class PacketOutputPinSetStateImpl extends PacketOutputPinImpl implements PacketOutputPinSetState {

	/**
	 * {@link PacketBuilder} to build a {@link PacketOutputPinSetState}.
	 */
	public static final PacketBuilder<PacketOutputPinSetState> BUILDER = new PacketBuilder<PacketOutputPinSetState>() {
		@Override
		public PacketOutputPinSetState build(List<String> parameters) {
			return new PacketOutputPinSetStateImpl(parameters);
		}
	};

	private final int id;
	private final boolean state;

	/**
	 * Constructs a new {@link PacketOutputPinSetStateImpl} with the raw packet
	 * data containing the id and state to set.
	 * 
	 * @param parameters
	 *            The raw packet parameters. The first index (0) is the id. The
	 *            second index (1) is the state to set ({@code "1"} for
	 *            <code>true</code>, other value (not <code>null</code>) for
	 *            <code>false</code>).
	 * @throws IllegalArgumentException
	 *             Thrown if id does not lay in range (0-32767).
	 * @throws NumberFormatException
	 *             Thrown if one of the index 0 is not a number or is
	 *             <code>null</code>.
	 * @throws IndexOutOfBoundsException
	 *             Thrown if size of <code>parameters</code> less than two.
	 * @throws NullPointerException
	 *             Thrown if second index (1) is <code>null</code>.
	 */
	public PacketOutputPinSetStateImpl(List<String> parameters) {
		super(parameters);
		this.id = Integer.parseInt(parameters.get(0));
		if (id < 0 || id > 32767) {
			throw new IllegalArgumentException("id value out of valid range: " + id);
		}
		state = parameters.get(1).equals("1");
	}

	/**
	 * Constructs a new {@link PacketOutputPinSetStateImpl} with the specified
	 * id and state to set.
	 * 
	 * @param id
	 *            The ID in range (0-32767).
	 * @param state
	 *            The state of the pin to set.
	 * @throws IllegalArgumentException
	 *             Thrown if <code>id</code> does not lay in range (0-32767).
	 */
	public PacketOutputPinSetStateImpl(int id, boolean state) {
		if (id < 0 || id > 32767) {
			throw new IllegalArgumentException("id value out of valid range: " + id);
		}
		this.id = id;
		this.state = state;
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public boolean getState() {
		return state;
	}

}
