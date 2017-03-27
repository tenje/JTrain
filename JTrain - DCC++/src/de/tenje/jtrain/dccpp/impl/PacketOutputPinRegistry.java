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

import java.util.Set;

import de.tenje.jtrain.AbstractOutputRegistry;
import de.tenje.jtrain.Address;
import de.tenje.jtrain.AddressRegistry;
import de.tenje.jtrain.OutputPin;
import de.tenje.jtrain.OutputRegistry;
import de.tenje.jtrain.SimpleAddressRegistry;
import de.tenje.jtrain.dccpp.LocalPacketBroker;
import de.tenje.jtrain.dccpp.Packet;
import de.tenje.jtrain.dccpp.PacketBroker;
import de.tenje.jtrain.dccpp.PacketListeningRegistry;
import de.tenje.jtrain.dccpp.PacketOutputPin;
import de.tenje.jtrain.dccpp.PacketOutputPinDefine;
import de.tenje.jtrain.dccpp.PacketOutputPinDelete;
import de.tenje.jtrain.dccpp.PacketOutputPinList;
import de.tenje.jtrain.dccpp.PacketOutputPinSetState;
import de.tenje.jtrain.dccpp.PacketTurnoutThrow;

/**
 * An {@link OutputRegistry} and {@link PacketListeningRegistry} for
 * {@link OutputPin}s. Listens (and responds) to the following {@link Packet}s:
 * {@link PacketOutputPinDefine}, {@link PacketOutputPinDelete},
 * {@link PacketOutputPinSetState}.
 * 
 * @author Jonas Tennié
 */
public class PacketOutputPinRegistry extends AbstractOutputRegistry<OutputPin> implements PacketListeningRegistry {

	private final AddressRegistry addressRegistry = new SimpleAddressRegistry();

	@Override
	public void packetReceived(Packet packet, PacketBroker sender, LocalPacketBroker receiver) {
		if (packet instanceof PacketOutputPin) {
			if (packet instanceof PacketOutputPinDefine) {
				getAddressRegistry().defineAddress(((PacketOutputPinDefine) packet).getId(),
						((PacketOutputPinDefine) packet).getAddress());
			}
			else if (packet instanceof PacketOutputPinDelete) {
				getAddressRegistry().defineAddress(((PacketOutputPinDelete) packet).getId(), null);
			}
			else if (packet instanceof PacketOutputPinList) {
				// TODO
			}
			else if (packet instanceof PacketOutputPinSetState) {
				Address address = getAddressRegistry().getAddress(((PacketTurnoutThrow) packet).getId());
				if (address != null) {
					Set<OutputPin> byId = getEntries().get(address);
					if (byId != null) {
						for (OutputPin pin : byId) {
							pin.setSwitched(((PacketOutputPinSetState) packet).getState());
						}
					}
				}
			}
		}
	}

	@Override
	public AddressRegistry getAddressRegistry() {
		return addressRegistry;
	}

}
