package de.tenje.jtrain.dccpp.impl;

import java.util.List;

import de.tenje.jtrain.dccpp.PacketBuilder;
import de.tenje.jtrain.dccpp.PacketOperationSuccessful;

/**
 * This class is a concrete implementation of the
 * {@link PacketOperationSuccessful} interface.
 * 
 * @author Jonas Tennié
 */
public class PacketOperationSuccessfulImpl extends AbstractPacket implements PacketOperationSuccessful {

	/**
	 * {@link PacketBuilder} to build a {@link PacketOperationSuccessful}. The
	 * parameter list is ignored.
	 */
	public static final PacketBuilder<PacketOperationSuccessful> BUILDER = new PacketBuilder<PacketOperationSuccessful>() {
		@Override
		public PacketOperationSuccessful build(List<String> parameters) {
			return new PacketOperationSuccessfulImpl();
		}
	};

	/**
	 * Constructs a new {@link PacketOperationSuccessfulImpl} with no packet
	 * parameters.
	 */
	public PacketOperationSuccessfulImpl() {
		super(PacketOperationSuccessful.TYPE_CHAR);
	}

}
