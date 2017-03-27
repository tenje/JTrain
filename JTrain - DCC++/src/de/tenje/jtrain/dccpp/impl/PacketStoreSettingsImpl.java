package de.tenje.jtrain.dccpp.impl;

import java.util.List;

import de.tenje.jtrain.dccpp.PacketBuilder;
import de.tenje.jtrain.dccpp.PacketStoreSettings;

/**
 * This class is a concrete implementation of the {@link PacketStoreSettings}
 * interface.
 * 
 * @author Jonas Tennié
 */
public class PacketStoreSettingsImpl extends AbstractPacket implements PacketStoreSettings {

	/**
	 * {@link PacketBuilder} to build a {@link PacketStoreSettings}. The
	 * parameter list is ignored.
	 */
	public static final PacketBuilder<PacketStoreSettings> BUILDER = new PacketBuilder<PacketStoreSettings>() {
		@Override
		public PacketStoreSettings build(List<String> parameters) {
			return new PacketStoreSettingsImpl();
		}
	};

	/**
	 * Constructs a new {@link PacketStoreSettingsImpl} with no packet
	 * parameters.
	 */
	public PacketStoreSettingsImpl() {
		super(PacketStoreSettings.TYPE_CHAR);
	}

}
