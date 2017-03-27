package de.tenje.jtrain;

/**
 * An electrical output pin of the machine the program is running (e.g. Arduino
 * or Raspberry Pi output pins).
 * 
 * @author Jonas Tennié
 */
public interface OutputPin extends Addressable, Switchable {

	@Override
	OutputPinAddress getAddress();

	/**
	 * Sets the state of the pin.
	 * 
	 * @param state
	 *            The state to set for the output pin. <code>true</code> for HIGH,
	 *            <code>false</code> for LOW.
	 */
	@Override
	void setSwitched(boolean state);

	/**
	 * Returns the state of the pin.
	 * 
	 * @return The state of the output pin. <code>true</code> for HIGH, <code>false</code>
	 *         for LOW.
	 */
	@Override
	boolean isSwitched();

}
