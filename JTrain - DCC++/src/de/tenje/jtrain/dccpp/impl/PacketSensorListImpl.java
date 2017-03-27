package de.tenje.jtrain.dccpp.impl;

import java.util.List;

import de.tenje.jtrain.dccpp.PacketBuilder;
import de.tenje.jtrain.dccpp.PacketSensorList;

/**
 * This class is a concrete implementation of the {@link PacketSensorList}
 * interface.
 * 
 * @author Jonas Tennié
 */
public class PacketSensorListImpl extends PacketSensorImpl implements PacketSensorList {

	/**
	 * {@link PacketBuilder} to build a {@link PacketSensorList}. The parameter
	 * list is ignored.
	 */
	public static final PacketBuilder<PacketSensorList> BUILDER = new PacketBuilder<PacketSensorList>() {
		@Override
		public PacketSensorList build(List<String> parameters) {
			return new PacketSensorListImpl();
		}
	};

	/**
	 * Constructs a new {@link PacketSensorListImpl} with no packet parameters.
	 */
	public PacketSensorListImpl() {
	}

}
