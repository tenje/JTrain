package de.tenje.jtrain.dccpp;

/**
 * {@link Packet} to read current being drawn on main operations track.
 * 
 * @author Jonas Tenni�
 */
public interface PacketReadCurrent extends Packet {

	/**
	 * The type char of the <code>PacketReadCurrent</code>.
	 */
	public static final char TYPE_CHAR = 'c';

}
