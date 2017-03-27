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
package de.tenje.jtrain.dccpp;

/**
 * {@link Packet} to return the power state of the tracks.
 * 
 * @author Jonas Tennié
 */
public interface PacketTrackPowerState extends Packet {

	/**
	 * The type char of the {@link PacketTrackPowerState}.
	 */
	public static final char TYPE_CHAR = 'p';

	/**
	 * Returns if the rails are powered.
	 * 
	 * @return <code>true</code> if powered, else <code>false</code>.
	 */
	boolean isPowered();

}
