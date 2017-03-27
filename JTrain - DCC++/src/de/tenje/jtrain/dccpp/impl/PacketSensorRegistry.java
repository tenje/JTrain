package de.tenje.jtrain.dccpp.impl;

import java.io.IOException;
import java.util.Map.Entry;

import de.tenje.jtrain.AbstractInputRegistry;
import de.tenje.jtrain.AccessoryDecoderAddress;
import de.tenje.jtrain.Address;
import de.tenje.jtrain.AddressRegistry;
import de.tenje.jtrain.InputRegistry;
import de.tenje.jtrain.Sensor;
import de.tenje.jtrain.SimpleAddressRegistry;
import de.tenje.jtrain.dccpp.LocalPacketBroker;
import de.tenje.jtrain.dccpp.Packet;
import de.tenje.jtrain.dccpp.PacketBroker;
import de.tenje.jtrain.dccpp.PacketListeningRegistry;
import de.tenje.jtrain.dccpp.PacketSensor;
import de.tenje.jtrain.dccpp.PacketSensorDefine;
import de.tenje.jtrain.dccpp.PacketSensorDelete;
import de.tenje.jtrain.dccpp.PacketSensorList;
import de.tenje.jtrain.dccpp.PacketSensorStateList;

/**
 * An {@link InputRegistry} and {@link PacketListeningRegistry} for
 * {@link Sensor}s. Listens (and responds) to the following {@link Packet}s:
 * {@link PacketSensorDefine}, {@link PacketSensorDelete},
 * {@link PacketSensorList}, {@link PacketSensorStateList}.
 * 
 * @author Jonas Tennié
 */
public class PacketSensorRegistry extends AbstractInputRegistry<Sensor> implements PacketListeningRegistry {

	private final AddressRegistry addressRegistry = new SimpleAddressRegistry();

	@Override
	public void packetReceived(Packet packet, PacketBroker sender, LocalPacketBroker receiver) throws IOException {
		if (packet instanceof PacketSensor) {
			if (packet instanceof PacketSensorDefine) {
				getAddressRegistry().defineAddress(((PacketSensorDefine) packet).getId(),
						((PacketSensorDefine) packet).getAddress());
				// Always successful
				receiver.sendPacket(new PacketOperationSuccessfulImpl(), sender);
			}
			else if (packet instanceof PacketSensorDelete) {
				// false if already defined
				if (getAddressRegistry().defineAddress(((PacketSensorDelete) packet).getId(), null)) {
					receiver.sendPacket(new PacketOperationSuccessfulImpl(), sender);
				}
				else {
					receiver.sendPacket(new PacketOperationFailedImpl(), sender);
				}
			}
			else if (packet instanceof PacketSensorList) {
				Sensor sensor;
				for (Entry<Integer, Address> entry : getAddressRegistry().getRegistrations().entrySet()) {
					sensor = getEntries().get(entry.getValue());
					if (sensor != null) {
						receiver.sendPacket(new PacketSensorDataImpl(entry.getKey(),
								(AccessoryDecoderAddress) entry.getValue(), sensor.isTriggered()), sender);
					}
				}
			}
		}
		else if (packet instanceof PacketSensorStateList) {
			Sensor sensor;
			for (Entry<Integer, Address> entry : getAddressRegistry().getRegistrations().entrySet()) {
				sensor = getEntries().get(entry.getValue());
				if (sensor != null) {
					if (sensor.isTriggered()) {
						receiver.sendPacket(new PacketSensorStateActiveImpl(entry.getKey()), sender);
					}
					else {
						receiver.sendPacket(new PacketSensorStateInactiveImpl(entry.getKey()), sender);
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
