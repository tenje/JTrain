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

import java.io.IOException;
import java.util.Map.Entry;
import java.util.Objects;

import org.tenje.jtrain.AbstractInputRegistry;
import org.tenje.jtrain.AccessoryDecoderAddress;
import org.tenje.jtrain.Address;
import org.tenje.jtrain.AddressRegistry;
import org.tenje.jtrain.InputRegistry;
import org.tenje.jtrain.Sensor;
import org.tenje.jtrain.SensorListener;
import org.tenje.jtrain.SimpleAddressRegistry;
import org.tenje.jtrain.dccpp.LocalPacketBroker;
import org.tenje.jtrain.dccpp.Packet;
import org.tenje.jtrain.dccpp.PacketBroker;
import org.tenje.jtrain.dccpp.PacketListeningRegistry;
import org.tenje.jtrain.dccpp.PacketSensor;
import org.tenje.jtrain.dccpp.PacketSensorDefine;
import org.tenje.jtrain.dccpp.PacketSensorDelete;
import org.tenje.jtrain.dccpp.PacketSensorList;
import org.tenje.jtrain.dccpp.PacketSensorStateActive;
import org.tenje.jtrain.dccpp.PacketSensorStateInactive;
import org.tenje.jtrain.dccpp.PacketSensorStateList;

/**
 * An {@link InputRegistry} and {@link PacketListeningRegistry} for
 * {@link Sensor}s. Listens (and responds) to the following {@link Packet}s:
 * {@link PacketSensorDefine}, {@link PacketSensorDelete},
 * {@link PacketSensorList}, {@link PacketSensorStateList}.<br />
 * Also listens to sensor state changes of all registered sensors and sends
 * {@link PacketSensorStateActive} and {@link PacketSensorStateInactive} on
 * state change.
 * 
 * @author Jonas Tennié
 */
public class PacketSensorRegistry extends AbstractInputRegistry<Sensor>
		implements PacketListeningRegistry, SensorListener {

	private final AddressRegistry addressRegistry = new SimpleAddressRegistry();
	private LocalPacketBroker sender;
	private PacketBroker receiver;

	/**
	 * Constructs a new {@link PacketSensorRegistry} with the defined packet
	 * sender and receiver to send sensor state packets.
	 * 
	 * @param sender
	 *            The sender to send the sensor state packets.
	 * @param receiver
	 *            The receiver to receive sensor state packets.
	 */
	public PacketSensorRegistry(LocalPacketBroker sender, PacketBroker receiver) {
		this.sender = Objects.requireNonNull(sender, "sender");
		this.receiver = Objects.requireNonNull(receiver, "receiver");
	}

	@Override
	public void packetReceived(Packet packet, PacketBroker sender,
			LocalPacketBroker receiver) throws IOException {
		if (packet instanceof PacketSensor) {
			if (packet instanceof PacketSensorDefine) {
				System.out.println("reg " + ((PacketSensorDefine) packet).getId()
						+ ((PacketSensorDefine) packet).getAddress());
				getAddressRegistry().defineAddress(((PacketSensorDefine) packet).getId(),
						((PacketSensorDefine) packet).getAddress());
				// Always successful
				receiver.sendPacket(new PacketOperationSuccessfulImpl(), sender);
			}
			else if (packet instanceof PacketSensorDelete) {
				// false if already defined
				if (getAddressRegistry()
						.defineAddress(((PacketSensorDelete) packet).getId(), null)) {
					receiver.sendPacket(new PacketOperationSuccessfulImpl(), sender);
				}
				else {
					receiver.sendPacket(new PacketOperationFailedImpl(), sender);
				}
			}
			else if (packet instanceof PacketSensorList) {
				Sensor sensor;
				for (Entry<Integer, Address> entry : getAddressRegistry()
						.getRegistrations().entrySet()) {
					sensor = getEntries().get(entry.getValue());
					if (sensor != null) {
						receiver.sendPacket(new PacketSensorDataImpl(entry.getKey(),
								(AccessoryDecoderAddress) entry.getValue(),
								sensor.isTriggered()), sender);
					}
				}
			}
		}
		else if (packet instanceof PacketSensorStateList) {
			Sensor sensor;
			for (Entry<Integer, Address> entry : getAddressRegistry().getRegistrations()
					.entrySet()) {
				sensor = getEntries().get(entry.getValue());
				if (sensor != null) {
					if (sensor.isTriggered()) {
						receiver.sendPacket(
								new PacketSensorStateActiveImpl(entry.getKey()), sender);
					}
					else {
						receiver.sendPacket(
								new PacketSensorStateInactiveImpl(entry.getKey()),
								sender);
					}
				}
			}
		}
	}

	@Override
	public AddressRegistry getAddressRegistry() {
		return addressRegistry;
	}

	@Override
	public Sensor register(Sensor object) {
		object.addSensorListener(this);
		return super.register(object);
	}

	@Override
	public boolean remove(Sensor object) {
		object.removeSensorListener(this);
		return super.remove(object);
	}

	@Override
	public void sensorStateChanged(Sensor sensor) {
		int id = addressRegistry.getId(sensor.getAddress());
		if (id >= 0) {
			if (sensor.isTriggered()) {
				try {
					sender.sendPacket(new PacketSensorStateActiveImpl(id), receiver);
				}
				catch (IOException ex) {}
			}
			else {
				try {
					sender.sendPacket(new PacketSensorStateInactiveImpl(id), receiver);
				}
				catch (IOException ex) {}
			}
		}
	}

	/**
	 * Returns the sender.
	 * 
	 * @return The sender.
	 */
	public LocalPacketBroker getSender() {
		return sender;
	}

	/**
	 * Sets the sender.
	 * 
	 * @param sender
	 *            The sender to set.
	 * @throws NullPointerException
	 *             Thrown if <code>sender</code> is <code>null<code>.
	 */
	public void setSender(LocalPacketBroker sender) {
		this.sender = Objects.requireNonNull(sender, "sender");
	}

	/**
	 * Returns the receiver.
	 * 
	 * @return The receiver.
	 */
	public PacketBroker getReceiver() {
		return receiver;
	}

	/**
	 * Sets the receiver.
	 * 
	 * @param receiver
	 *            The receiver to set.
	 * @throws NullPointerException
	 *             Thrown if <code>receiver</code> is <code>null<code>.
	 */
	public void setReceiver(PacketBroker receiver) {
		this.receiver = Objects.requireNonNull(receiver, "receiver");
	}

}
