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

import java.io.IOException;

import de.tenje.jtrain.AddressRegistry;
import de.tenje.jtrain.Signal;
import de.tenje.jtrain.SignalAspect;
import de.tenje.jtrain.SimpleAddressRegistry;
import de.tenje.jtrain.dccpp.LocalPacketBroker;
import de.tenje.jtrain.dccpp.Packet;
import de.tenje.jtrain.dccpp.PacketBroker;
import de.tenje.jtrain.dccpp.PacketListener;
import de.tenje.jtrain.dccpp.PacketListeningRegistry;
import de.tenje.jtrain.dccpp.PacketTurnoutDefine;
import de.tenje.jtrain.dccpp.PacketTurnoutDelete;
import de.tenje.jtrain.dccpp.PacketTurnoutList;
import de.tenje.jtrain.dccpp.PacketTurnoutThrow;

/**
 * A {@link PacketListeningRegistry} for {@link Signal}s. Each
 * {@link SignalAspect} of a signal has its own address. The
 * {@link PacketListener} listens to {@link PacketTurnoutThrow} to set the
 * signal's aspect. The value returned by {@link PacketTurnoutThrow#isThrown()}
 * is ignored. Listens (and responds) to the following {@link Packet}s:
 * {@link PacketTurnoutDefine}, {@link PacketTurnoutDelete},
 * {@link PacketTurnoutList}, {@link PacketTurnoutThrow}.
 * 
 * @author Jonas Tennié
 */
public class PacketSignalRegistry extends PacketTurnoutRegistry {

	private final AddressRegistry addressRegistry = new SimpleAddressRegistry();

	@Override
	public void packetReceived(Packet packet, PacketBroker sender,
			LocalPacketBroker receiver) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public AddressRegistry getAddressRegistry() {
		return addressRegistry;
	}

}
