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
import java.io.InputStream;

import de.tenje.jtrain.dccpp.Packet;
import de.tenje.jtrain.dccpp.PacketBroker;
import de.tenje.jtrain.dccpp.PacketListener;

class DccppListeningThread extends Thread {

	private final AbstractDccppSocket socket;
	private final PacketBroker connectedBroker;
	private final InputStream in;
	private final Runnable deathRunnable;

	DccppListeningThread(AbstractDccppSocket socket, PacketBroker connectedBroker,
			InputStream in, Runnable deathRunnable) {
		this.socket = socket;
		this.connectedBroker = connectedBroker;
		this.in = in;
		this.deathRunnable = deathRunnable;
	}

	@Override
	public void run() {
		// Auto closes stream->socket on fail
		try (PacketInputStream in = new PacketInputStream(this.in)) {
			Packet packetData;
			Packet packet;
			while (!Thread.interrupted()) {
				packetData = in.readRawPacket();
				try {
					packet = socket.getPacketFactory().buildPacket(
							packetData.getTypeChar(), packetData.getRawParameters());
				}
				catch (IllegalArgumentException ex) {
					ex.printStackTrace();
					continue;
				}
				if (packet != null) {
					for (PacketListener l : socket.getListeners()) {
						try {
							l.packetReceived(packet, connectedBroker, socket);
						}
						catch (IOException ex) {
							System.err.println("Debug: ");
							ex.printStackTrace();
							System.err.println();
							throw ex; // Ends thread
						}
						catch (Exception ex) {
							System.err.println("Listener failed to handle packet:");
							ex.printStackTrace();
						}
					}
				}
				else {
					System.err.println("Unknown packet: " + packetData.getTypeChar()
							+ ". Ignoring it...");
				}
			}
		}
		catch (IOException e) {}
		if (deathRunnable != null) {
			deathRunnable.run();
		}
	}

}
