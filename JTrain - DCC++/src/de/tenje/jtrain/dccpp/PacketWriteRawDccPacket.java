package de.tenje.jtrain.dccpp;

/**
 * {@link Packet} to write a raw DCC packet containing 2-5 bytes.
 * 
 * @author Jonas Tennié
 */
public interface PacketWriteRawDccPacket extends Packet, Registrable {

	/**
	 * The type char of the <code>PacketWriteRawDccPacket</code>.
	 */
	public static final char TYPE_CHAR = 'M';

	/**
	 * Returns the byte at the given <code>index</code>. The returned byte has range
	 * (0-255).
	 * 
	 * @param index
	 *            The index of the byte to get.
	 * @return The byte at the given <code>index</code>.
	 * @throws IndexOutOfBoundsException
	 *             If no byte is defined for the given <code>index</code>.
	 */
	int getByte(int index);

}
