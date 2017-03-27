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
