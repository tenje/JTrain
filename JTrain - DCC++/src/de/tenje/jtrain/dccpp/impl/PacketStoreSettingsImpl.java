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
package de.tenje.jtrain.dccpp.impl;

import java.util.List;

import de.tenje.jtrain.dccpp.PacketBuilder;
import de.tenje.jtrain.dccpp.PacketStoreSettings;

/**
 * This class is a concrete implementation of the {@link PacketStoreSettings}
 * interface.
 * 
 * @author Jonas Tenni�
 */
public class PacketStoreSettingsImpl extends AbstractPacket implements PacketStoreSettings {

	/**
	 * {@link PacketBuilder} to build a {@link PacketStoreSettings}. The
	 * parameter list is ignored.
	 */
	public static final PacketBuilder<PacketStoreSettings> BUILDER = new PacketBuilder<PacketStoreSettings>() {
		@Override
		public PacketStoreSettings build(List<String> parameters) {
			return new PacketStoreSettingsImpl();
		}
	};

	/**
	 * Constructs a new {@link PacketStoreSettingsImpl} with no packet
	 * parameters.
	 */
	public PacketStoreSettingsImpl() {
		super(PacketStoreSettings.TYPE_CHAR);
	}

}
