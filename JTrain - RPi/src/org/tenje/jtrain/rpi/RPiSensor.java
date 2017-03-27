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

import org.tenje.jtrain.AbstractSensor;
import org.tenje.jtrain.AccessoryDecoderAddress;
import org.tenje.jtrain.Sensor;

import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;

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
