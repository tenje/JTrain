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
 * A DCC controlled train.
 * 
 * @author Jonas Tennié
 */
public interface Train extends Addressable {

	@Override
	LongTrainAddress getAddress();

	/**
	 * Returns a train function by its id.
	 * 
	 * @param id
	 *            The function id in range (0-28).
	 * @return The function by id. <code>null</code> if function for this id is not
	 *         defined.
	 * @throws IndexOutOfBoundsException
	 *             Thrown if <code>id</code> does not lay in range (0-28).
	 */
	TrainFunction getFunction(int id);

	/**
	 * Sets the function for the specified function <code>id</code>.
	 * 
	 * @param id
	 *            The function id in range (0-28).
	 * @param function
	 *            The function to set or <code>null</code> to remove.
	 * @throws IndexOutOfBoundsException
	 *             Thrown if <code>id</code> does not lay in range (0-28).
	 */
	void setFunction(int id, TrainFunction function);

	/**
	 * Sets the target engine speed of the train in range (0-126), -1 for
	 * emergency break. The engine may speed up/slow down smooth (to simulate
	 * the train weight) or directly. Defined by the <code>hard</code> value. If
	 * <code>speed</code> is {@code -1}, then the engine stops directly (emergency
	 * break).
	 * 
	 * @param speed
	 *            The speed to set.
	 * @param hard
	 *            <code>true</code> to set speed directly, <code>false</code> to speed
	 *            up/slow down smooth.
	 * @throws IllegalArgumentException
	 *             Thrown if <code>speed</code> does not lay in range ((-1)-126).
	 */
	void setSpeed(int speed, boolean hard);

	/**
	 * Returns the current speed of the train engine. This value is equivalent
	 * to the electrical current to the engine.
	 * 
	 * @return The current speed of the train engine.
	 */
	int getCurrentSpeed();

	/**
	 * The target speed of the train engine. The train may reach this speed
	 * later. This value is different from {@link #getCurrentSpeed()} if the
	 * train speeds up or slows down.
	 * 
	 * @return The target speed of the train engine.
	 */
	int getTargetSpeed();

	/**
	 * Returns the direction of the train. If speed is zero, then this value
	 * shows only the lights of the train face and back.
	 * 
	 * @return The current direction of the train.
	 */
	TrainDirection getDirection();

	/**
	 * Sets the direction of the train. If speed is zero, then this only sets
	 * the lights of the train face and back. If direction changes, the speed
	 * will be reset hard (train stops directly).
	 * 
	 * @param direction
	 *            The direction of the train.
	 */
	void setDirection(TrainDirection direction);

}
