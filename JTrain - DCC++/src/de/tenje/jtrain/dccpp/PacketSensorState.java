package de.tenje.jtrain.dccpp;

/**
 * Parent interface for some sensor state related {@link Packet}s (list
 * states/sensor activated notification).
 * 
 * @author Jonas Tennié
 */
public interface PacketSensorState extends Packet {

	/**
	 * The type char of the <code>PacketReadSensorStates</code>.
	 */
	public static final char TYPE_CHAR = 'Q';

}
