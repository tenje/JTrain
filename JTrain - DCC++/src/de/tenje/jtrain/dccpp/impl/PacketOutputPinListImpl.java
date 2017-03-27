package de.tenje.jtrain.dccpp.impl;

import java.util.List;

import de.tenje.jtrain.dccpp.PacketBuilder;
import de.tenje.jtrain.dccpp.PacketOutputPinList;

/**
 * This class is a concrete implementation of the {@link PacketOutputPinList}
 * interface.
 * 
 * @author Jonas Tennié
 */
public class PacketOutputPinListImpl extends PacketOutputPinImpl implements PacketOutputPinList {

	/**
	 * {@link PacketBuilder} to build a {@link PacketOutputPinList}. The
	 * parameter list is ignored.
	 */
	public static final PacketBuilder<PacketOutputPinList> BUILDER = new PacketBuilder<PacketOutputPinList>() {
		@Override
		public PacketOutputPinList build(List<String> parameters) {
			return new PacketOutputPinListImpl();
		}
	};

	/**
	 * Constructs a new {@link PacketOutputPinListImpl} with no packet
	 * parameters.
	 */
	public PacketOutputPinListImpl() {}

}
