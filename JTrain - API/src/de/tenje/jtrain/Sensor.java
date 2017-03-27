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
package de.tenje.jtrain;

/**
 * A simple sensor which may be triggered or not. A {@link SensorListener} can
 * be registered to listen to sensor states using
 * {@link #addSensorListener(SensorListener)}.
 * 
 * @author Jonas Tennié
 */
public interface Sensor extends Addressable {

	@Override
	AccessoryDecoderAddress getAddress();

	/**
	 * Returns whether this sensor is triggered.
	 * 
	 * @return <code>true</code> if triggered, else <code>false</code>.
	 */
	boolean isTriggered();

	/**
	 * Adds a {@link SensorListener} to this sensor. Equality is checked using
	 * {@link SensorListener#equals(Object)}.
	 * 
	 * @param listener
	 *            The listener to add.
	 * @return <code>true</code> if the listener was added, <code>false</code> if the
	 *         listener was already added to this sensor.
	 * @throws NullPointerException
	 *             Thrown if <code>listener</code> is <code>null</code>.
	 */
	boolean addSensorListener(SensorListener listener);

	/**
	 * Removes a {@link SensorListener} from this sensor. Equality is checked
	 * using {@link SensorListener#equals(Object)}.
	 * 
	 * @param listener
	 *            The listener to remove.
	 * @return <code>true</code> if the listener was removed, <code>false</code> if the
	 *         listener was not added to this sensor before.
	 * @throws NullPointerException
	 *             Thrown if <code>listener</code> is <code>null</code>.
	 */
	boolean removeSensorListener(SensorListener listener);

}
