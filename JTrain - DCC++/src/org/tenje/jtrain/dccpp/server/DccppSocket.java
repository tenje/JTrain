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
import java.net.Socket;

import org.tenje.jtrain.dccpp.Packet;
import org.tenje.jtrain.dccpp.PacketBroker;
import org.tenje.jtrain.dccpp.PacketFactory;
import org.tenje.jtrain.dccpp.PacketListener;

/**
 * A socket to connect to a DCC++ server socket to control trains and other
 * DCC++ components using the DCC++ protocol. The socket reads the
 * {@link Packet}s from the connected sockets and passes them to specified
 * {@link PacketListener}. These listeners then handle the packet (and send
 * return packets).
 * 
 * @author Jonas Tennié
 */
public class DccppSocket extends AbstractDccppSocket {

	private final Socket socket;
	private final PacketOutputStream out;
	private final SocketPacketBroker connectedBroker;

	/**
	 * Constructs a new {@link DccppSocket} and connects it to the specified
	 * port number at the specified IP address. Creates a new
	 * {@link PacketFactory} object.
	 * 
	 * @param address
	 *            The IP address of the remote.
	 * @param port
	 *            The port number of the remote.
	 * @throws IOException
	 *             Thrown if an I/O error occurs when creating the socket.
	 * @throws IllegalArgumentException
	 *             Thrown if the port parameter is outside the specified range
	 *             of valid port values, which is between 0 and 65535,
	 *             inclusive.
	 * @throws NullPointerException
	 *             Thrown if <code>address</code> is <code>null</code>.
	 * @throws SecurityException
	 *             Thrown if a security manager exists and its
	 *             {@code SecurityManager#checkConnect(String, int)} method
	 *             doesn't allow the operation.
	 * @see Socket#Socket(String, int)
	 */
	public DccppSocket(InetAddress address, int port) throws IOException {
		this(address, port, null);
	}

	/**
	 * Constructs a new {@link DccppSocket} and connects it to the specified
	 * port number at the specified IP address.
	 * 
	 * @param address
	 *            The IP address of the remote.
	 * @param port
	 *            The port number of the remote.
	 * @param packetFactory
	 *            The packet factory to use. <code>null</code> to create a new
	 *            object.
	 * @throws IOException
	 *             Thrown if an I/O error occurs when creating the socket.
	 * @throws IllegalArgumentException
	 *             Thrown if the port parameter is outside the specified range
	 *             of valid port values, which is between 0 and 65535,
	 *             inclusive.
	 * @throws NullPointerException
	 *             Thrown if <code>address</code> is <code>null</code>.
	 * @throws SecurityException
	 *             Thrown if a security manager exists and its
	 *             {@code SecurityManager#checkConnect(String, int)} method
	 *             doesn't allow the operation.
	 * @see Socket#Socket(String, int)
	 */
	public DccppSocket(final InetAddress address, final int port,
			PacketFactory packetFactory) throws IOException {
		super(address, port, packetFactory);
		socket = new Socket(address, port);
		out = new PacketOutputStream(socket.getOutputStream());
		connectedBroker = new SocketPacketBroker() {
			@Override
			public int getPort() {
				return port;
			}

			@Override
			public InetAddress getInetAddress() {
				return address;
			}
		};
		Thread thread = new DccppListeningThread(this, connectedBroker,
				socket.getInputStream(), new Runnable() {
					@Override
					public void run() {
						DccppSocket.this.notifyAll();
					}
				});
		thread.start();
		fireEvent(SocketEventType.BROKER_CONNECT, connectedBroker);
	}

	@Override
	public void sendPacket(Packet packet, PacketBroker receiver) throws IOException {
		if (receiver == null || receiver == connectedBroker) {
			out.writePacket(packet);
		}
		else {
			throw new UnsupportedOperationException(
					"cannot send data to specified receiver");
		}
	}

	/**
	 * Returns the connected {@link PacketBroker}.
	 * 
	 * @return The connected packet broker.
	 */
	public SocketPacketBroker getConnectedBroker() {
		return connectedBroker;
	}

}
