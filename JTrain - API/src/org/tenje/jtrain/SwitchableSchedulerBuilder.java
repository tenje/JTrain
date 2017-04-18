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
package org.tenje.jtrain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A builder to build a {@link SwitchableScheduler}.
 * 
 * @author Jonas Tennié
 */
public class SwitchableSchedulerBuilder {

	// ArrayList as #clone() is required
	private final ArrayList<Runnable> scheduler = new ArrayList<>();

	/**
	 * Causes the scheduler to sleep for <code>millis</code> milliseconds.
	 * 
	 * @param millis
	 *            The length of time to sleep in milliseconds.
	 * @return This builder.
	 * @throws IllegalArgumentException
	 *             Thrown if <code>millis</code> is smaller than zero.
	 */
	public SwitchableSchedulerBuilder sleep(long millis) {
		if (millis < 0) {
			throw new IllegalArgumentException("millis cannot be smaller than zero");
		}
		if (millis > 0) {
			if (scheduler.size() != 0
					&& scheduler.get(scheduler.size() - 1) instanceof SleepRunnable) {
				SleepRunnable runnable = (SleepRunnable) scheduler
						.get(scheduler.size() - 1);
				runnable.millis += millis;
			}
			else {
				SleepRunnable runnable = new SleepRunnable();
				runnable.millis = millis;
				scheduler.add(runnable);
			}
		}
		return this;
	}

	/**
	 * Adds a switch state change to be executed by the scheduler.
	 * 
	 * @param switchable
	 *            The switchable.
	 * @param state
	 *            The state to set to the <code>switchable</code>
	 * @return This builder.
	 */
	public SwitchableSchedulerBuilder setState(Switchable switchable, boolean state) {
		Objects.requireNonNull(switchable, "switchable");
		SwitchRunnable runnable = new SwitchRunnable();
		runnable.switchable = switchable;
		runnable.state = state;
		scheduler.add(runnable);
		return this;
	}

	/**
	 * Builds the {@link SwitchableScheduler}. Equivalent to the call of
	 * {@link #build(boolean)} with argument <code>false</code>.
	 * 
	 * @return The newly built scheduler.
	 */
	public SwitchableScheduler build() {
		return build(false);
	}

	/**
	 * Builds the {@link SwitchableScheduler}. The built scheduler is no longer
	 * related to this builder.
	 * 
	 * @param loop
	 *            <code>true</code> for a looped scheduler, <code>false</code>
	 *            for a single execution scheduler.
	 * @return The newly built scheduler.
	 */
	@SuppressWarnings("unchecked")
	public SwitchableScheduler build(boolean loop) {
		return new Scheduler((List<Runnable>) scheduler.clone(), loop);
	}

	private static class SleepRunnable implements Runnable {

		long millis;

		@Override
		public void run() {
			try {
				Thread.sleep(millis);
			}
			catch (InterruptedException e) {}
		}

	}

	private static class SwitchRunnable implements Runnable {

		Switchable switchable;
		boolean state;

		@Override
		public void run() {
			switchable.setSwitched(state);
		}

	}

	private static class Scheduler extends AbstractSwitchable
			implements SwitchableScheduler {

		private final List<Runnable> scheduler;
		private boolean loop;
		private Thread thread;

		Scheduler(List<Runnable> scheduler, boolean loop) {
			this.scheduler = scheduler;
			this.loop = loop;
		}

		private synchronized void start() {
			thread = new Thread() {
				@Override
				public void run() {
					for (int i = 0; i < scheduler.size(); i++) {
						scheduler.get(i).run();
					}
					if (loop && !isInterrupted()) {
						run();
					}
				}
			};
			thread.setDaemon(true);
			thread.start();
		}

		private synchronized void stop() {
			thread.interrupt();
			try {
				thread.join();
			}
			catch (InterruptedException ex) {}
			thread = null;
		}

		@Override
		public void setSwitched(boolean switched) {
			if (switched != isSwitched()) {
				if (switched) {
					start();
				}
				else {
					stop();
				}
			}
		}

		@Override
		public void restart() {
			if (isSwitched()) {
				stop();
			}
			start();
		}

		@Override
		public boolean isSwitched() {
			return thread != null;
		}

		@Override
		public boolean isLoop() {
			return loop;
		}

		@Override
		public void setLoop(boolean loop) {
			this.loop = loop;
		}

	}

}
