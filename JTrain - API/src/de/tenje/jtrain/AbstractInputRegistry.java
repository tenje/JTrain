package de.tenje.jtrain;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * This class provides a skeletal implementation of the {@link InputRegistry}
 * interface to minimize the effort required to implement this interface.
 * 
 * @author Jonas Tennié
 * @param <T>
 *            The type of object handled by this registry.
 */
public abstract class AbstractInputRegistry<T extends Addressable> implements InputRegistry<T> {

	/**
	 * The addresses and their related objects.
	 */
	protected final Map<Address, T> objectsByAddress = new HashMap<>();
	private final Map<Address, T> unmodifiableObjectsByAddress = Collections.unmodifiableMap(objectsByAddress);

	/**
	 * Constructs a new {@link AbstractInputRegistry} with no entries.
	 */
	public AbstractInputRegistry() {}

	@Override
	public Map<Address, T> getEntries() {
		return unmodifiableObjectsByAddress;
	}

	@Override
	public T register(T object) {
		Objects.requireNonNull(object, "object");
		return objectsByAddress.put(object.getAddress(), object);
	}

	@Override
	public boolean remove(T object) {
		Objects.requireNonNull(object, "object");
		T toRemove = objectsByAddress.get(object.getAddress());
		if (toRemove != null && toRemove.equals(object)) {
			objectsByAddress.remove(object.getAddress());
			return true;
		}
		return false;
	}

}
