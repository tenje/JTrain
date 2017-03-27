package de.tenje.jtrain.dccpp.impl;

import java.util.List;

import de.tenje.jtrain.dccpp.PacketBuilder;
import de.tenje.jtrain.dccpp.PacketListBitContents;

/**
 * This class is a concrete implementation of the {@link PacketListBitContents}
 * interface.
 * 
 * @author Jonas Tennié
 */
public class PacketListBitContentsImpl extends AbstractPacket implements PacketListBitContents {

	/**
	 * {@link PacketBuilder} to build a {@link PacketListBitContents}. The
	 * parameter list is ignored.
	 */
	public static final PacketBuilder<PacketListBitContents> BUILDER = new PacketBuilder<PacketListBitContents>() {
		@Override
		public PacketListBitContents build(List<String> parameters) {
			return new PacketListBitContentsImpl();
		}
	};

	/**
	 * Constructs a new {@link PacketListBitContentsImpl} with no packet
	 * parameters.
	 */
	public PacketListBitContentsImpl() {
		super(PacketListBitContents.TYPE_CHAR);
	}

}
