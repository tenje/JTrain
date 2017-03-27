package de.tenje.jtrain.dccpp;

import java.util.List;

/**
 * Factory to build {@link Packet}s. Once registered, a {@link PacketBuilder} is
 * used to create <code>Packet</code>s of the builder's type.
 * 
 * @author Jonas Tennié
 */
public interface PacketFactory {

	/**
	 * Registers a new {@link PacketBuilder} to be used by
	 * {@link #buildPacket(char, List)} and {@link #buildPacket(Class, List)}.
	 * 
	 * @param <P>
	 *            The packet class type.
	 * @param builder
	 *            The builder to register.
	 * @param clazz
	 *            The packet class.
	 * @param typeChar
	 *            The type char of the packet.
	 * @throws NullPointerException
	 *             Thrown if <code>builder</code> or <code>clazz</code> is <code>null</code>.
	 * @throws IllegalStateException
	 *             Thrown if a builder for the specified <code>clazz</code> or
	 *             <code>typeChar</code> is already registered.
	 */
	<P extends Packet> void registerBuilder(PacketBuilder<P> builder, Class<P> clazz, char typeChar);

	/**
	 * Tries to find a registered {@link PacketBuilder} for the specified
	 * <code>clazz</code>. The builder will build the packet using the specified
	 * <code>data</code>.
	 * 
	 * @param <P>
	 *            The packet class type.
	 * @param packetClass
	 *            The packet class.
	 * @param parameters
	 *            The raw packet parameters.
	 * @return The built packet, or <code>null</code> if no <code>PacketBuilder</code> was
	 *         found for the specified <code>packetClass</code>.
	 * @throws IllegalArgumentException
	 *             Thrown if the packet builder cannot build the packet as there
	 *             are illegal values in <code>parameters</code>.
	 * @throws IndexOutOfBoundsException
	 *             Thrown if <code>parameters</code> does not contain enough
	 *             parameters required to build the packet.
	 */
	<P extends Packet> P buildPacket(Class<P> packetClass, List<String> parameters);

	/**
	 * Tries to find a registered {@link PacketBuilder} for the specified
	 * <code>typeChar</code>. The builder will build the packet using the specified
	 * <code>data</code>.
	 * 
	 * @param typeChar
	 *            The type char of the packet.
	 * @param parameters
	 *            The raw packet parameters.
	 * @return The built packet, or <code>null</code> if no <code>PacketBuilder</code> was
	 *         found for the specified <code>typeChar</code>.
	 * @throws NullPointerException
	 *             Thrown if <code>data</code> is <code>null</code>.
	 * @throws IllegalArgumentException
	 *             Thrown if the packet builder cannot build the packet as there
	 *             are illegal values in <code>parameters</code>.
	 * @throws IndexOutOfBoundsException
	 *             Thrown if <code>parameters</code> does not contain enough
	 *             parameters required to build the packet.
	 */
	Packet buildPacket(char typeChar, List<String> parameters);

}
