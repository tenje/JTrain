package de.tenje.jtrain.dccpp;

import de.tenje.jtrain.Addressable;

/**
 * A {@link Packet} to write a one-byte configuration variable to the decoder
 * without verifying. The packet is sent to trains/locos on the main track. A
 * number in range (1-1024) defines the memory location to write the variable
 * to.
 * 
 * @author Jonas Tennié
 */
public interface PacketWriteConfigurationByteMainTrack extends Addressable, PacketWrite {

	/**
	 * The type char of the <code>PacketWriteConfigurationByteMainTrack</code>.
	 */
	public static final char TYPE_CHAR = 'w';

}
