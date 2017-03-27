package de.tenje.jtrain.dccpp.impl;

import java.util.List;

import de.tenje.jtrain.dccpp.Packet;
import de.tenje.jtrain.dccpp.PacketBuilder;
import de.tenje.jtrain.dccpp.PacketSensorState;
import de.tenje.jtrain.dccpp.PacketSensorStateActive;
import de.tenje.jtrain.dccpp.PacketSensorStateList;

/**
 * This class is a concrete implementation of the {@link PacketSensorState}
 * interface.
 * 
 * @author Jonas Tennié
 */
public class PacketSensorStateImpl extends AbstractPacket implements PacketSensorState {

	/**
	 * {@link PacketBuilder} to build a sub packet of {@link PacketSensorState}.
	 * The size of <code>parameters</code> defines the type of the returned packet:
	 * <table>
	 * <tr>
	 * <td>0</td>
	 * <td>{@link PacketSensorStateList}</td>
	 * </tr>
	 * <tr>
	 * <td>1 (or more)</td>
	 * <td>{@link PacketSensorStateActive}</td>
	 * </tr>
	 * </table>
	 */
	public static final PacketBuilder<PacketSensorState> BUILDER = new PacketBuilder<PacketSensorState>() {
		@Override
		public PacketSensorState build(List<String> parameters) {
			switch (parameters.size()) {
				case 0:
					return PacketSensorStateListImpl.BUILDER.build(parameters);
				case 1:
					return PacketSensorStateActiveImpl.BUILDER.build(parameters);
				default: // 2 or more
					return PacketSensorDataImpl.BUILDER.build(parameters);
			}
		}
	};

	/**
	 * Constructs a new <code>PacketSensorState</code> with the specified raw packet
	 * parameters.
	 * 
	 * @param parameters
	 *            The packet parameters as described in
	 *            {@link Packet#getRawParameters()}.
	 */
	public PacketSensorStateImpl(List<String> parameters) {
		super(PacketSensorState.TYPE_CHAR, parameters);
	}

	/**
	 * Constructs a new {@link PacketSensorState} with no parameters.
	 */
	public PacketSensorStateImpl() {
		super(PacketSensorState.TYPE_CHAR);
	}

}
