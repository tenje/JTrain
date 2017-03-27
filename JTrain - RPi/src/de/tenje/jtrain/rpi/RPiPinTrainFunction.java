package de.tenje.jtrain.rpi;

import java.util.Objects;

import com.pi4j.io.gpio.GpioPinDigitalOutput;

import de.tenje.jtrain.AbstractSwitchable;
import de.tenje.jtrain.TrainFunction;

/**
 * A {@link TrainFunction} which controls an {@link GpioPinDigitalOutput}.
 * Switching the function on will set the pin state to HIGH. Switching the
 * function off will set the pin state to LOW.
 * 
 * @author Jonas Tennié
 */
public class RPiPinTrainFunction extends AbstractSwitchable implements TrainFunction {

	private final GpioPinDigitalOutput pin;

	/**
	 * Constructs a new {@link RPiPinTrainFunction} with the specified output
	 * pin.
	 * 
	 * @param pin
	 *            The output pin to control.
	 */
	public RPiPinTrainFunction(GpioPinDigitalOutput pin) {
		this.pin = Objects.requireNonNull(pin, "pin");
	}

	/**
	 * @param switched
	 *            <code>true</code> to set pin state to HIGH, <code>false</code> to set
	 *            pin state to LOW.
	 */
	@Override
	public void setSwitched(boolean switched) {
		if (switched) {
			pin.high();
		}
		else {
			pin.low();
		}
	}

	/**
	 * Returns the current pin state.
	 * 
	 * @return <code>true</code> for HIGH, <code>false</code> for LOW.
	 */
	@Override
	public boolean isSwitched() {
		return pin.isHigh();
	}

}
