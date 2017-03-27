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

import java.util.List;

import org.tenje.jtrain.dccpp.PacketBuilder;
import org.tenje.jtrain.dccpp.PacketReadStationState;

/**
 * This class is a concrete implementation of the {@link PacketReadStationState}
 * interface.
 * 
 * @author Jonas Tenni�
 */
public class PacketReadStationStateImpl extends AbstractPacket implements PacketReadStationState {

	/**
	 * {@link PacketBuilder} to build a {@link PacketReadStationState}. The
	 * parameter list is ignored.
	 */
	public static final PacketBuilder<PacketReadStationState> BUILDER = new PacketBuilder<PacketReadStationState>() {
		@Override
		public PacketReadStationState build(List<String> parameters) {
			return new PacketReadStationStateImpl();
		}
	};

	/**
	 * Constructs a new {@link PacketReadStationStateImpl} with no packet
	 * parameters.
	 */
	public PacketReadStationStateImpl() {
		super(PacketReadStationState.TYPE_CHAR);
	}

}
