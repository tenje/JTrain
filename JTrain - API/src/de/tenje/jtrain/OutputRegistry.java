package de.tenje.jtrain;

import java.util.Map;
import java.util.Set;

/**
 * A registry for a specific type of objects which output/send data to other
 * components. The objects are stored with an {@link Address} as key. Different
 * objects can be registered with the same address.
 * 
 * @author Jonas Tennié
 * @param <T>
 *            The type of object handled by this registry.
 */
public interface OutputRegistry<T extends Addressable> {

	/**
	 * Returns an unmodifiable map of all entries in this registry. The map
	 * always represents the current state of the registry. The map key is the
	 * address of the registered objects, the value is is a set of objects
	 * registered for the key address.
	 * 
	 * @return Am unmodifiable map of all entries in this registry.
	 */
	Map<Address, Set<T>> getEntries();

	/**
	 * Registers an object for its {@link Address}. The address returned by
	 * {@link Addressable#getAddress()} is used as key.
	 * 
	 * @param object
	 *            The object to register.
	 * @return <code>true</code> if registry changed as a result of the call (object
	 *         registered), <code>false</code> if nothing changed (object already
	 *         registered).
	 * @throws NullPointerException
	 *             Thrown if <code>object</code> is <code>null</code>.
	 * @see Addressable#getAddress()
	 */
	boolean register(T object);

	/**
	 * Removes an object using {@link Object#equals(Object)} to find the object.
	 * 
	 * @param object
	 *            The object to remove.
	 * @return <code>true</code> if registry changed as a result of the call (object
	 *         removed), <code>false</code> if nothing changed (object was not
	 *         registered).
	 * @throws NullPointerException
	 *             Thrown if <code>object</code> is <code>null</code>.
	 */
	boolean remove(T object);

	/**
	 * Removes all objects for a specified {@link Address}.
	 * 
	 * @param address
	 *            The address.
	 * @throws NullPointerException
	 *             Thrown if <code>address</code> is <code>null</code>.
	 */
	void clearAddressRegistrations(Address address);

}
