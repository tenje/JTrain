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

import java.util.Objects;

/**
 * This class provides a skeletal implementation of the {@link Train} interface
 * to minimize the effort required to implement this interface.
 * 
 * @author Jonas Tennié
 */
public abstract class AbstractTrain implements Train {

	private final LongTrainAddress address;
	private final TrainFunction[] functions = new TrainFunction[29];

	/**
	 * Constructs a new {@link AbstractTrain} with the specified
	 * {@link LongTrainAddress}.
	 * 
	 * @param address
	 *            The train's address.
	 * @throws NullPointerException
	 *             Thrown if <code>address</code> is <code>null</code>.
	 */
	public AbstractTrain(LongTrainAddress address) {
		this.address = Objects.requireNonNull(address, "address");
	}

	@Override
	public LongTrainAddress getAddress() {
		return address;
	}

	@Override
	public TrainFunction getFunction(int id) {
		return functions[id];
	}

	@Override
	public void setFunction(int id, TrainFunction function) {
		functions[id] = function;
	}

}
