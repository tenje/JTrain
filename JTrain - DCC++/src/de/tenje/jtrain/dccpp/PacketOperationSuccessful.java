package de.tenje.jtrain.dccpp;

/**
 * A return packet to notify that a requested operation was successful.
 * 
 * @author Jonas Tenni�
 */
public interface PacketOperationSuccessful extends Packet {

	/**
	 * The type char of the {@link PacketOperationSuccessful}.
	 */
	public static final char TYPE_CHAR = 'O';

}
