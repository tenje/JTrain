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

import java.util.ArrayList;
import java.util.List;

import org.tenje.jtrain.AbstractTrain;
import org.tenje.jtrain.LongTrainAddress;
import org.tenje.jtrain.Train;
import org.tenje.jtrain.TrainDirection;

import com.pi4j.wiringpi.Gpio;
import com.pi4j.wiringpi.SoftPwm;

/**
 * A {@link Train} controlled by a Raspberry Pi. Acceleration is supported by
 * this implementation.
 * 
 * @author Jonas Tennié
 */
public class RPiTrain extends AbstractTrain {

	private final List<RPiPinTrainFunctionDirectionDepend> functions = new ArrayList<>();

	private final int forwardPin, reversePin;
	private volatile int currentPin;
	private volatile Thread thread;
	private TrainDirection direction = TrainDirection.FORWARD;
	private final double acceleration; // Step changes per 1/10 sec
	private final double minPower; // motor power in % for speed step 1
	private final double maxPower; // motor power in % for speed step 126
	private final double powerRange; // maxSpeed - minSpeed
	private volatile double currentSpeed, targetSpeed;

	/**
	 * Constructs a new {@link RPiTrain} with the specified address, control
	 * pins and motor configuration. {@link Gpio#wiringPiSetup()} must be called
	 * before.
	 * 
	 * @param address
	 *            The address of the train.
	 * @param forwardPin
	 *            The pin to move the train in forward direction.
	 * @param reversePin
	 *            The pin to move the train in reverse direction.
	 * @param acceleration
	 *            The acceleration of the train in
	 *            <code>speed steps/second</code>. A value of <code>126</code> means
	 *            that the train speeds up from zero to 126 in one second.
	 * @param minPower
	 *            The minimal motor power in percent of the total motor power.
	 *            The motor is powered with <code>minPower</code> if train speed is
	 *            one.
	 * @param maxPower
	 *            The maximal motor power in percent of the total motor power.
	 *            The motor is powered with <code>maxPower</code> if train speed is
	 *            126.
	 */
	public RPiTrain(LongTrainAddress address, int forwardPin, int reversePin, int acceleration, int minPower,
			int maxPower) {
		super(address);
		this.forwardPin = forwardPin;
		this.reversePin = reversePin;
		currentPin = forwardPin;
		if (acceleration <= 0) {
			throw new IllegalArgumentException("acceleration must be higher than zero");
		}
		if (minPower < 0 || minPower > 100) {
			throw new IllegalArgumentException("minSpeed out of valid range: " + minPower);
		}
		if (maxPower < 0 || maxPower > 100) {
			throw new IllegalArgumentException("maxSpeed out of valid range: " + maxPower);
		}
		if (maxPower < minPower) {
			throw new IllegalArgumentException("maxSpeed cannot be smaller than minSpeed");
		}
		// Convert step/sec to step / (1/10) sec
		this.acceleration = acceleration / 10D;
		this.minPower = minPower;
		this.maxPower = maxPower;
		powerRange = this.maxPower - this.minPower;
	}

	void addFunction(RPiPinTrainFunctionDirectionDepend function) {
		functions.add(function);
	}

	@Override
	public void setSpeed(int speed, boolean hard) {
		if (speed < -1 || speed > 126) {
			throw new IllegalArgumentException("speed out of valid range: " + speed);
		}
		if (hard) {
			stop();
			if (speed > 0) {
				currentSpeed = speed;
				targetSpeed = speed;
				SoftPwm.softPwmCreate(currentPin, (int) (minPower + powerRange / 126 * currentSpeed), 100);
			}
		}
		else {
			if (speed == -1) { // Emergency stop
				setSpeed(-1, true);
			}
			else {
				targetSpeed = speed;
				if (thread == null) {
					startThread();
				}
			}
		}
	}

	@Override
	public int getCurrentSpeed() {
		return (int) currentSpeed;
	}

	@Override
	public int getTargetSpeed() {
		return (int) targetSpeed;
	}

	@Override
	public TrainDirection getDirection() {
		return direction;
	}

	@Override
	public void setDirection(TrainDirection direction) {
		if (this.direction != direction) {
			setSpeed(0, true);
			this.direction = direction;
			currentPin = direction == TrainDirection.FORWARD ? forwardPin : reversePin;
			for (RPiPinTrainFunctionDirectionDepend function : functions) {
				function.update();
			}
		}
	}

	private void startThread() {
		thread = new Thread() {
			@Override
			public void run() {
				SoftPwm.softPwmCreate(currentPin, 0, 100);
				double power;
				while (!isInterrupted() && Math.abs(currentSpeed - targetSpeed) > 0.1) {
					if (currentSpeed < targetSpeed) { // Speed up
						currentSpeed += acceleration;
						if (currentSpeed > targetSpeed) {
							currentSpeed = targetSpeed;
						}
					}
					else { // Slow down
						currentSpeed -= acceleration;
						if (currentSpeed < targetSpeed) {
							currentSpeed = targetSpeed;
						}
					}
					// Speed is motor power in %. speed = 0 means motor stops,
					// speed = 100 means full motor power
					power = minPower + powerRange / 126 * currentSpeed;
					SoftPwm.softPwmWrite(currentPin, (int) power);
					try {
						Thread.sleep(100);
					}
					catch (InterruptedException e) {
						break;
					}
				}
				if (targetSpeed == 0) {
					SoftPwm.softPwmStop(currentPin);
				}
				thread = null;
			}
		};
		thread.start();
	}

	/**
	 * Stops the train and its threads used for speed control.
	 */
	public void stop() {
		SoftPwm.softPwmStop(currentPin);
		if (thread != null) {
			thread.interrupt(); // Stop thread
			while (thread.isAlive()) {
				;
			}
		}
	}

}
