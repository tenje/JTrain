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
package org.tenje.jtrain.dccpp.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.tenje.jtrain.dccpp.Packet;

/**
 * This class provides a skeletal implementation of the {@link Packet} interface
 * to minimize the effort required to implement this interface.
 * 
 * @author Jonas Tennié
 */
public abstract class AbstractPacket implements Packet {

	private final char typeChar;
	private final List<String> parameters;

	/**
	 * Constructs a new {@link AbstractPacket} with no parameters.
	 * 
	 * @param typeChar
	 *            The type char of the packet.
	 */
	public AbstractPacket(char typeChar) {
		this.typeChar = typeChar;
		parameters = Collections.emptyList();
	}

	/**
	 * Constructs a new <code>AbstractPacket</code> with the specified raw packet
	 * parameters. The parameter list is copied and will be no longer related to
	 * the passed list.
	 * 
	 * @param typeChar
	 *            The type char of the packet.
	 * 
	 * @param parameters
	 *            The packet parameters as described in
	 *            {@link Packet#getRawParameters()}.
	 */
	public AbstractPacket(char typeChar, List<String> parameters) {
		this.typeChar = typeChar;
		if (parameters == null || parameters.isEmpty()) {
			this.parameters = Collections.emptyList();
		}
		else {
			this.parameters = Collections.unmodifiableList(new ArrayList<>(parameters));
		}
	}

	@Override
	public String toString() {
		return getClass().getName() + "[typeChar=" + getTypeChar() + ", parameters: " + parameters + "]";
	}

	@Override
	public char getTypeChar() {
		return typeChar;
	}

	@Override
	public List<String> getRawParameters() {
		return parameters;
	}

}
