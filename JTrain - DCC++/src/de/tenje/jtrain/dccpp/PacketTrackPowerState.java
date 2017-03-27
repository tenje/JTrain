package de.tenje.jtrain.dccpp;

/**
 * {@link Packet} to return the power state of the tracks.
 * 
 * @author Jonas Tennié
 */
public interface PacketTrackPowerState extends Packet {

	/**
	 * The type char of the {@link PacketTrackPowerState}.
	 */
	public static final char TYPE_CHAR = 'p';

	/**
	 * Returns if the rails are powered.
	 * 
	 * @return <code>true</code> if powered, else <code>false</code>.
	 */
	boolean isPowered();

}
