package de.tenje.jtrain.rpi;

import java.util.Objects;

import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;

import de.tenje.jtrain.AbstractSensor;
import de.tenje.jtrain.AccessoryDecoderAddress;
import de.tenje.jtrain.Sensor;

/**
 * A {@link Sensor} as wrapper for a {@link GpioPinDigitalInput}. The address is
 * not related to the input pin address.
 * 
 * @author Jonas Tennié
 */
public class RPiSensor extends AbstractSensor implements GpioPinListenerDigital {

	private final GpioPinDigitalInput pin;

	/**
	 * Constructs a new {@link RPiSensor} with the specified address and input
	 * pin. The address is not related to the input pin address.
	 * 
	 * @param address
	 *            The address of the sensor.
	 * 
	 * @param pin
	 *            The pin to listen to.
	 */
	public RPiSensor(AccessoryDecoderAddress address, GpioPinDigitalInput pin) {
		super(address);
		this.pin = Objects.requireNonNull(pin, "pin");
		pin.addListener(this);
	}

	@Override
	public boolean isTriggered() {
		return pin.isHigh();
	}

	@Override
	public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
		notifyListeners();
	}

}
