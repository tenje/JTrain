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
import java.io.OutputStream;
import java.util.List;
import java.util.Objects;

import de.tenje.jtrain.dccpp.Packet;

/**
 * An {@link OutputStream} which writes {@link Packet}s to an other underlying
 * (handled) output stream.
 * 
 * @author Jonas Tennié
 */
public class PacketOutputStream extends OutputStream {

	private OutputStream handle;

	/**
	 * Constructs a new {@link PacketOutputStream} with the specified handled
	 * output stream.
	 * 
	 * @param handle
	 *            The handled output stream.
	 * @throws NullPointerException
	 *             Thrown if <code>handle</code> is <code>null</code>.
	 */
	public PacketOutputStream(OutputStream handle) {
		this.handle = Objects.requireNonNull(handle, "handle");
	}

	@Override
	public void write(byte[] b, int off, int len) throws IOException {
		handle.write(b, off, len);
	}

	@Override
	public void write(int b) throws IOException {
		handle.write(b);
	}

	/**
	 * Writes a {@link Packet} to the output stream using the following format:
	 * <i>&lt;t PARAM0 PARAM1&gt;</i>, where 't' is the type char and
	 * <i>PARAM0</i> and <i>PARAM1</i> are some packet parameters.
	 * 
	 * @param packet
	 *            The packet to write.
	 * @throws IOException
	 *             Thrown if an I/O error occurs while writing to the underlying
	 *             output stream.
	 * @throws NullPointerException
	 *             Thrown if <code>packet</code> is <code>null</code>.
	 */
	public void writePacket(Packet packet) throws IOException {
		Objects.requireNonNull(packet, "packet");
		List<String> parameters = packet.getRawParameters();
		String parameter;
		write('<');
		write(packet.getTypeChar());
		for (int i = 0; i < parameters.size(); i++) {
			write(' ');
			parameter = parameters.get(i);
			for (int j = 0; j < parameter.length(); j++) {
				write(parameter.charAt(j));
			}
		}
		write('>');
	}

}
