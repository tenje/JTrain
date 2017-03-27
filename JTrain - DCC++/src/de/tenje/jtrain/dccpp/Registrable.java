package de.tenje.jtrain.dccpp;

/**
 * An object which holds a registration id. Different implementations may define
 * different valid id ranges.
 * 
 * @author Jonas Tennié
 */
public interface Registrable {

	/**
	 * Returns the registration id.
	 * 
	 * @return The registration id.
	 */
	int getRegisterId();

}
