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

import javax.sound.sampled.Clip;

/**
 * A {@link TrainFunction} which is a volatile sound. If the sound is still
 * playing and the function is enabled again, the sound will be stopped and
 * replayed.
 * 
 * @author Jonas Tennié
 */
public class VolatileSoundTrainFunction implements TrainFunction {

	private final Clip clip;

	/**
	 * Constructs a new {@link VolatileSoundTrainFunction} with a specified
	 * audio clip.
	 * 
	 * @param clip
	 *            The audio clip.
	 * @throws NullPointerException
	 *             Thrown if <code>clip</code> is <code>null</code>.
	 */
	public VolatileSoundTrainFunction(Clip clip) {
		this.clip = Objects.requireNonNull(clip, "clip");
	}

	@Override
	public void toggle() {
		setSwitched(true); // Always unswitched
	}

	@Override
	public void setSwitched(boolean switched) {
		if (switched) {
			try {
				clip.stop();
				clip.setMicrosecondPosition(0);
				clip.start();
			}
			catch (Exception ex) {
				System.err.println("Failed to play clip:");
				ex.printStackTrace();
			}
		}
	}

	@Override
	public boolean isSwitched() {
		return false;
	}

}
