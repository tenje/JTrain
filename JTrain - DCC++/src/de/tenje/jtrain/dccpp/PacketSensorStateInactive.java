package de.tenje.jtrain.dccpp;

/**
 * A {@link Packet} to notify that a sensor is inactive/no longer triggered.
 * This interface is not a sub interface of the {@link PacketSensorState}
 * interface group as this packet type has an other type char.
 * 
 * @author Jonas Tennié
 */
public interface PacketSensorStateInactive extends RegistrationIdHolder, Packet {

	/**
	 * The type char of the <code>PacketSensorInactive</code>.
	 */
	public static final char TYPE_CHAR = 'q';

}
