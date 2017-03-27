package de.tenje.jtrain.dccpp;

/**
 * A super interface for all {@link Packet}s to write to a decoder.
 * 
 * @author Jonas Tennié
 */
public interface PacketWrite extends Packet {

	/**
	 * Returns the memory location of the variable to write.
	 * 
	 * @return The memory location of the variable to write.
	 */
	int getVariableLocation();

	/**
	 * Returns the value of the variable to write in range (0-255) when writing
	 * a byte or 0 or 1 when writing a bit.
	 * 
	 * @return The value of the variable to write.
	 */
	int getValue();

}
