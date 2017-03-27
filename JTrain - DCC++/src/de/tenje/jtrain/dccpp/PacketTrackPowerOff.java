package de.tenje.jtrain.dccpp;

/**
 * {@link Packet} to turn off power on the tracks.
 * 
 * @author Jonas Tenni�
 */
public interface PacketTrackPowerOff extends Packet {

	/**
	 * The type char of the <code>PacketTurnOffTrackPower</code>.
	 */
	public static final char TYPE_CHAR = '0';

}
