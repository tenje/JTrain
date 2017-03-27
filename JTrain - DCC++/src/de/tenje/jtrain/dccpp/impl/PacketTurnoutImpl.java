package de.tenje.jtrain.dccpp.impl;

import java.util.List;

import de.tenje.jtrain.dccpp.Packet;
import de.tenje.jtrain.dccpp.PacketBuilder;
import de.tenje.jtrain.dccpp.PacketTurnout;
import de.tenje.jtrain.dccpp.PacketTurnoutDefine;
import de.tenje.jtrain.dccpp.PacketTurnoutDelete;
import de.tenje.jtrain.dccpp.PacketTurnoutList;
import de.tenje.jtrain.dccpp.PacketTurnoutThrow;

/**
 * This class is an abstract implementation of the {@link PacketTurnout}
 * interface.
 * 
 * @author Jonas Tennié
 */
public abstract class PacketTurnoutImpl extends AbstractPacket implements PacketTurnout {

	/**
	 * {@link PacketBuilder} to build a sub packet of {@link PacketTurnout}. The
	 * size of <code>parameters</code> defines the type of the returned packet:
	 * <table>
	 * <tr>
	 * <td>0</td>
	 * <td>{@link PacketTurnoutList}</td>
	 * </tr>
	 * <tr>
	 * <td>1</td>
	 * <td>{@link PacketTurnoutDelete}</td>
	 * </tr>
	 * <tr>
	 * <td>2</td>
	 * <td>{@link PacketTurnoutThrow}</td>
	 * </tr>
	 * <tr>
	 * <td>3 (or more)</td>
	 * <td>{@link PacketTurnoutDefine}</td>
	 * </tr>
	 * </table>
	 */
	public static final PacketBuilder<PacketTurnout> BUILDER = new PacketBuilder<PacketTurnout>() {
		@Override
		public PacketTurnout build(List<String> parameters) {
			switch (parameters.size()) {
				case 0:
					return PacketTurnoutListImpl.BUILDER.build(parameters);
				case 1:
					return PacketTurnoutDeleteImpl.BUILDER.build(parameters);
				case 2:
					return PacketTurnoutThrowImpl.BUILDER.build(parameters);
				default: // 3 or more
					return PacketTurnoutDefineImpl.BUILDER.build(parameters);
			}
		}
	};

	/**
	 * Constructs a new <code>PacketTurnoutImpl</code> with the specified raw packet
	 * parameters.
	 * 
	 * @param parameters
	 *            The packet parameters as described in
	 *            {@link Packet#getRawParameters()}.
	 */
	public PacketTurnoutImpl(List<String> parameters) {
		super(PacketTurnout.TYPE_CHAR, parameters);
	}

	/**
	 * Constructs a new {@link PacketTurnoutImpl} with no parameters.
	 */
	public PacketTurnoutImpl() {
		super(PacketTurnout.TYPE_CHAR);
	}

}
