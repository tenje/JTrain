package de.tenje.jtrain.dccpp;

/**
 * A {@link Packet} to read the value of a data byte of a train/loco on the
 * programming track.
 * 
 * @author Jonas Tennié
 */
public interface PacketReadConfigurationByte extends CallbackData, Packet {

	/**
	 * The type char of the <code>PacketReadConfigurationByte</code>.
	 */
	public static final char TYPE_CHAR = 'R';

	/**
	 * Returns the memory location of the variable to read.
	 * 
	 * @return The memory location of the variable to read.
	 */
	int getVariableLocation();

}
