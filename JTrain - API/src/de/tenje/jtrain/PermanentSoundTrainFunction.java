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
