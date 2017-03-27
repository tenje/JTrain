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

import de.tenje.jtrain.dccpp.Packet;
import de.tenje.jtrain.dccpp.PacketBuilder;
import de.tenje.jtrain.dccpp.PacketOutputPin;
import de.tenje.jtrain.dccpp.PacketOutputPinDefine;
import de.tenje.jtrain.dccpp.PacketOutputPinDelete;
import de.tenje.jtrain.dccpp.PacketOutputPinList;
import de.tenje.jtrain.dccpp.PacketOutputPinSetState;

/**
 * This class is an abstract implementation of the {@link PacketOutputPin}
 * interface.
 * 
 * @author Jonas Tennié
 */
public abstract class PacketOutputPinImpl extends AbstractPacket implements PacketOutputPin {

	/**
	 * {@link PacketBuilder} to build a sub packet of {@link PacketOutputPin}.
	 * The size of <code>parameters</code> defines the type of the returned packet:
	 * <table>
	 * <tr>
	 * <td>0</td>
	 * <td>{@link PacketOutputPinList}</td>
	 * </tr>
	 * <tr>
	 * <td>1</td>
	 * <td>{@link PacketOutputPinDelete}</td>
	 * </tr>
	 * <tr>
	 * <td>2</td>
	 * <td>{@link PacketOutputPinSetState}</td>
	 * </tr>
	 * <tr>
	 * <td>3 (or more)</td>
	 * <td>{@link PacketOutputPinDefine}</td>
	 * </tr>
	 * </table>
	 */
	public static final PacketBuilder<PacketOutputPin> BUILDER = new PacketBuilder<PacketOutputPin>() {
		@Override
		public PacketOutputPin build(List<String> parameters) {
			switch (parameters.size()) {
				case 0:
					return PacketOutputPinListImpl.BUILDER.build(parameters);
				case 1:
					return PacketOutputPinDeleteImpl.BUILDER.build(parameters);
				case 2:
					return PacketOutputPinSetStateImpl.BUILDER.build(parameters);
				default: // 3 or more
					return PacketOutputPinDefineImpl.BUILDER.build(parameters);
			}
		}
	};

	/**
	 * Constructs a new <code>PacketOutputPinImpl</code> with the specified raw
	 * packet parameters.
	 * 
	 * @param parameters
	 *            The packet parameters as described in
	 *            {@link Packet#getRawParameters()}.
	 */
	public PacketOutputPinImpl(List<String> parameters) {
		super(PacketOutputPin.TYPE_CHAR, parameters);
	}

	/**
	 * Constructs a new {@link PacketOutputPinImpl} with no parameters.
	 */
	public PacketOutputPinImpl() {
		super(PacketOutputPin.TYPE_CHAR);
	}

}
