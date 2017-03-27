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
import org.tenje.jtrain.dccpp.PacketTrackPowerState;

/**
 * This class is a concrete implementation of the {@link PacketTrackPowerState}
 * interface.
 * 
 * @author Jonas Tennié
 */
public class PacketTrackPowerStateImpl extends AbstractPacket implements PacketTrackPowerState {

	/**
	 * {@link PacketBuilder} to build a {@link PacketTrackPowerState}.
	 */
	public static final PacketBuilder<PacketTrackPowerState> BUILDER = new PacketBuilder<PacketTrackPowerState>() {
		@Override
		public PacketTrackPowerState build(List<String> parameters) {
			return new PacketTrackPowerStateImpl(parameters);
		}
	};

	private final boolean powered;

	/**
	 * Constructs a new {@link PacketTrackPowerStateImpl} with the specified
	 * value if powered or not.
	 * 
	 * @param powered
	 *            The power state of the tracks. <code>true</code> if the tracks are
	 *            powered, else <code>false</code>.
	 */
	public PacketTrackPowerStateImpl(boolean powered) {
		super(PacketTrackPowerState.TYPE_CHAR, Arrays.asList(powered ? "true" : "false"));
		this.powered = powered;
	}

	/**
	 * Constructs a new {@link PacketOutputPinSetStateImpl} with the raw packet
	 * data containing the id and state to set.
	 * 
	 * @param parameters
	 *            The raw packet parameters. The first index (0) is the power
	 *            state ({@code "1"} for <code>true</code>, other value (not
	 *            <code>null</code>) for <code>false</code>).
	 * @throws IndexOutOfBoundsException
	 *             Thrown if <code>parameters</code> is empty.
	 * @throws NullPointerException
	 *             Thrown if first index (0) is <code>null</code>.
	 */
	public PacketTrackPowerStateImpl(List<String> parameters) {
		super(PacketTrackPowerState.TYPE_CHAR, parameters);
		powered = parameters.get(0).equals("1");
	}

	@Override
	public boolean isPowered() {
		return powered;
	}

}
