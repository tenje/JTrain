package de.tenje.jtrain.dccpp;

import de.tenje.jtrain.AccessoryDecoderAddress;
import de.tenje.jtrain.Addressable;
import de.tenje.jtrain.Sensor;

/**
 * A {@link Packet} to send the data of a defined {@link Sensor}.
 * 
 * @author Jonas Tennié
 */
public interface PacketSensorData extends Addressable, RegistrationIdHolder, PacketSensorState {

	@Override
	AccessoryDecoderAddress getAddress();

	/**
	 * Returns whether the sensor is triggered.
	 * 
	 * @return <code>true</code> if triggered, else <code>false</code>.
	 */
	boolean isTriggered();

}
