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

import java.io.IOException;
import java.util.Map.Entry;
import java.util.Set;

import de.tenje.jtrain.AbstractOutputRegistry;
import de.tenje.jtrain.AccessoryDecoderAddress;
import de.tenje.jtrain.Address;
import de.tenje.jtrain.AddressRegistry;
import de.tenje.jtrain.AddressableSwitchable;
import de.tenje.jtrain.OutputRegistry;
import de.tenje.jtrain.SimpleAddressRegistry;
import de.tenje.jtrain.Switchable;
import de.tenje.jtrain.dccpp.LocalPacketBroker;
import de.tenje.jtrain.dccpp.Packet;
import de.tenje.jtrain.dccpp.PacketBroker;
import de.tenje.jtrain.dccpp.PacketListeningRegistry;
import de.tenje.jtrain.dccpp.PacketTurnout;
import de.tenje.jtrain.dccpp.PacketTurnoutDefine;
import de.tenje.jtrain.dccpp.PacketTurnoutDelete;
import de.tenje.jtrain.dccpp.PacketTurnoutList;
import de.tenje.jtrain.dccpp.PacketTurnoutThrow;

/**
 * An {@link OutputRegistry} and {@link PacketListeningRegistry} for
 * {@link Switchable}s. Listens (and responds) to the following {@link Packet}s:
 * {@link PacketTurnoutDefine}, {@link PacketTurnoutDelete},
 * {@link PacketTurnoutList}, {@link PacketTurnoutThrow}.
 * 
 * @author Jonas Tenni�
 */
public class PacketTurnoutRegistry extends AbstractOutputRegistry<AddressableSwitchable>
		implements PacketListeningRegistry {

	private final AddressRegistry addressRegistry = new SimpleAddressRegistry();

	@Override
	public void packetReceived(Packet packet, PacketBroker sender,
			LocalPacketBroker receiver) throws IOException {
		if (packet instanceof PacketTurnout) {
			if (packet instanceof PacketTurnoutDefine) {
				getAddressRegistry().defineAddress(((PacketTurnoutDefine) packet).getId(),
						((PacketTurnoutDefine) packet).getAddress());
				receiver.sendPacket(new PacketOperationSuccessfulImpl(), sender);
			}
			else if (packet instanceof PacketTurnoutDelete) {
				if (getAddressRegistry()
						.defineAddress(((PacketTurnoutDelete) packet).getId(), null)) {
					receiver.sendPacket(new PacketOperationSuccessfulImpl(), sender);
				}
				else {
					receiver.sendPacket(new PacketOperationFailedImpl(), sender);
				}
			}
			else if (packet instanceof PacketTurnoutList) {
				for (Entry<Integer, Address> entry : getAddressRegistry()
						.getRegistrations().entrySet()) {
					for (Switchable s : getEntries().get(entry.getValue())) {
						receiver.sendPacket(new PacketTurnoutStateImpl(entry.getKey(),
								(AccessoryDecoderAddress) entry.getValue(),
								s.isSwitched()), sender);
					}
				}
			}
			else if (packet instanceof PacketTurnoutThrow) {
				Address address = getAddressRegistry()
						.getAddress(((PacketTurnoutThrow) packet).getId());
				if (address != null) {
					Set<AddressableSwitchable> byId = getEntries().get(address);
					if (byId != null) {
						for (Switchable switchable : byId) {
							switchable.setSwitched(
									((PacketTurnoutThrow) packet).isThrown());
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
