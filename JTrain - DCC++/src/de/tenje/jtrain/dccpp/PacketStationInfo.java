package de.tenje.jtrain.dccpp;

/**
 * A {@link Packet} which holds information about the DCC++ base station.
 * 
 * @author Jonas Tennié
 */
public interface PacketStationInfo extends Packet {

	/**
	 * The type char of the <code>PacketStationInfo</code>.
	 */
	public static final char TYPE_CHAR = 'i';

	/**
	 * Returns the station info string. The string is a string of all parameters
	 * separated by a space.
	 * 
	 * @return The station info string.
	 */
	String getInfo();

}
