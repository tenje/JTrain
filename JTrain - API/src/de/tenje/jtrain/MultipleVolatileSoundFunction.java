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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import javax.sound.sampled.Clip;

/**
 * A {@link TrainFunction} which is a volatile sound. If the sound is still
 * playing and the function is enabled again, the sound will be stopped and
 * replayed. The function can play different sounds in different {@link Order}s.
 * 
 * @author Jonas Tennié
 */
public class MultipleVolatileSoundFunction extends AbstractSwitchable implements TrainFunction {

	private final List<Clip> clips;
	private final Order order;
	private Clip currentClip;
	// Next index to return
	private int index;
	// The current iteration direction used for Order#FIRST_TO_LAST_TO_FIRST
	// true: forward, false: reverse
	private boolean iterDirection;

	/**
	 * Constructs a new {@link MultipleVolatileSoundFunction} with the specified
	 * clips. An internal copy of clips is used.
	 * 
	 * @param clips
	 *            The clips to play randomly.
	 * @param order
	 *            The order in which the clips are played.
	 * @throws NullPointerException
	 *             Thrown if <code>clips</code> is <code>null</code> or one of its
	 *             elements is <code>null</code> or if size of <code>clips</code> is
	 *             higher than one and <code>order</code> is <code>null</code>.
	 * @throws IllegalArgumentException
	 *             Thrown if <code>clips</code> is empty.
	 */
	public MultipleVolatileSoundFunction(Collection<Clip> clips, Order order) {
		Objects.requireNonNull(clips, "clips");
		this.order = Objects.requireNonNull(order, "order");
		if (clips.isEmpty()) {
			throw new IllegalArgumentException("clips cannot be empty");
		}
		this.clips = new ArrayList<>(clips.size());
		for (Clip clip : clips) {
			if (clip != null) {
				this.clips.add(clip);
			}
			else {
				throw new NullPointerException("clips contains null elements");
			}
		}
	}

	@Override
	public void setSwitched(boolean switched) {
		if (switched) {
			if (clips.size() == 1) {
				setClip(clips.get(0));
			}
			else {
				switch (order) {
					case FIRST_TO_LAST:
						if (index == clips.size()) {
							index = 0;
						}
						setClip(clips.get(index));
						index++;
					break;
					case FIRST_TO_LAST_TO_FIRST:
						if (iterDirection) {
							if (index == clips.size()) {
								index = clips.size() - 2;
								iterDirection = false;
								setClip(clips.get(clips.size() - 1));
							}
							else {
								setClip(clips.get(index));
								index++;
							}
						}
						else {
							if (index == -1) {
								index = 2;
								iterDirection = true;
								setClip(clips.get(1));
							}
							else {
								setClip(clips.get(index - 1));
								index--;
							}
						}
					break;
					case RANDOM:
						setClip(clips.get((int) (Math.random() * clips.size())));
					break;
				}
			}
		}
	}

	private void setClip(Clip clip) {
		if (currentClip != null) {
			currentClip.stop();
		}
		currentClip = clip;
		currentClip.setMicrosecondPosition(0);
		currentClip.start();
	}

	@Override
	public boolean isSwitched() {
		return false;
	}

}
