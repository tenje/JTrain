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
package org.tenje.jtrain.dccpp.server;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.tenje.jtrain.dccpp.LocalPacketBroker;
import org.tenje.jtrain.dccpp.Packet;
import org.tenje.jtrain.dccpp.PacketBroker;
import org.tenje.jtrain.dccpp.PacketBuilder;
import org.tenje.jtrain.dccpp.PacketFactory;
import org.tenje.jtrain.dccpp.PacketListener;
import org.tenje.jtrain.dccpp.impl.PacketFactoryImpl;

/**
 * An abstract server used for the DCC++ protocol. Implements parts of the
 * following interfaces: {@link AutoCloseable}, {@link LocalPacketBroker} and
 * {@link SocketPacketBroker}. Uses a {@link PacketFactoryImpl} as
 * {@link PacketFactory} by default.
 * 
 * @author Jonas Tennié
 */
public abstract class AbstractDccppSocket
		implements AutoCloseable, LocalPacketBroker, SocketPacketBroker {

	private final InetAddress address;
	private final int port;
	private final Set<SocketListener> socketListeners = new HashSet<>();
	private final Set<PacketListener> packetListeners = new HashSet<>();
	private final Set<PacketListener> unmodifiablePacketListeners = Collections
			.unmodifiableSet(packetListeners);
	private PacketFactory packetFactory;

	/**
	 * Constructs a new {@link AbstractDccppSocket} with the specified
	 * <code>address</code> and <code>port</code>. Creates a new
	 * {@link PacketFactory} object.
	 * 
	 * @param address
	 *            The address.
	 * @param port
	 *            The port.
	 * @throws NullPointerException
	 *             Thrown if <code>address</code> is <code>null</code>.
	 * @throws IllegalArgumentException
	 *             Thrown if <code>port</code> does not lay in range (0-65535).
	 */
	public AbstractDccppSocket(InetAddress address, int port) {
		this(address, port, null);
	}

	/**
	 * Constructs a new {@link AbstractDccppSocket} with the specified
	 * <code>address</code> and <code>port</code>.
	 * 
	 * @param address
	 *            The address.
	 * @param port
	 *            The port.
	 * @param packetFactory
	 *            The packet factory to use. <code>null</code> to create a new
	 *            object.
	 * @throws NullPointerException
	 *             Thrown if <code>address</code> is <code>null</code>.
	 * @throws IllegalArgumentException
	 *             Thrown if <code>port</code> does not lay in range (0-65535).
	 */
	public AbstractDccppSocket(InetAddress address, int port,
			PacketFactory packetFactory) {
		this.address = Objects.requireNonNull(address, "address");
		if (port < 0 || port > 65535) {
			throw new IllegalArgumentException("port value out of valid range: " + port);
		}
		this.port = port;
		this.packetFactory = packetFactory == null ? new PacketFactoryImpl()
				: packetFactory;
	}

	@Override
	public void close() throws IOException {
		fireEvent(SocketEventType.SOCKET_CLOSE, null);
		packetListeners.clear();
	}

	/**
	 * Fires a {@link SocketEvent} for all registered listeners.
	 * 
	 * @param type
	 *            The event type.
	 * @param broker
	 *            The broker. May be <code>null</code> if <code>type</code> is
	 *            {@link SocketEventType#SOCKET_CLOSE}.
	 * @throws NullPointerException
	 *             Thrown if <code>type</code>is <code>null</code> or if
	 *             <code>broker</code> is <code>null</code>, but type is not
	 *             <code>SOCKET_CLOSE</code>.
	 * 
	 */
	protected void fireEvent(final SocketEventType type, final PacketBroker broker) {
		Objects.requireNonNull(type, "type");
		if (type != SocketEventType.SOCKET_CLOSE) {
			Objects.requireNonNull(broker, "broker");
		}
		SocketEvent event = new SocketEvent() {
			@Override
			public SocketEventType getType() {
				return type;
			}

			@Override
			public PacketBroker getBroker() {
				return broker;
			}
		};
		for (SocketListener listener : socketListeners) {
			listener.socketEvent(event);
		}
	}

	/**
	 * Registers a socket listener for this server.
	 * 
	 * @param listener
	 *            The listener to register.
	 * @return <code>true</code> if the listener was registered successfully,
	 *         <code>false</code> if the listener was already registered.
	 * @throws NullPointerException
	 *             Thrown if <code>listener</code> is <code>null</code>.
	 */
	public boolean addSocketListener(SocketListener listener) {
		Objects.requireNonNull(listener, "listener");
		return socketListeners.add(listener);
	}

	/**
	 * Removes a socket listener from this server.
	 * 
	 * @param listener
	 *            The listener to remove. Ignores <code>null</code>-values.
	 * @return <code>true</code> if the listener was removed, <code>false</code>
	 *         if the listener was not registered or <code>null</code> was
	 *         passed.
	 */
	public boolean removeSocketListener(SocketListener listener) {
		return socketListeners.remove(listener);
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
		return packetListeners.add(listener);
	}

	/**
	 * Removes a packet listener from this server.
	 * 
	 * @param listener
	 *            The listener to remove. Ignores <code>null</code>-values.
	 * @return <code>true</code> if the listener was removed, <code>false</code>
	 *         if the listener was not registered or <code>null</code> was
	 *         passed.
	 */
	public boolean removePacketListener(PacketListener listener) {
		return packetListeners.remove(listener);
	}

	/**
	 * Returns an unmodifiable set containing all registered listeners.
	 * 
	 * @return An unmodifiable set containing all registered listeners.
	 */
	public Set<PacketListener> getListeners() {
		return unmodifiablePacketListeners;
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
