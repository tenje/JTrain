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
