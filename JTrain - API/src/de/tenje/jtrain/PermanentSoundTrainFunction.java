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
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineEvent.Type;
import javax.sound.sampled.LineListener;

/**
 * A {@link TrainFunction} which is a permanent sound. If the sound is playing
 * and the function is enabled again, nothing will happen (the old sound is
 * still played). The sound is splited in three parts: Enable sound (played when
 * function is enabled), loop sound (looped until function is disabled), disable
 * sound (played when function is disabled). May be used for engine sound
 * (start/shutdown sounds).
 * 
 * @author Jonas Tennié
 */
public class PermanentSoundTrainFunction extends AbstractSwitchable implements TrainFunction {

	private final Clip enableSound, loopSound, disableSound;
	private boolean enabled;

	/**
	 * Constructs a new {@link PermanentSoundTrainFunction} with the specified
	 * enable clip, loop clip and disable clip.
	 * 
	 * @param enableClip
	 *            The enable clip. May be <code>null</code>.
	 * @param loopClip
	 *            The loop clip.
	 * @param disableClip
	 *            The disable clip. May be <code>null</code>.
	 */
	public PermanentSoundTrainFunction(Clip enableClip, Clip loopClip, Clip disableClip) {
		this.enableSound = enableClip;
		this.loopSound = Objects.requireNonNull(loopClip, "loopClip");
		this.disableSound = disableClip;
	}

	/**
	 * Constructs a new {@link PermanentSoundTrainFunction} with the specified
	 * loop clip, but without enable/disable clip.
	 * 
	 * @param loopClip
	 *            The loop clip.
	 */
	public PermanentSoundTrainFunction(Clip loopClip) {
		this(null, loopClip, null);
	}

	@Override
	public void setSwitched(boolean switched) {
		if (switched) {
			if (!enabled) { // Not running, requested to start
				enabled = true;
				if (disableSound != null) { // Stop disable clip
					disableSound.stop();
				}
				if (enableSound != null) {
					// Start enable clip
					enableSound.setMicrosecondPosition(0);
					enableSound.start();
					// Wait until enable clip finished:
					enableSound.addLineListener(new LineListener() {
						@Override
						public void update(LineEvent event) {
							if (event.getType() == Type.STOP) {
								enableSound.removeLineListener(this);
								loopSound.setMicrosecondPosition(0);
								loopSound.loop(Clip.LOOP_CONTINUOUSLY);
							}
						}
					});
				}
				else {
					while (enabled) { // Run until disabled
						loopSound.setMicrosecondPosition(0);
						loopSound.start();
					}
				}
			}
		}
		else {
			if (enabled) { // Running, requested to stop
				enabled = false;
				if (enableSound != null) { // Stop enable clip
					enableSound.stop();
				}
				loopSound.stop();
				if (disableSound != null) { // Start disable clip
					disableSound.setMicrosecondPosition(0);
					disableSound.start();
				}
			}
		}
	}

	@Override
	public boolean isSwitched() {
		return enabled;
	}

}
