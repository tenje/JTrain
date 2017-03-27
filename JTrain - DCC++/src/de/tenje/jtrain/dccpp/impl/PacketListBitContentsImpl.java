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

import java.util.List;

import de.tenje.jtrain.dccpp.PacketBuilder;
import de.tenje.jtrain.dccpp.PacketListBitContents;

/**
 * This class is a concrete implementation of the {@link PacketListBitContents}
 * interface.
 * 
 * @author Jonas Tennié
 */
public class PacketListBitContentsImpl extends AbstractPacket implements PacketListBitContents {

	/**
	 * {@link PacketBuilder} to build a {@link PacketListBitContents}. The
	 * parameter list is ignored.
	 */
	public static final PacketBuilder<PacketListBitContents> BUILDER = new PacketBuilder<PacketListBitContents>() {
		@Override
		public PacketListBitContents build(List<String> parameters) {
			return new PacketListBitContentsImpl();
		}
	};

	/**
	 * Constructs a new {@link PacketListBitContentsImpl} with no packet
	 * parameters.
	 */
	public PacketListBitContentsImpl() {
		super(PacketListBitContents.TYPE_CHAR);
	}

}
