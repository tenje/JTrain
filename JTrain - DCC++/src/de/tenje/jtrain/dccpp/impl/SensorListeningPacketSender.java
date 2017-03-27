package de.tenje.jtrain.dccpp.impl;

import java.io.IOException;
import java.lang.Thread.State;
import java.util.Objects;

import de.tenje.jtrain.Sensor;
import de.tenje.jtrain.SensorListener;
import de.tenje.jtrain.dccpp.LocalPacketBroker;
import de.tenje.jtrain.dccpp.Packet;
import de.tenje.jtrain.dccpp.PacketBroker;
import de.tenje.jtrain.dccpp.PacketSensorStateActive;
import de.tenje.jtrain.dccpp.PacketSensorStateInactive;

/**
 * A {@link SensorListener} which sends a sensor state {@link Packet} every time
 * a sensor's {@link State} changed.
 * 
 * @author Jonas Tennié
 */
public class SensorListeningPacketSender implements SensorListener {

	private PacketSensorRegistry registry;
	private LocalPacketBroker sender;
	private PacketBroker receiver;

	/**
	 * Constructs a new {@link SensorListeningPacketSender} with the specified
	 * sensor registry, packet sender and packet receiver.
	 * 
	 * @param registry
	 *            The used sensor registry.
	 * @param sender
	 *            The sender which sends the packets.
	 * @param receiver
	 *            The receiver which receives the packets.
	 */
	public SensorListeningPacketSender(PacketSensorRegistry registry,
			LocalPacketBroker sender, PacketBroker receiver) {
		this.registry = Objects.requireNonNull(registry, "registry");
		this.sender = Objects.requireNonNull(sender, "sender");
		this.receiver = Objects.requireNonNull(receiver, "receiver");
	}

	/**
	 * {@inheritDoc} Sends a {@link PacketSensorStateActive} or
	 * {@link PacketSensorStateInactive} depending on the the current state of
	 * the <code>sensor</code> via the defined sender to the defined receiver.
	 * The registration id of the <code>sensor</code>'s address defined in the
	 * used {@link PacketSensorRegistry} is used.
	 */
	@Override
	public void sensorStateChanged(Sensor sensor) {
		int id = registry.getAddressRegistry().getId(sensor.getAddress());
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
	 * Returns the registry.
	 * 
	 * @return The registry.
	 */
	public PacketSensorRegistry getRegistry() {
		return registry;
	}

	/**
	 * Sets the registry.
	 * 
	 * @param registry
	 *            The registry to set.
	 * @throws NullPointerException
	 *             Thrown if <code>registry</code> is <code>null<code>.
	 */
	public void setRegistry(PacketSensorRegistry registry) {
		this.registry = Objects.requireNonNull(registry, "registry");
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
