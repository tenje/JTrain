package de.tenje.jtrain.dccpp;

/**
 * An object that holds two callback variables called <i>num</i> and <i>sub</i>.
 * Both have range (0-32767).
 * 
 * @author Jonas Tennié
 */
public interface CallbackData {

	/**
	 * Returns the value of <i>num</i>.
	 * 
	 * @return The value of <i>num</i>.
	 */
	int getCallbackNumber();

	/**
	 * Returns the value of <i>sub</i>.
	 * 
	 * @return The value of <i>sub</i>.
	 */
	int getCallbackSum();

}
