/*******************************************************************************
 * Copyright (c): Jonas Tenni� 2017
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
 * A {@link Turnout} which controls a single {@link Signal}'s
 * {@link SignalAspect}. Switch state set by {@link #setSwitched(boolean)} must
 * be <code>true</code> to set signal state.
 * 
 * @author Jonas Tenni�
 */
public class SignalAspectControlTurnout extends AbstractSwitchable implements Turnout {

	private final AccessoryDecoderAddress address;
	private final Signal signal;
	private final SignalAspect aspect;

	/**
	 * Constructs a new {@link SignalAspectControlTurnout} with the specified
	 * address for the specified signal and signal aspect.
	 * 
	 * @param address
	 *            The address.
	 * @param signal
	 *            The signal to handle.
	 * @param aspect
	 *            The aspect to set to the signal if switched.
	 */
	public SignalAspectControlTurnout(AccessoryDecoderAddress address, Signal signal,
			SignalAspect aspect) {
		this.address = Objects.requireNonNull(address, "address");
		this.signal = signal;
		this.aspect = aspect;
	}

	@Override
	public AccessoryDecoderAddress getAddress() {
		return address;
	}

	/**
	 * Sets the signal state to the defined state if {@code switched} is
	 * {@code true}, does nothing otherwise.
	 */
	@Override
	public void setSwitched(boolean switched) {
		if (switched) {
			signal.setAspect(aspect);
		}
	}

	/**
	 * Returns whether the current signal aspect is the same aspect as the
	 * aspect controlled by this turnout.
	 * 
	 * @return {@code true} if the handled signal's state is the same as the
	 *         handled signal aspect, else {@code false}.
	 */
	@Override
	public boolean isSwitched() {
		return signal.getAspect() == aspect;
	}

}
