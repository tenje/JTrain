package org.tenje.jtrain.rpi;

import java.io.IOException;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import org.tenje.jtrain.AbstractSwitchable;
import org.tenje.jtrain.AccessoryDecoderAddress;
import org.tenje.jtrain.Turnout;

/**
 * A {@link Turnout} which controls a servo connected to a Raspberry Pi.
 * 
 * @author Jonas Tennié
 */
public class RPiServoTurnout extends AbstractSwitchable implements Turnout {

	private static final Timer TIMER = new Timer();

	private final AccessoryDecoderAddress address;
	private final int pin, straightTime, thrownTime;
	private final long switchTime;
	private boolean switched;
	private TimerTask currentTask;

	/**
	 * Constructs a new {@link RPiServoTurnout} with the specified address, pin,
	 * PWM times and switch time.
	 * 
	 * @param address
	 *            The address of the turnout.
	 * @param pin
	 *            The pin to control. Using wiringPi scheme.
	 * @param straightTime
	 *            The PWM on time for straight position in ms.
	 * @param thrownTime
	 *            The PWM on time for thrown position in ms.
	 * @param switchTime
	 *            The switch time. In this time the servo moves. Zero for
	 *            infinity move.
	 * @throws NullPointerException
	 *             Thrown if <code>address</code> is <code>null</code>.
	 * @throws IllegalArgumentException
	 *             Thrown if <code>straightTime</code> or
	 *             <code>thrownTime</code> is zero or smaller or higher than
	 *             ten.
	 */
	public RPiServoTurnout(AccessoryDecoderAddress address, int pin, double straightTime,
			double thrownTime, int switchTime) {
		this.address = Objects.requireNonNull(address, "address");
		this.pin = pin;
		if (straightTime <= 0 || straightTime > 10) {
			throw new IllegalArgumentException(
					"straightTime out of valid range: " + straightTime);
		}
		if (thrownTime <= 0 || thrownTime > 10) {
			throw new IllegalArgumentException(
					"thrownTime out of valid range: " + thrownTime);
		}
		this.straightTime = (int) (straightTime * 100);
		this.thrownTime = (int) (thrownTime * 100);
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
		final Runtime runtime = Runtime.getRuntime();
		this.switched = switched;
		if (currentTask != null) {
			currentTask.cancel();
		}
		try {
			runtime.exec("gpio mode " + pin + " pwm");
			runtime.exec("gpio pwm-ms");
			runtime.exec("gpio pwmc 192");
			runtime.exec("gpio pwmr 2000");
			runtime.exec("gpio pwm 1 " + (switched ? thrownTime : straightTime));
		}
		catch (IOException ex) {}
		if (switchTime != 0) {
			currentTask = new TimerTask() {
				@Override
				public void run() {
					try {
						runtime.exec("gpio mode " + pin + " out");
						runtime.exec("gpio write " + pin + " 0");
					}
					catch (IOException e) {}
				}
			};
			TIMER.schedule(currentTask, switchTime);
		}
	}

	@Override
	public boolean isSwitched() {
		return switched;
	}

}
