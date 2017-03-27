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
import de.tenje.jtrain.dccpp.PacketClearSettings;

/**
 * This class is a concrete implementation of the {@link PacketClearSettings}
 * interface.
 * 
 * @author Jonas Tennié
 */
public class PacketClearSettingsImpl extends AbstractPacket implements PacketClearSettings {

	/**
	 * {@link PacketBuilder} to build a {@link PacketClearSettings}. The
	 * parameter list is ignored.
	 */
	public static final PacketBuilder<PacketClearSettings> BUILDER = new PacketBuilder<PacketClearSettings>() {
		@Override
		public PacketClearSettings build(List<String> parameters) {
			return new PacketClearSettingsImpl();
		}
	};

	/**
	 * Constructs a new {@link PacketClearSettingsImpl} with no packet
	 * parameters.
	 */
	public PacketClearSettingsImpl() {
		super(PacketClearSettings.TYPE_CHAR);
	}

}
