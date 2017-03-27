package de.tenje.jtrain.dccpp;

import java.util.List;

import de.tenje.jtrain.dccpp.impl.PacketFactoryImpl;

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
