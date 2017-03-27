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
import java.util.Timer;
import java.util.TimerTask;

import com.pi4j.io.gpio.GpioPinDigitalOutput;

import de.tenje.jtrain.AbstractSwitchable;
import de.tenje.jtrain.AccessoryDecoderAddress;
import de.tenje.jtrain.Turnout;

/**
 * A {@link Turnout} which is controlled by two Raspberry Pi GPIO pins.
 * 
 * @author Jonas Tennié
 */
public class RPiTurnout extends AbstractSwitchable implements Turnout {

	private static final Timer TIMER = new Timer();

	private final AccessoryDecoderAddress address;
	private final GpioPinDigitalOutput straightPin, thrownPin;
	private final long switchTime;
	private boolean switched;
	private TimerTask currentTask;

	/**
	 * Constructs a new {@link RPiTurnout} with the specified address and
	 * control pins.
	 * 
	 * @param address
	 *            The address of the turnout.
	 * @param straightPin
	 *            The pin to set the turnout to straight.
	 * @param thrownPin
	 *            The pin to set the turnout to thrown.
	 * @param switchTime
	 *            The time a control pin is HIGH to switch in ms. A value
	 *            smaller than one means that the pin is HIGH until the throw
	 *            state should change.
	 */
	public RPiTurnout(AccessoryDecoderAddress address, GpioPinDigitalOutput straightPin,
			GpioPinDigitalOutput thrownPin, int switchTime) {
		this.address = Objects.requireNonNull(address, "address");
		this.straightPin = Objects.requireNonNull(straightPin, "straightPin");
		this.thrownPin = Objects.requireNonNull(thrownPin, "thrownPin");
		if (switchTime < 0) {
			switchTime = 0;
		}
		this.switchTime = switchTime;
	}

	@Override
	public AccessoryDecoderAddress getAddress() {
		return address;
	}

	@Override
	public void setSwitched(boolean switched) {
		if (currentTask != null) {
			currentTask.cancel();
		}
		if (switched) {
			straightPin.low();
			thrownPin.high();
			if (switchTime != 0) {
				currentTask = new TimerTask() {
					@Override
					public void run() {
						thrownPin.low();
					}
				};
				TIMER.schedule(currentTask, switchTime);
			}
		}
		else {
			thrownPin.low();
			straightPin.high();
			if (switchTime != 0) {
				currentTask = new TimerTask() {
					@Override
					public void run() {
						straightPin.low();
					}
				};
				TIMER.schedule(currentTask, switchTime);
			}
		}
	}

	@Override
	public boolean isSwitched() {
		return switched;
	}

}
