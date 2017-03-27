package de.tenje.jtrain.dccpp;

/**
 * A {@link Packet} to write a one-bit configuration variable to the decoder
 * without verifying. The packet is sent to trains/locos on the programming
 * track. A byte number in range (1-1024) and a bit number in range (0-7)
 * defines the memory location to write the variable to.
 * 
 * @author Jonas Tennié
 */
public interface PacketWriteConfigurationBitProgrammingTrack extends CallbackData, PacketWrite {

	/**
	 * The type char of the
	 * <code>PacketWriteConfigurationByteProgrammingTrack</code>.
	 */
	public static final char TYPE_CHAR = 'B';

}
