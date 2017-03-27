package de.tenje.jtrain;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * This class provides a skeletal implementation of the {@link OutputRegistry}
 * interface to minimize the effort required to implement this interface.
 * 
 * @author Jonas Tennié
 * @param <T>
 *            The type of object handled by this registry.
 */
public abstract class AbstractOutputRegistry<T extends Addressable>
		implements OutputRegistry<T> {

	/**
	 * The addresses and their related objects.
	 */
	protected final Map<Address, Set<T>> objectsByAddress = new HashMap<>();
	private final Map<Address, Set<T>> unmodifiableObjectsByAddress = Collections
			.unmodifiableMap(objectsByAddress);

	/**
	 * Constructs a new {@link AbstractOutputRegistry} with no entries.
	 */
	public AbstractOutputRegistry() {}

	@Override
	public Map<Address, Set<T>> getEntries() {
		return unmodifiableObjectsByAddress;
	}

	@Override
	public boolean register(T object) {
		Objects.requireNonNull(object, "object");
		Set<T> objectsByAddress = this.objectsByAddress.get(object.getAddress());
		if (objectsByAddress == null) {
			objectsByAddress = new HashSet<>();
			this.objectsByAddress.put(object.getAddress(), objectsByAddress);
		}
		return objectsByAddress.add(object);
	}

	@Override
	public boolean remove(T object) {
		Objects.requireNonNull(object, "object");
		Set<T> objectsByAddress = this.objectsByAddress.get(object.getAddress());
		if (objectsByAddress != null) {
			return objectsByAddress.remove(object);
		}
		return false;
	}

	@Override
	public void clearAddressRegistrations(Address address) {
		Objects.requireNonNull(address, "address");
		objectsByAddress.remove(address);
	}

}
