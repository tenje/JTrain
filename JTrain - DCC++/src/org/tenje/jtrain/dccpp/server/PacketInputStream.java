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
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.tenje.jtrain.dccpp.Packet;

/**
 * An {@link InputStream} which reads {@link Packet}s from an other underlying
 * (handled) input stream.
 * 
 * @author Jonas Tennié
 */
public class PacketInputStream extends InputStream {

	private InputStream handle;

	/**
	 * Constructs a new {@link PacketInputStream} with the specified handled
	 * input stream.
	 * 
	 * @param handle
	 *            The handled input stream.
	 * @throws NullPointerException
	 *             Thrown if <code>handle</code> is <code>null</code>.
	 */
	public PacketInputStream(InputStream handle) {
		this.handle = Objects.requireNonNull(handle, "handle");
	}

	@Override
	public int read(byte[] b, int off, int len) throws IOException {
		return handle.read(b, off, len);
	}

	@Override
	public int read() throws IOException {
		return handle.read();
	}

	/**
	 * Closes this input stream and releases any system resources associated
	 * with the stream. The handled input stream is also closed.
	 * 
	 * @throws IOException
	 *             Thrown if an I/O error occurs while closing the stream.
	 */
	@Override
	public void close() throws IOException {
		handle.close();
		handle = null;
	}

	/**
	 * Reads a {@link Packet} from the handle {@link InputStream}. The stream is
	 * read until a packet begins (a '&gt;' is read). Only 20 parameter chars
	 * are read. Chars after the 20th parameter char are ignored. The returned
	 * packet is a simple implementation of the {@link Packet} interface and
	 * does not implement any other interfaces of different packet types.
	 * 
	 * @return The read packet containing only the packet type char and the raw
	 *         packet parameters.
	 * @throws IOException
	 *             Thrown if an I/O error occurs while reading from the handled
	 *             stream.
	 */
	public Packet readRawPacket() throws IOException {
		int read;
		final char typeChar;
		char[] parameterBuffer = new char[20];
		int parameterBufferIndex = 0;
		final List<String> parameters = new ArrayList<>(5);
		// Wait for packet, ignore other chars:
		while ((read = read()) != '<') {}
		// Ignore white space before packet type char:
		while ((read = read()) == ' ') {}
		typeChar = (char) read;
		// Read at most 20 data chars in a packet
		for (int i = 0; i < 20; i++) {
			read = read();
			if (read == ' ') { // End of parameter
				if (parameterBufferIndex > 0) {
					parameters.add(new String(parameterBuffer, 0, parameterBufferIndex));
					parameterBufferIndex = 0;
				}
			}
			else if (read == '>') { // End of packet
				break;
			}
			else {
				parameterBuffer[parameterBufferIndex++] = (char) read;
			}
		}
		if (read != '>') { // Buffer is full, but packet is not closed
			while ((read = read()) != '>') {} // Wait for packet end
		}
		if (parameterBufferIndex > 0) { // Build last parameter
			parameters.add(new String(parameterBuffer, 0, parameterBufferIndex));
		}
		return new Packet() {
			@Override
			public char getTypeChar() {
				return typeChar;
			}

			@Override
			public List<String> getRawParameters() {
				return parameters;
			}
		};
	}

}
