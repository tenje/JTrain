package de.tenje.jtrain.dccpp;

/**
 * Packet to list the packet contents of the main operations track registers and
 * the programming track registers. For diagnostic and testing use only.
 * 
 * @author Jonas Tennié
 */
public interface PacketListBitContents extends Packet {

	/**
	 * The type char of the <code>PacketListBitContents</code>.
	 */
	public static final char TYPE_CHAR = 'L';

}
