package de.tenje.jtrain.dccpp;

/**
 * {@link Packet} to turn on power on the tracks.
 * 
 * @author Jonas Tennié
 */
public interface PacketTrackPowerOn extends Packet {

	/**
	 * The type char of the <code>PacketTurnOnTrackPower</code>.
	 */
	public static final char TYPE_CHAR = '1';

}
