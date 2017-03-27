package de.tenje.jtrain;

/**
 * A railroad turnout. A turnout can have two states: Thrown or straight.
 * 
 * @author Jonas Tennié
 */
public interface Turnout extends AddressableSwitchable {

	@Override
	AccessoryDecoderAddress getAddress();

	/**
	 * Sets the throw state of the turnout.
	 * 
	 * @param switched
	 *            The throw state to set. <code>true</code> to throw,
	 *            <code>false</code> for straight.
	 */
	@Override
	void setSwitched(boolean switched);

	/**
	 * Returns whether the turnout is thrown or not.
	 * 
	 * @return <code>true</code> if thrown, else <code>false</code>.
	 */
	@Override
	boolean isSwitched();

}
