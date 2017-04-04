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
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.tenje.jtrain.dccpp.Packet;
import org.tenje.jtrain.dccpp.PacketBroker;
import org.tenje.jtrain.dccpp.PacketBuilder;
import org.tenje.jtrain.dccpp.PacketFactory;
import org.tenje.jtrain.dccpp.PacketListener;

/**
 * A server socket to communicate with a control station, trains and other DCC++
 * components using the DCC++ protocol. The server reads the {@link Packet}s
 * from the connected sockets and passes them to specified
 * {@link PacketListener}. These listeners then handle the packet (and send
 * return packets).
 * 
 * @author Jonas Tennié
 */
public class DccppServerSocket extends AbstractDccppSocket {

	private final ServerSocket server;
	private final Map<SocketPacketBroker, Socket> socketsByBroker = new HashMap<>();
	private final Map<Socket, PacketOutputStream> socketOuts = new HashMap<>();
	private final Set<SocketPacketBroker> unmodifiableBrokers = Collections
			.unmodifiableSet(socketsByBroker.keySet());

	/**
	 * Creates a server socket, bound to the specified port. The server's
	 * {@link PacketFactory} has no registered {@link PacketBuilder}s by
	 * default.
	 * 
	 * @param port
	 *            The port number, or 0 to use a port number that is
	 *            automatically allocated.
	 * @throws IOException
	 *             Thrown if an I/O error occurs when opening the socket.
	 * @throws IllegalArgumentException
	 *             Thrown if the port parameter is outside the specified range
	 *             of valid port values, which is between 0 and 65535,
	 *             inclusive.
	 * @throws SecurityException
	 *             Thrown if a security manager exists and its
	 *             {@code SecurityManager#checkConnect(String, int)} method
	 *             doesn't allow the operation.
	 * @see ServerSocket#ServerSocket(int)
	 */
	public DccppServerSocket(int port) throws IOException {
		super(InetAddress.getLocalHost(), port);
		server = new ServerSocket(port);
		new Thread() {
			@Override
			public void run() {
				while (!Thread.interrupted()) {
					try {
						handleSocket(server.accept(), null);
					}
					catch (IOException e) {}
				}
			};
		}.start();
	}

	private void handleSocket(final Socket socket, SocketPacketBroker connectedBroker)
			throws IOException {
		if (connectedBroker == null) {
			connectedBroker = new SocketPacketBroker() {
				@Override
				public int getPort() {
					return socket.getPort();
				}

				@Override
				public InetAddress getInetAddress() {
					return socket.getInetAddress();
				}
			};
		}
		socketsByBroker.put(connectedBroker, socket);
		socketOuts.put(socket, new PacketOutputStream(socket.getOutputStream()));
		fireEvent(SocketEventType.BROKER_CONNECT, connectedBroker);
		new DccppListeningThread(this, connectedBroker, socket.getInputStream(), null)
				.start();
	}

	/**
	 * Closes this server socket and clears the set of registered
	 * {@link PacketListener}s.
	 */
	// All socket handler threads will end as an IOException is thrown when
	// closing the socket which causes the thread to finish
	@Override
	public void close() throws IOException {
		super.close();
		server.close();
		socketsByBroker.clear();
	}

	/**
	 * {@inheritDoc} If no connection exists with the <code>receiver</code>'s
	 * data, then this method will try to open a new socket to the specified
	 * <code>receiver</code>. Therefore <code>receiver</code> must be an
	 * instance of {@link SocketPacketBroker}. The <code>receiver</code> must be
	 * a server socket to accept the connection. This method may also be used to
	 * create a new connection to an other server socket.
	 */
	@Override
	public void sendPacket(Packet packet, PacketBroker receiver) throws IOException {
		Objects.requireNonNull(packet, "packet");
		Objects.requireNonNull(receiver, "receiver");
		Socket socket = socketsByBroker.get(receiver);
		if (socket == null || socket.isClosed()) { // No such socket
			SocketPacketBroker socketReceiver = (SocketPacketBroker) receiver;
			// Throws ClassCastException if receiver is no SocketPacketBroker
			socket = new Socket(socketReceiver.getInetAddress(),
					socketReceiver.getPort());
			handleSocket(socket, socketReceiver);
		}
		PacketOutputStream out = socketOuts.get(socket);
		try {
			out.writePacket(packet);
		}
		catch (IOException ex) {
			try {
				socket.close();
			}
			// Ignore. Only thrown if data to send still in socket buffer
			catch (IOException ex2) {}
			socketsByBroker.remove(receiver);
			socketOuts.remove(socket);
			fireEvent(SocketEventType.BROKER_DISCONNECT, receiver);
			throw ex;
		}
	}

	/**
	 * Returns an unmodifiable {@link Set} containing all connected
	 * {@link SocketPacketBroker}s. The set always represents the currently
	 * connected brokers.
	 * 
	 * @return An unmodifiable set containing all registered brokers.
	 */
	public Set<SocketPacketBroker> getConnectedBrokers() {
		return unmodifiableBrokers;
	}

}
