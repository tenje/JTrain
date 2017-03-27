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
