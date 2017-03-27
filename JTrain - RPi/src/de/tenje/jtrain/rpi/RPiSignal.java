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

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;

import com.pi4j.io.gpio.GpioPinDigitalOutput;

import de.tenje.jtrain.AccessoryDecoderAddress;
import de.tenje.jtrain.Signal;
import de.tenje.jtrain.SignalAspect;

/**
 * A {@link Signal} which is controlled by Raspberry Pi GPIO pins.
 * 
 * @author Jonas Tennié
 */
public class RPiSignal implements Signal {

	private final AccessoryDecoderAddress address;
	private final Map<SignalAspect, GpioPinDigitalOutput> pins = new EnumMap<>(
			SignalAspect.class);
	private final Set<SignalAspect> supportedAspects;
	private SignalAspect aspect;

	/**
	 * Constructs a new {@link RPiSignal} with the specified address and signal
	 * aspect/pin entries.
	 * 
	 * @param address
	 *            The address.
	 * 
	 * @param pins
	 *            The supported signal aspects as key with their related output
	 *            pins as value.
	 * @throws NullPointerException
	 *             Thrown if <code>address</code>, <code>pins</code> or any key
	 *             or value in <code>pins</code> is <code>null</code>.
	 */
	public RPiSignal(AccessoryDecoderAddress address,
			Map<SignalAspect, GpioPinDigitalOutput> pins) {
		this.address = Objects.requireNonNull(address, "address");
		Objects.requireNonNull(pins, "pins");
		for (Entry<SignalAspect, GpioPinDigitalOutput> entry : pins.entrySet()) {
			Objects.requireNonNull(entry.getKey(), "key in pins");
			Objects.requireNonNull(entry.getValue(), "value in pins");
			this.pins.put(entry.getKey(), entry.getValue());
		}
		supportedAspects = Collections.unmodifiableSet(pins.keySet());
	}

	@Override
	public AccessoryDecoderAddress getAddress() {
		return address;
	}

	@Override
	public void setAspect(SignalAspect aspect) {
		Objects.requireNonNull(aspect, "aspect");
		if (!pins.containsKey(aspect)) {
			throw new UnsupportedOperationException("aspect not supported: " + aspect);
		}
		this.aspect = aspect;
		for (Entry<SignalAspect, GpioPinDigitalOutput> entry : pins.entrySet()) {
			if (entry.getKey() == aspect) {
				entry.getValue().high();
			}
			else {
				entry.getValue().low();
			}
		}
	}

	@Override
	public SignalAspect getAspect() {
		return aspect;
	}

	@Override
	public Set<SignalAspect> getSupportedAspects() {
		return supportedAspects;
	}

}
