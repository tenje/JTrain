package de.tenje.jtrain;

/**
 * The function of a train. A train may have up to 29 functions (F0-F28). Some
 * functions can only be switched on/enabled (e.g. to play a short sound).
 * Others can be switched on/enabled and off/disabled (e.g. a light).
 * 
 * @author Jonas Tennié
 */
public interface TrainFunction extends Switchable {

	/**
	 * {@inheritDoc} Enables this function if this function cannot be disabled.
	 */
	@Override
	void toggle();

	/**
	 * Enables or disables this function. Does nothing if <code>switched</code> is
	 * <code>true</code> and the function is already enabled or if <code>switched</code>
	 * is <code>false</code> and the function is not enabled or cannot be disabled.
	 * 
	 * @param switched
	 *            <code>true</code> to enabled function, <code>false</code> to disable.
	 */
	@Override
	void setSwitched(boolean switched);

	/**
	 * Returns whether the function is enabled.
	 * 
	 * @return <code>true</code> if enabled, <code>false</code> if disabled, also
	 *         <code>false</code> if function cannot be disabled.
	 */
	@Override
	boolean isSwitched();

}
