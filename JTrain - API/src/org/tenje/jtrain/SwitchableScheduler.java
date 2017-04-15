package org.tenje.jtrain;

/**
 * A scheduler to schedule the switch state of a set of {@link Switchable}s.
 * 
 * @author Jonas Tennié
 */
public interface SwitchableScheduler extends Switchable {

	/**
	 * Returns if the scheduler is running. <code>true</code> if running, else
	 * <code>false</code>.
	 */
	@Override
	boolean isSwitched();

	/**
	 * Starts or stops the scheduler execution. Does nothing if
	 * <code>switched</code> is the current state of the scheduler returned by
	 * {@link #isSwitched()}.
	 * 
	 * @param switched
	 *            <code>true</code> to start, <code>false</code> to stop.
	 */
	@Override
	void setSwitched(boolean switched);

	/**
	 * Stops the scheduler if it is running. And (re-) starts the scheduler
	 * execution.
	 */
	void restart();

	/**
	 * Returns if the scheduler restarts after finishing.
	 * 
	 * @return <code>true</code> if the scheduler runs in a loop,
	 *         <code>false</code> if the scheduler runs only once when calling
	 *         {@link #setSwitched(boolean)} with argument <code>true</code>.
	 */
	boolean isLoop();

	/**
	 * Sets if the scheduler restarts after finishing.
	 * 
	 * @param loop
	 *            <code>true</code> if the scheduler runs in a loop,
	 *            <code>false</code> if the scheduler runs only once when
	 *            calling {@link #setSwitched(boolean)} with argument
	 *            <code>true</code>.
	 */
	void setLoop(boolean loop);

}
