package de.tenje.jtrain;

/**
 * A level crossing. The representation of a level crossing is as simple as
 * possible to make it universally usable.
 * 
 * @author Jonas Tennié
 */
public interface LevelCrossing extends Switchable {

	/**
	 * Returns whether the barriers are closed or not.
	 * 
	 * @return <code>true</code> if closed or closing, <code>false</code> if open or
	 *         opening.
	 */
	@Override
	boolean isSwitched();

	/**
	 * Returns whether the barriers are closing. If the barriers are closing,
	 * they are not fully open and not fully closed. If this method returns
	 * <code>true</code>, then {@link #isSwitched()} returns <code>true</code>, too. If
	 * this level crossing has no barriers, then this method will always return
	 * <code>false</code>.
	 * 
	 * @return <code>true</code> if closing, else <code>false</code>.
	 */
	boolean isClosing();

	/**
	 * Returns whether the barriers are opening. If the barriers are opening,
	 * they are not fully open and not fully closed. If this method returns
	 * <code>true</code>, then {@link #isSwitched()} returns <code>false</code>. If this
	 * level crossing has no barriers, then this method will always return
	 * <code>false</code>.
	 * 
	 * @return <code>true</code> if closing, else <code>false</code>.
	 */
	boolean isOpening();

	/**
	 * Closes or opens the barriers.<br>
	 * <b>Close:</b> Starts a barrier closing. The warning lights are turned on
	 * and (after a time) the barriers close.<br>
	 * <b>Open:</b> Starts barrier opening. The barriers open and the warning
	 * lights are turned off.
	 * 
	 * @param switched
	 *            <code>true</code> to close barriers, <code>false</code> to open.
	 */
	@Override
	void setSwitched(boolean switched);

}
