package de.tenje.jtrain.dccpp.impl;

import java.util.List;

import de.tenje.jtrain.dccpp.PacketBuilder;
import de.tenje.jtrain.dccpp.PacketTrackPowerOff;

/**
 * This class is a concrete implementation of the {@link PacketTrackPowerOff}
 * interface.
 * 
 * @author Jonas Tennié
 */
public class PacketTrackPowerOffImpl extends AbstractPacket implements PacketTrackPowerOff {

	/**
	 * {@link PacketBuilder} to build a {@link PacketTrackPowerOff}. The
	 * parameter list is ignored.
	 */
	public static final PacketBuilder<PacketTrackPowerOff> BUILDER = new PacketBuilder<PacketTrackPowerOff>() {
		@Override
		public PacketTrackPowerOff build(List<String> parameters) {
			return new PacketTrackPowerOffImpl();
		}
	};

	/**
	 * Constructs a new {@link PacketTrackPowerOffImpl} with no packet
	 * parameters.
	 */
	public PacketTrackPowerOffImpl() {
		super(PacketTrackPowerOff.TYPE_CHAR);
	}

}
