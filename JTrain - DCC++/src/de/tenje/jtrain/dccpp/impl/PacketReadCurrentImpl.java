package de.tenje.jtrain.dccpp.impl;

import java.util.List;

import de.tenje.jtrain.dccpp.PacketBuilder;
import de.tenje.jtrain.dccpp.PacketReadCurrent;

/**
 * This class is a concrete implementation of the {@link PacketReadCurrent}
 * interface.
 * 
 * @author Jonas Tennié
 */
public class PacketReadCurrentImpl extends AbstractPacket implements PacketReadCurrent {

	/**
	 * {@link PacketBuilder} to build a {@link PacketReadCurrent}. The parameter
	 * list is ignored.
	 */
	public static final PacketBuilder<PacketReadCurrent> BUILDER = new PacketBuilder<PacketReadCurrent>() {
		@Override
		public PacketReadCurrent build(List<String> parameters) {
			return new PacketReadCurrentImpl();
		}
	};

	/**
	 * Constructs a new {@link PacketReadCurrentImpl} with no packet parameters.
	 */
	public PacketReadCurrentImpl() {
		super(PacketReadCurrent.TYPE_CHAR);
	}

}
