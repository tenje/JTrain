/*******************************************************************************
 * Copyright (c): Jonas Tennié 2017
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Lesser Public License for more
 * details.
 * You should have received a copy of the GNU General Lesser Public License
 * along with this program. If not, see
 * <http://www.gnu.org/licenses/lgpl-3.0.html>.
 *******************************************************************************/
package org.tenje.jtrain.rpi;

import java.util.Objects;

import org.tenje.jtrain.AbstractSwitchable;
import org.tenje.jtrain.OutputPin;
import org.tenje.jtrain.OutputPinAddress;

import com.pi4j.io.gpio.GpioPinDigitalOutput;

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
