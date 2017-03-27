package de.tenje.jtrain.dccpp;

/**
 * {@link Packet} to read the state of the DCC++ base station.
 * 
 * @author Jonas Tennié
 */
public interface PacketReadStationState extends Packet {

	/**
	 * The type char of the <code>PacketReadStationState</code>.
	 */
	public static final char TYPE_CHAR = 's';

}
