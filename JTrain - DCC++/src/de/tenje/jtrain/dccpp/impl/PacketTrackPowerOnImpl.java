package de.tenje.jtrain.dccpp.impl;

import java.util.List;

import de.tenje.jtrain.dccpp.PacketBuilder;
import de.tenje.jtrain.dccpp.PacketTrackPowerOn;

/**
 * This class is a concrete implementation of the {@link PacketTrackPowerOn}
 * interface.
 * 
 * @author Jonas Tennié
 */
public class PacketTrackPowerOnImpl extends AbstractPacket implements PacketTrackPowerOn {

	/**
	 * {@link PacketBuilder} to build a {@link PacketTrackPowerOn}. The
	 * parameter list is ignored.
	 */
	public static final PacketBuilder<PacketTrackPowerOn> BUILDER = new PacketBuilder<PacketTrackPowerOn>() {
		@Override
		public PacketTrackPowerOn build(List<String> parameters) {
			return new PacketTrackPowerOnImpl();
		}
	};

	/**
	 * Constructs a new {@link PacketTrackPowerOn} with no packet parameters.
	 */
	public PacketTrackPowerOnImpl() {
		super(PacketTrackPowerOn.TYPE_CHAR);
	}

}
