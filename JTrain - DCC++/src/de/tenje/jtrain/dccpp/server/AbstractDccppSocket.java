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
package de.tenje.jtrain.dccpp.server;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import de.tenje.jtrain.dccpp.LocalPacketBroker;
import de.tenje.jtrain.dccpp.Packet;
import de.tenje.jtrain.dccpp.PacketBuilder;
import de.tenje.jtrain.dccpp.PacketFactory;
import de.tenje.jtrain.dccpp.PacketListener;
import de.tenje.jtrain.dccpp.impl.PacketFactoryImpl;

/**
 * An abstract server used for the DCC++ protocol. Implements parts of the
 * following interfaces: {@link AutoCloseable}, {@link LocalPacketBroker} and
 * {@link SocketPacketBroker}. Uses a {@link PacketFactoryImpl} as
 * {@link PacketFactory} by default.
 * 
 * @author Jonas Tennié
 */
public abstract class AbstractDccppSocket implements AutoCloseable, LocalPacketBroker, SocketPacketBroker {

	private final InetAddress address;
	private final int port;
	private final Set<PacketListener> listeners = new HashSet<>();
	private final Set<PacketListener> unmodifiableListeners = Collections.unmodifiableSet(listeners);
	private PacketFactory packetFactory = new PacketFactoryImpl();

	/**
	 * Constructs a new {@link AbstractDccppSocket} with the specified
	 * <code>address</code> and <code>port</code>. No argument validation is made.
	 * 
	 * @param address
	 *            The address.
	 * @param port
	 *            The port.
	 */
	public AbstractDccppSocket(InetAddress address, int port) {
		this.address = address;
		this.port = port;
	}

	@Override
	public void close() throws IOException {
		listeners.clear();
	}

	/**
	 * Registers a packet listener for this server.
	 * 
	 * @param listener
	 *            The listener to register.
	 * @return <code>true</code> if the listener was registered successfully,
	 *         <code>false</code> if the listener was already registered.
	 * @throws NullPointerException
	 *             Thrown if <code>listener</code> is <code>null</code>.
	 */
	public boolean addPacketListener(PacketListener listener) {
		Objects.requireNonNull(listener, "listener");
		return listeners.add(listener);
	}

	/**
	 * Removes a packet listener from this server.
	 * 
	 * @param listener
	 *            The listener to remove. Ignores <code>null</code>-values.
	 * @return <code>true</code> if the listener was removed, <code>false</code> if the
	 *         listener was not registered or <code>null</code> was passed.
	 */
	public boolean removeListener(PacketListener listener) {
		return listeners.remove(listener);
	}

	/**
	 * Returns an unmodifiable set containing all registered listeners.
	 * 
	 * @return An unmodifiable set containing all registered listeners.
	 */
	public Set<PacketListener> getListeners() {
		return unmodifiableListeners;
	}

	/**
	 * Returns the packet factory used by this server to build the
	 * {@link Packet}s read from an input stream. Packets can be registered
	 * using {@link PacketFactory#registerBuilder(PacketBuilder, Class, char)}
	 * to allow this server to read packets of a specified type.
	 * 
	 * @return The packet factory used by this server.
	 */
	public PacketFactory getPacketFactory() {
		return packetFactory;
	}

	/**
	 * Returns the packet factory used by this server to build the
	 * {@link Packet}s read from an input stream.
	 * 
	 * @param factory
	 *            The factory to set.
	 * @throws NullPointerException
	 *             Thrown if <code>factory</code> is <code>null</code>.
	 */
	public void setPacketFactory(PacketFactory factory) {
		this.packetFactory = Objects.requireNonNull(factory, "factory");
	}

	@Override
	public InetAddress getInetAddress() {
		return address;
	}

	@Override
	public int getPort() {
		return port;
	}

}
