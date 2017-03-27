package de.tenje.jtrain;

/**
 * An object that holds an {@link Address}. The address does not change in the
 * object's life cycle.
 * 
 * @author Jonas Tennié
 */
public interface Addressable {

	/**
	 * Returns the address.
	 * 
	 * @return The address.
	 */
	Address getAddress();

}
