package de.tenje.jtrain.dccpp.impl;

import java.util.List;

import de.tenje.jtrain.dccpp.PacketBuilder;
import de.tenje.jtrain.dccpp.PacketSensorStateList;

/**
 * This class is a concrete implementation of the {@link PacketSensorStateList}
 * interface.
 * 
 * @author Jonas Tennié
 */
public class PacketSensorStateListImpl extends PacketSensorStateImpl implements PacketSensorStateList {

	/**
	 * {@link PacketBuilder} to build a {@link PacketSensorStateList}. The
	 * parameter list is ignored.
	 */
	public static final PacketBuilder<PacketSensorStateList> BUILDER = new PacketBuilder<PacketSensorStateList>() {
		@Override
		public PacketSensorStateList build(List<String> parameters) {
			return new PacketSensorStateListImpl();
		}
	};

	/**
	 * Constructs a new {@link PacketSensorStateList} with no packet parameters.
	 */
	public PacketSensorStateListImpl() {
	}

}
