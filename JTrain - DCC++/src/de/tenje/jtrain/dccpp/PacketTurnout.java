package de.tenje.jtrain.dccpp;

/**
 * Parent interface for all turn out related {@link Packet}s.
 * 
 * @author Jonas Tennié
 */
public interface PacketTurnout extends Packet {

	/**
	 * The type char of the <code>PacketTurnOut</code>.
	 */
	public static final char TYPE_CHAR = 'T';

}
