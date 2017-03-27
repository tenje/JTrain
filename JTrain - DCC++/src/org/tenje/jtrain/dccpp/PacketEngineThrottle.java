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
package org.tenje.jtrain.dccpp;

import org.tenje.jtrain.Addressable;
import org.tenje.jtrain.LongTrainAddress;
import org.tenje.jtrain.TrainDirection;

/**
 * A {@link Packet} to set a train's speed and direction.
 * 
 * <table>
 * <tr>
 * <td>Type char:</td>
 * <td>'t'</td>
 * </tr>
 * <tr>
 * <td valign="top">Packet format:</td>
 * <td ><i>&lt;t Register Address Speed Direction&gt;</i><br>
 * <i>Register:</i> An internal register number to store the packet<br>
 * <i>Address:</i> Train decoder address in range (1-10293)<br>
 * <i>Speed:</i> Engine speed in range (0-126), -1 for emergency stop<br>
 * <i>Direction:</i> The train direction;
 * 0&#8793;{@link TrainDirection#REVERSE},
 * 1&#8793;{@link TrainDirection#FORWARD}. Also used for lighting</td>
 * </tr>
 * <tr>
 * <td>Return packet:</td>
 * <td>TODO</td>
 * </tr>
 * </table>
 * 
 * @author Jonas Tennié
 */
public interface PacketEngineThrottle extends Addressable, Packet, Registrable {

	/**
	 * The type char of the <code>PacketEngineThrottle</code>.
	 */
	public static final char TYPE_CHAR = 't';

	@Override
	LongTrainAddress getAddress();

	/**
	 * Returns the speed to set using 128-step speed (0-126, -1 for emergency
	 * break).
	 * 
	 * @return The speed to set.
	 */
	int getSpeed();

	/**
	 * Returns the direction of the train.
	 * 
	 * @return The direction of the train.
	 */
	TrainDirection getDirection();

}
