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
import org.tenje.jtrain.TrainDirection;
import org.tenje.jtrain.TrainFunction;

import com.pi4j.io.gpio.GpioPinDigitalOutput;

/**
 * A {@link RPiPinTrainFunction} which is only enabled if a specified
 * {@link RPiTrain} has a specific {@link TrainDirection}.
 * 
 * @author Jonas Tennié
 */
public class RPiPinTrainFunctionDirectionDepend extends AbstractSwitchable implements TrainFunction {

	private final GpioPinDigitalOutput forwardPin, reversePin;
	private final RPiTrain train;
	private boolean switched;

	/**
	 * Constructs a new {@link RPiPinTrainFunctionDirectionDepend} with the
	 * specified output pin for the specified train and direction.
	 * 
	 * @param pin
	 *            The output pin to control.
	 * @param direction
	 *            The direction to enable the function.
	 * @param train
	 *            The train to use its direction.
	 * @throws NullPointerException
	 *             Thrown if <code>pin</code>, <code>direction</code> or <code>train</code> is
	 *             <code>null</code>.
	 */
	public RPiPinTrainFunctionDirectionDepend(GpioPinDigitalOutput pin, TrainDirection direction, RPiTrain train) {
		Objects.requireNonNull(pin, "pin");
		Objects.requireNonNull(direction, "direction");
		this.train = Objects.requireNonNull(train, "train");
		if (direction == TrainDirection.FORWARD) {
			forwardPin = pin;
			reversePin = null;
		}
		else {
			forwardPin = null;
			reversePin = pin;
		}
		train.addFunction(this);
	}

	/**
	 * Constructs a new {@link RPiPinTrainFunctionDirectionDepend} with the
	 * specified output pins for forward and reverse.
	 * 
	 * @param forwardPin
	 *            The pin which should be set to HIGHT when train direction is
	 *            forward.
	 * @param reversePin
	 *            The pin which should be set to HIGHT when train direction is
	 *            reverse.
	 * @param train
	 *            The train to use its direction.
	 * @throws NullPointerException
	 *             Thrown if both, <code>forwardPin</code> and <code>reversePin</code> are
	 *             <code>null</code> or if <code>train</code> is <code>null</code>.
	 */
	public RPiPinTrainFunctionDirectionDepend(GpioPinDigitalOutput forwardPin, GpioPinDigitalOutput reversePin,
			RPiTrain train) {
		if (forwardPin == null && reversePin == null) {
			throw new NullPointerException("forwardPin and reversePin cannot both be null");
		}
		this.train = Objects.requireNonNull(train, "train");
		this.forwardPin = forwardPin;
		this.reversePin = reversePin;
		train.addFunction(this);
	}

	void update() {
		if (train.getDirection() == TrainDirection.FORWARD) {
			setPinState(forwardPin, switched);
			setPinState(reversePin, false);
		}
		else {
			setPinState(forwardPin, false);
			setPinState(reversePin, switched);
		}
	}

	private void setPinState(GpioPinDigitalOutput pin, boolean state) {
		if (pin != null) {
			pin.setState(state);
		}
	}

	@Override
	public void setSwitched(boolean switched) {
		this.switched = switched;
		update();
	}

	@Override
	public boolean isSwitched() {
		return switched;
	}

}
