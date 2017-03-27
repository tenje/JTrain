package de.tenje.jtrain.dccpp;

import de.tenje.jtrain.AccessoryDecoderAddress;
import de.tenje.jtrain.Addressable;

/**
 * {@link PacketSensor} to define a sensor.
 * 
 * @author Jonas Tennié
 */
public interface PacketSensorDefine extends Addressable, RegistrationIdHolder, PacketSensor {

	@Override
	AccessoryDecoderAddress getAddress();

	/**
	 * Returns if an internal pull-up resistor should be used for the sensor.
	 * This parameter is used for the Arduino.
	 * 
	 * @return <code>true</code> if internal pull-up resister should be used, else
	 *         <code>false</code>.
	 * @deprecated As this is an Arduino-only parameter.
	 */
	@Deprecated
	boolean usePullUp();

}
