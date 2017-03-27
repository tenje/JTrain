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
import org.tenje.jtrain.dccpp.PacketTurnout;
import org.tenje.jtrain.dccpp.PacketTurnoutDefine;
import org.tenje.jtrain.dccpp.PacketTurnoutDelete;
import org.tenje.jtrain.dccpp.PacketTurnoutList;
import org.tenje.jtrain.dccpp.PacketTurnoutThrow;

/**
 * This class is an abstract implementation of the {@link PacketTurnout}
 * interface.
 * 
 * @author Jonas Tennié
 */
public abstract class PacketTurnoutImpl extends AbstractPacket implements PacketTurnout {

	/**
	 * {@link PacketBuilder} to build a sub packet of {@link PacketTurnout}. The
	 * size of <code>parameters</code> defines the type of the returned packet:
	 * <table>
	 * <tr>
	 * <td>0</td>
	 * <td>{@link PacketTurnoutList}</td>
	 * </tr>
	 * <tr>
	 * <td>1</td>
	 * <td>{@link PacketTurnoutDelete}</td>
	 * </tr>
	 * <tr>
	 * <td>2</td>
	 * <td>{@link PacketTurnoutThrow}</td>
	 * </tr>
	 * <tr>
	 * <td>3 (or more)</td>
	 * <td>{@link PacketTurnoutDefine}</td>
	 * </tr>
	 * </table>
	 */
	public static final PacketBuilder<PacketTurnout> BUILDER = new PacketBuilder<PacketTurnout>() {
		@Override
		public PacketTurnout build(List<String> parameters) {
			switch (parameters.size()) {
				case 0:
					return PacketTurnoutListImpl.BUILDER.build(parameters);
				case 1:
					return PacketTurnoutDeleteImpl.BUILDER.build(parameters);
				case 2:
					return PacketTurnoutThrowImpl.BUILDER.build(parameters);
				default: // 3 or more
					return PacketTurnoutDefineImpl.BUILDER.build(parameters);
			}
		}
	};

	/**
	 * Constructs a new <code>PacketTurnoutImpl</code> with the specified raw packet
	 * parameters.
	 * 
	 * @param parameters
	 *            The packet parameters as described in
	 *            {@link Packet#getRawParameters()}.
	 */
	public PacketTurnoutImpl(List<String> parameters) {
		super(PacketTurnout.TYPE_CHAR, parameters);
	}

	/**
	 * Constructs a new {@link PacketTurnoutImpl} with no parameters.
	 */
	public PacketTurnoutImpl() {
		super(PacketTurnout.TYPE_CHAR);
	}

}
