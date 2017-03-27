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
package de.tenje.jtrain.dccpp.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.tenje.jtrain.LongTrainAddress;
import org.tenje.jtrain.ParameterValidator;
import org.tenje.jtrain.TrainDirection;

import de.tenje.jtrain.dccpp.PacketBuilder;
import de.tenje.jtrain.dccpp.PacketEngineThrottle;

/**
 * This class is a concrete implementation of the {@link PacketEngineThrottle}
 * interface.
 * 
 * @author Jonas Tennié
 */
public class PacketEngineThrottleImpl extends AbstractPacket implements PacketEngineThrottle {

	/**
	 * {@link PacketBuilder} to build a {@link PacketEngineThrottle}.
	 */
	public static final PacketBuilder<PacketEngineThrottle> BUILDER = new PacketBuilder<PacketEngineThrottle>() {
		@Override
		public PacketEngineThrottle build(List<String> parameters) {
			return new PacketEngineThrottleImpl(parameters);
		}
	};

	private final LongTrainAddress address;
	private final int registerId, speed;
	private final TrainDirection direction;

	/**
	 * Constructs a new {@link PacketEngineThrottleImpl} with the specified
	 * address, register id, speed and train direction.
	 * 
	 * @param address
	 *            The train address.
	 * @param registerId
	 *            The register id.
	 * @param speed
	 *            The train speed.
	 * @param direction
	 *            The train direction.
	 * @throws IllegalArgumentException
	 *             Thrown if<code>registerId</code> or <code>speed</code> lays out of
	 *             valid range.
	 * @throws NullPointerException
	 *             Thrown if <code>address</code> or <code>direction</code> is
	 *             <code>null</code>.
	 */
	public PacketEngineThrottleImpl(LongTrainAddress address, int registerId, int speed, TrainDirection direction) {
		super(PacketEngineThrottle.TYPE_CHAR, Arrays.asList(String.valueOf(address.getAddress()),
				String.valueOf(registerId), String.valueOf(speed), String.valueOf(direction.ordinal())));
		this.address = address;
		this.registerId = ParameterValidator.validateRegistrationId(registerId);
		this.speed = ParameterValidator.validateSpeed(speed);
		this.direction = Objects.requireNonNull(direction, "direction");
	}

	/**
	 * Constructs a new <code>PacketEngineThrottleImpl</code> with the raw packet
	 * parameters containing the address, register id, speed and train
	 * direction.
	 * 
	 * @param parameters
	 *            The raw packet parameters. First index (0): address (0-10293),
	 *            second index (1): register id (one or higher), third index
	 *            (2): speed ((-1)-126), fourth index (3): train direction ("1"
	 *            for {@link TrainDirection#FORWARD}, other value (not
	 *            <code>null</code>) for {@link TrainDirection#REVERSE}.
	 * 
	 * @throws NumberFormatException
	 *             Thrown if one of the indexes 0-2 is not a number or is
	 *             <code>null</code>.
	 * @throws NullPointerException
	 *             Thrown if one of the direction (index 3) of
	 *             <code>parameters</code> is <code>null</code>.
	 * @throws IllegalArgumentException
	 *             Thrown if address, register id or speed lays out of valid
	 *             range.
	 */
	public PacketEngineThrottleImpl(List<String> parameters) {
		super(PacketEngineThrottle.TYPE_CHAR, parameters);
		this.registerId = ParameterValidator.validateRegistrationId(Integer.parseInt(parameters.get(0)));
		this.address = new LongTrainAddress(Integer.parseInt(parameters.get(1)));
		this.speed = ParameterValidator.validateSpeed(Integer.parseInt(parameters.get(2)));
		this.direction = parameters.get(3).equals("1") ? TrainDirection.FORWARD : TrainDirection.REVERSE;
	}

	@Override
	public LongTrainAddress getAddress() {
		return address;
	}

	@Override
	public int getRegisterId() {
		return registerId;
	}

	@Override
	public int getSpeed() {
		return speed;
	}

	@Override
	public TrainDirection getDirection() {
		return direction;
	}

}
