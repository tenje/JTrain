package de.tenje.jtrain.dccpp.impl;

import java.util.List;

import de.tenje.jtrain.dccpp.PacketBuilder;
import de.tenje.jtrain.dccpp.PacketTurnoutList;

/**
 * This class is a concrete implementation of the {@link PacketTurnoutList}
 * interface.
 * 
 * @author Jonas Tennié
 */
public class PacketTurnoutListImpl extends PacketTurnoutImpl implements PacketTurnoutList {

	/**
	 * {@link PacketBuilder} to build a {@link PacketTurnoutList}.
	 */
	public static final PacketBuilder<PacketTurnoutList> BUILDER = new PacketBuilder<PacketTurnoutList>() {
		@Override
		public PacketTurnoutList build(List<String> parameters) {
			return new PacketTurnoutListImpl();
		}
	};

	/**
	 * Constructs a new {@link PacketTurnoutListImpl} with no packet parameters.
	 */
	public PacketTurnoutListImpl() {
	}

}
