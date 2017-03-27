package de.tenje.jtrain.dccpp.impl;

import java.util.List;

import de.tenje.jtrain.dccpp.PacketBuilder;
import de.tenje.jtrain.dccpp.PacketReadStationState;

/**
 * This class is a concrete implementation of the {@link PacketReadStationState}
 * interface.
 * 
 * @author Jonas Tennié
 */
public class PacketReadStationStateImpl extends AbstractPacket implements PacketReadStationState {

	/**
	 * {@link PacketBuilder} to build a {@link PacketReadStationState}. The
	 * parameter list is ignored.
	 */
	public static final PacketBuilder<PacketReadStationState> BUILDER = new PacketBuilder<PacketReadStationState>() {
		@Override
		public PacketReadStationState build(List<String> parameters) {
			return new PacketReadStationStateImpl();
		}
	};

	/**
	 * Constructs a new {@link PacketReadStationStateImpl} with no packet
	 * parameters.
	 */
	public PacketReadStationStateImpl() {
		super(PacketReadStationState.TYPE_CHAR);
	}

}
