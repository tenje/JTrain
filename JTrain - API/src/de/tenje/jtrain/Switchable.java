package de.tenje.jtrain;

/**
 * A switchable can have one of the two states: Not switched or switched. These
 * two states are specified by the implementation.
 * 
 * @author Jonas Tennié
 */
public interface Switchable {

	/**
	 * Toggles the switch state. Sets to switched if not switched, sets to not
	 * switched if switched.
	 */
	void toggle();

	/**
	 * Sets the switch state.
	 * 
	 * @param switched
	 *            <code>true</code> for switched, <code>false</code> for not switched.
	 */
	void setSwitched(boolean switched);

	/**
	 * Returns the switch state.
	 * 
	 * @return <code>true</code> for switched, <code>false</code> for not switched.
	 */
	boolean isSwitched();

}
