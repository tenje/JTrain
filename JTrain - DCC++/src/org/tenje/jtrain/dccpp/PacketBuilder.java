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
package org.tenje.jtrain.dccpp;

import java.util.List;

import org.tenje.jtrain.dccpp.impl.PacketFactoryImpl;

/**
 * A builder to build {@link Packet}s out of raw parameters. The raw parameters
 * are stored in a {@link List}&lt;{@link String}&gt;. A raw parameter is a
 * string containing only alphanumeric chars.
 * 
 * @author Jonas Tennié
 * @param <P>
 *            The packet type of <code>Packet</code>s built by this builder.
 * @see PacketFactoryImpl
 */
public interface PacketBuilder<P extends Packet> {

	/**
	 * Builds a {@link Packet} using the specified <code>data</code>.
	 * 
	 * @param parameters
	 *            The raw packet parameters.
	 * @return The built packet.
	 * @throws NullPointerException
	 *             Thrown if <code>parameters</code> is <code>null</code>, but required or
	 *             if one of the required elements in <code>parameters</code> is
	 *             <code>null</code>.
	 * @throws IndexOutOfBoundsException
	 *             Thrown if <code>parameters</code> does not contain enough
	 *             parameters required to build the packet.
	 * @throws NumberFormatException
	 *             Thrown if a parameter is required to be a number, but it is
	 *             not a number (or <code>null</code>).
	 * @throws IllegalArgumentException
	 *             Thrown if one of the values in <code>parameters</code> is illegal.
	 */
	P build(List<String> parameters);

}
