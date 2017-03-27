package de.tenje.jtrain.dccpp.impl;

import java.util.List;

import de.tenje.jtrain.dccpp.PacketBuilder;
import de.tenje.jtrain.dccpp.PacketOperationFailed;

/**
 * This class is a concrete implementation of the {@link PacketOperationFailed}
 * interface.
 * 
 * @author Jonas Tennié
 */
public class PacketOperationFailedImpl extends AbstractPacket implements PacketOperationFailed {

	/**
	 * {@link PacketBuilder} to build a {@link PacketOperationFailed}. The
	 * parameter list is ignored.
	 */
	public static final PacketBuilder<PacketOperationFailed> BUILDER = new PacketBuilder<PacketOperationFailed>() {
		@Override
		public PacketOperationFailed build(List<String> parameters) {
			return new PacketOperationFailedImpl();
		}
	};

	/**
	 * Constructs a new {@link PacketOperationFailedImpl} with no packet
	 * parameters.
	 */
	public PacketOperationFailedImpl() {
		super(PacketOperationFailed.TYPE_CHAR);
	}

}
