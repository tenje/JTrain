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

import org.tenje.jtrain.dccpp.Packet;
import org.tenje.jtrain.dccpp.PacketBuilder;
import org.tenje.jtrain.dccpp.PacketSensorState;
import org.tenje.jtrain.dccpp.PacketSensorStateActive;
import org.tenje.jtrain.dccpp.PacketSensorStateList;

/**
 * This class is a concrete implementation of the {@link PacketSensorState}
 * interface.
 * 
 * @author Jonas Tennié
 */
public class PacketSensorStateImpl extends AbstractPacket implements PacketSensorState {

	/**
	 * {@link PacketBuilder} to build a sub packet of {@link PacketSensorState}.
	 * The size of <code>parameters</code> defines the type of the returned packet:
	 * <table>
	 * <tr>
	 * <td>0</td>
	 * <td>{@link PacketSensorStateList}</td>
	 * </tr>
	 * <tr>
	 * <td>1 (or more)</td>
	 * <td>{@link PacketSensorStateActive}</td>
	 * </tr>
	 * </table>
	 */
	public static final PacketBuilder<PacketSensorState> BUILDER = new PacketBuilder<PacketSensorState>() {
		@Override
		public PacketSensorState build(List<String> parameters) {
			switch (parameters.size()) {
				case 0:
					return PacketSensorStateListImpl.BUILDER.build(parameters);
				case 1:
					return PacketSensorStateActiveImpl.BUILDER.build(parameters);
				default: // 2 or more
					return PacketSensorDataImpl.BUILDER.build(parameters);
			}
		}
	};

	/**
	 * Constructs a new <code>PacketSensorState</code> with the specified raw packet
	 * parameters.
	 * 
	 * @param parameters
	 *            The packet parameters as described in
	 *            {@link Packet#getRawParameters()}.
	 */
	public PacketSensorStateImpl(List<String> parameters) {
		super(PacketSensorState.TYPE_CHAR, parameters);
	}

	/**
	 * Constructs a new {@link PacketSensorState} with no parameters.
	 */
	public PacketSensorStateImpl() {
		super(PacketSensorState.TYPE_CHAR);
	}

}
