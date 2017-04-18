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

/**
 * A scheduler to schedule the switch state of a set of {@link Switchable}s.
 * 
 * @author Jonas Tennié
 */
public interface SwitchableScheduler extends Switchable {

	/**
	 * Returns if the scheduler is running. <code>true</code> if running, else
	 * <code>false</code>.
	 */
	@Override
	boolean isSwitched();

	/**
	 * Starts or stops the scheduler execution. Does nothing if
	 * <code>switched</code> is the current state of the scheduler returned by
	 * {@link #isSwitched()}.
	 * 
	 * @param switched
	 *            <code>true</code> to start, <code>false</code> to stop.
	 */
	@Override
	void setSwitched(boolean switched);

	/**
	 * Stops the scheduler if it is running. And (re-) starts the scheduler
	 * execution.
	 */
	void restart();

	/**
	 * Returns if the scheduler restarts after finishing.
	 * 
	 * @return <code>true</code> if the scheduler runs in a loop,
	 *         <code>false</code> if the scheduler runs only once when calling
	 *         {@link #setSwitched(boolean)} with argument <code>true</code>.
	 */
	boolean isLoop();

	/**
	 * Sets if the scheduler restarts after finishing.
	 * 
	 * @param loop
	 *            <code>true</code> if the scheduler runs in a loop,
	 *            <code>false</code> if the scheduler runs only once when
	 *            calling {@link #setSwitched(boolean)} with argument
	 *            <code>true</code>.
	 */
	void setLoop(boolean loop);

}
