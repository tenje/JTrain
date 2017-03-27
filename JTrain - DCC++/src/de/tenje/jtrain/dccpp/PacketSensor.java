package de.tenje.jtrain.dccpp;

/**
 * Parent interface for all sensor related {@link Packet}s (define/delete/list).
 * 
 * @author Jonas Tenni�
 */
public interface PacketSensor extends Packet {

	/**
	 * The type char of the <code>PacketSensor</code>.
	 */
	public static final char TYPE_CHAR = 'S';

}
