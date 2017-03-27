package de.tenje.jtrain.rpi;

import java.util.Objects;

import com.pi4j.io.gpio.GpioPinDigitalOutput;

import de.tenje.jtrain.AbstractSwitchable;
import de.tenje.jtrain.OutputPin;
import de.tenje.jtrain.OutputPinAddress;

/**
 * An {@link OutputPin} as wrapper for a {@link GpioPinDigitalOutput}.
 * 
 * @author Jonas Tennié
 */
public class RPiOutputPin extends AbstractSwitchable implements OutputPin {

	private final OutputPinAddress address;
	private final GpioPinDigitalOutput pin;

	/**
	 * Constructs a new {@link RPiOutputPin} as wrapper for the specified pin.
	 * 
	 * @param pin
	 *            The pin to wrap.
	 */
	public RPiOutputPin(GpioPinDigitalOutput pin) {
		this.pin = Objects.requireNonNull(pin, "pin");
		address = new OutputPinAddress(pin.getPin().getAddress());
	}

	@Override
	public OutputPinAddress getAddress() {
		return address;
	}

	@Override
	public void setSwitched(boolean state) {
		pin.setState(state);
	}

	@Override
	public boolean isSwitched() {
		return pin.isHigh();
	}

}
