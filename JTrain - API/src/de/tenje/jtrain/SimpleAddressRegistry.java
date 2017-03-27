package de.tenje.jtrain;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * This class provides a skeletal implementation of the {@link AddressRegistry}
 * interface to minimize the effort required to implement this interface.
 * 
 * @author Jonas Tennié
 */
public class SimpleAddressRegistry implements AddressRegistry {

	/**
	 * The ids and their related addresses,
	 */
	protected final Map<Integer, Address> addressesById = new HashMap<>();
	private final Map<Integer, Address> unmodifiableAddressesById = Collections
			.unmodifiableMap(addressesById);

	/**
	 * Constructs a new {@link SimpleAddressRegistry} with no entries.
	 */
	public SimpleAddressRegistry() {}

	@Override
	public Map<Integer, Address> getRegistrations() {
		return unmodifiableAddressesById;
	}

	@Override
	public boolean defineAddress(int registrationId, Address address) {
		ParameterValidator.validateRegistrationId(registrationId);
		if (address == null) {
			return addressesById.remove(registrationId) != null;
		}
		else {
			return !address.equals(addressesById.put(registrationId, address));
		}
	}

	@Override
	public Address getAddress(int registrationId) {
		ParameterValidator.validateRegistrationId(registrationId);
		return addressesById.get(registrationId);
	}

	@Override
	public int getId(Address address) {
		for (Entry<Integer, Address> entry : addressesById.entrySet()) {
			if (entry.getValue().equals(address)) {
				return entry.getKey();
			}
		}
		return -1;
	}

}
