package de.tenje.jtrain.dccpp;

/**
 * Parent interface for all output pin related {@link Packet}s
 * (define/delete/list/set state).
 * 
 * @author Jonas Tennié
 */
public interface PacketOutputPin extends Packet {

	/**
	 * The type char of the <code>PacketOutputPin</code>.
	 */
	public static final char TYPE_CHAR = 'Z';

}
