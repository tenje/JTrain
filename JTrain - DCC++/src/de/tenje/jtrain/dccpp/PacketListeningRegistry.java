package de.tenje.jtrain.dccpp;

import de.tenje.jtrain.AddressRegistry;

/**
 * A registry which listens to {@link Packet}s that change registry entries.
 * 
 * @author Jonas Tennié
 */
public interface PacketListeningRegistry extends PacketListener {

	/**
	 * Returns the address registry of this registry.
	 * 
	 * @return The address registry.
	 */
	AddressRegistry getAddressRegistry();

}
