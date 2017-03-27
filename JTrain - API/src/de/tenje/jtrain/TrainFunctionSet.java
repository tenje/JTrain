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

import java.util.Objects;
import java.util.Set;

/**
 * A set of {@link TrainFunction}s which can be registered for the same function
 * id.
 * 
 * @author Jonas Tennié
 */
public class TrainFunctionSet extends AbstractSwitchable implements TrainFunction {

	private final Set<TrainFunction> functions;
	private boolean enabled;

	/**
	 * Constructs a new {@link TrainFunctionSet} as a representation of the
	 * defined set.
	 * 
	 * @param functions
	 *            The function set.
	 * @throws NullPointerException
	 *             Thrown if <code>functions</code> is <code>null</code>.
	 */
	public TrainFunctionSet(Set<TrainFunction> functions) {
		this.functions = Objects.requireNonNull(functions, "functions");
	}

	/**
	 * Enables or disables all functions which are currently in the function set
	 * defined in {@link #TrainFunctionSet(Set)}.
	 */
	@Override
	public void setSwitched(boolean switched) {
		enabled = switched;
		for (TrainFunction function : functions) {
			if (function != null) {
				function.setSwitched(switched);
			}
		}
	}

	@Override
	public boolean isSwitched() {
		return enabled;
	}

}
