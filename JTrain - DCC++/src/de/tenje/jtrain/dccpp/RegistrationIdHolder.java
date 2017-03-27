package de.tenje.jtrain.dccpp;

/**
 * An object that holds an id. The id range may be restricted by the
 * implementing class.
 * 
 * @author Jonas Tennié
 */
public interface RegistrationIdHolder {

	/**
	 * Returns the ID of the identifiable.
	 * 
	 * @return The ID.
	 */
	int getId();

}
