package de.tenje.jtrain;

/**
 * An address to address/identify an object. Each address has a minimum value of
 * zero, the maximum value is specified by the implementation. Some values may
 * be forbidden. The address may be splited in a main address (first/right bits)
 * and sub address (last/left bits).<br>
 * {@link Object#equals(Object)} and {@link Object#hashCode()} have to be
 * overridden by the implementation to ensure that different objects can be used
 * as same key for collections (using hashing). {@link Object#equals(Object)}
 * only returns <code>true</code> if {@link #getAddress()} returns the same value.
 * 
 * @author Jonas Tennié
 */
public interface Address {

	/**
	 * Returns the whole address.
	 * 
	 * @return The whole address.
	 */
	int getAddress();

	/**
	 * Returns the main address. The main address is represented by the
	 * first/right bits of the whole address. The value is equal to the return
	 * value of {@link #getAddress()} if no sub address exists.
	 * 
	 * @return The main address.
	 */
	int getMainAddress();

	/**
	 * Returns the sub address. The main address is represented by the last/left
	 * bits of the whole address. The value is <code>0</code> if this address does
	 * not have a sub address.
	 * 
	 * @return The sub address.
	 */
	int getSubAddress();

}
