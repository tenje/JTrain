package de.tenje.jtrain.dccpp;

/**
 * {@link Packet} to store settings for turn-outs and sensors to the
 * non-volatile memory.
 * 
 * @author Jonas Tennié
 */
public interface PacketStoreSettings extends Packet {

	/**
	 * The type char of the <code>PacketStoreSettings</code>.
	 */
	public static final char TYPE_CHAR = 'E';

}
