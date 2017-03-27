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
import de.tenje.jtrain.dccpp.PacketSensor;
import de.tenje.jtrain.dccpp.PacketSensorDefine;
import de.tenje.jtrain.dccpp.PacketSensorDelete;
import de.tenje.jtrain.dccpp.PacketSensorList;

/**
 * This class is an abstract implementation of the {@link PacketSensor}
 * interface.
 * 
 * @author Jonas Tennié
 */
public abstract class PacketSensorImpl extends AbstractPacket implements PacketSensor {

	/**
	 * {@link PacketBuilder} to build a sub packet of {@link PacketSensor}. The
	 * size of <code>parameters</code> defines the type of the returned packet:
	 * <table>
	 * <tr>
	 * <td>0</td>
	 * <td>{@link PacketSensorList}</td>
	 * </tr>
	 * <tr>
	 * <td>1 (or 2)</td>
	 * <td>{@link PacketSensorDelete}</td>
	 * </tr>
	 * <tr>
	 * <td>3 (or more)</td>
	 * <td>{@link PacketSensorDefine}</td>
	 * </tr>
	 * </table>
	 */
	public static final PacketBuilder<PacketSensor> BUILDER = new PacketBuilder<PacketSensor>() {
		@Override
		public PacketSensor build(List<String> parameters) {
			switch (parameters.size()) {
				case 0:
					return PacketSensorListImpl.BUILDER.build(parameters);
				case 1:
				case 2:
					return PacketSensorDeleteImpl.BUILDER.build(parameters);
				default: // 3 or more
					return PacketSensorDefineImpl.BUILDER.build(parameters);
			}
		}
	};

	/**
	 * Constructs a new <code>PacketSensorImpl</code> with the specified raw packet
	 * parameters.
	 * 
	 * @param parameters
	 *            The packet parameters as described in
	 *            {@link Packet#getRawParameters()}.
	 */
	public PacketSensorImpl(List<String> parameters) {
		super(PacketSensor.TYPE_CHAR, parameters);
	}

	/**
	 * Constructs a new {@link PacketSensorImpl} with no parameters.
	 */
	public PacketSensorImpl() {
		super(PacketSensor.TYPE_CHAR);
	}

}
