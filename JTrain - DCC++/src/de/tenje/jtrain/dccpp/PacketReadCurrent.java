package de.tenje.jtrain.dccpp;

/**
 * {@link Packet} to read current being drawn on main operations track.
 * 
 * @author Jonas Tennié
 */
public interface PacketReadCurrent extends Packet {

	/**
	 * The type char of the <code>PacketReadCurrent</code>.
	 */
	public static final char TYPE_CHAR = 'c';

}
