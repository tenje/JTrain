package de.tenje.jtrain.dccpp.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.tenje.jtrain.dccpp.Packet;

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
