package de.tenje.jtrain.dccpp.impl;

import java.util.List;

import de.tenje.jtrain.dccpp.PacketBuilder;
import de.tenje.jtrain.dccpp.PacketClearSettings;

/**
 * This class is a concrete implementation of the {@link PacketClearSettings}
 * interface.
 * 
 * @author Jonas Tennié
 */
public class PacketClearSettingsImpl extends AbstractPacket implements PacketClearSettings {

	/**
	 * {@link PacketBuilder} to build a {@link PacketClearSettings}. The
	 * parameter list is ignored.
	 */
	public static final PacketBuilder<PacketClearSettings> BUILDER = new PacketBuilder<PacketClearSettings>() {
		@Override
		public PacketClearSettings build(List<String> parameters) {
			return new PacketClearSettingsImpl();
		}
	};

	/**
	 * Constructs a new {@link PacketClearSettingsImpl} with no packet
	 * parameters.
	 */
	public PacketClearSettingsImpl() {
		super(PacketClearSettings.TYPE_CHAR);
	}

}
