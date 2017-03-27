package de.tenje.jtrain.dccpp.impl;

import java.util.List;

import de.tenje.jtrain.dccpp.PacketBuilder;
import de.tenje.jtrain.dccpp.PacketEnterDiagnosticMode;

/**
 * This class is a concrete implementation of the
 * {@link PacketEnterDiagnosticMode} interface.
 * 
 * @author Jonas Tennié
 */
public class PacketEnterDiagnosticModeImpl extends AbstractPacket implements PacketEnterDiagnosticMode {

	/**
	 * {@link PacketBuilder} to build a {@link PacketEnterDiagnosticMode}.
	 */
	public static final PacketBuilder<PacketEnterDiagnosticMode> BUILDER = new PacketBuilder<PacketEnterDiagnosticMode>() {
		@Override
		public PacketEnterDiagnosticMode build(List<String> parameters) {
			return new PacketEnterDiagnosticModeImpl();
		}
	};

	/**
	 * Constructs a new {@link PacketEnterDiagnosticMode} with no packet
	 * parameters.
	 */
	public PacketEnterDiagnosticModeImpl() {
		super(PacketEnterDiagnosticMode.TYPE_CHAR);
	}

}
