package de.tenje.jtrain.dccpp;

import de.tenje.jtrain.Addressable;

/**
 * A {@link Packet} to write a one-bit configuration variable to the decoder
 * without verifying. The packet is sent to trains/locos on the main track. A
 * byte number in range (1-1024) and a bit number in range (0-7) defines the
 * memory location to write the variable to.
 * 
 * @author Jonas Tennié
 */
public interface PacketWriteConfigurationBitMainTrack extends Addressable, PacketWrite {

	/**
	 * The type char of the <code>PacketWriteConfigurationBitMainTrack</code>.
	 */
	public static final char TYPE_CHAR = 'b';

	/**
	 * Returns the bit location in the data byte returned by
	 * {@link #getVariableLocation()} of the value.
	 * 
	 * @return The bit location of the value.
	 */
	int getBit();

}
