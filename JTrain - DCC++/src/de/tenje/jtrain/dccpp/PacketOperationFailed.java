package de.tenje.jtrain.dccpp;

/**
 * A return packet to notify that a requested operation was not successful for
 * some reason.
 * 
 * @author Jonas Tennié
 */
public interface PacketOperationFailed extends Packet {

	/**
	 * The type char of the {@link PacketOperationFailed}.
	 */
	public static final char TYPE_CHAR = 'X';

}
