package de.tenje.jtrain.dccpp.impl;

import java.util.List;

import de.tenje.jtrain.dccpp.Packet;
import de.tenje.jtrain.dccpp.PacketBuilder;
import de.tenje.jtrain.dccpp.PacketOutputPin;
import de.tenje.jtrain.dccpp.PacketOutputPinDefine;
import de.tenje.jtrain.dccpp.PacketOutputPinDelete;
import de.tenje.jtrain.dccpp.PacketOutputPinList;
import de.tenje.jtrain.dccpp.PacketOutputPinSetState;

/**
 * This class is an abstract implementation of the {@link PacketOutputPin}
 * interface.
 * 
 * @author Jonas Tennié
 */
public abstract class PacketOutputPinImpl extends AbstractPacket implements PacketOutputPin {

	/**
	 * {@link PacketBuilder} to build a sub packet of {@link PacketOutputPin}.
	 * The size of <code>parameters</code> defines the type of the returned packet:
	 * <table>
	 * <tr>
	 * <td>0</td>
	 * <td>{@link PacketOutputPinList}</td>
	 * </tr>
	 * <tr>
	 * <td>1</td>
	 * <td>{@link PacketOutputPinDelete}</td>
	 * </tr>
	 * <tr>
	 * <td>2</td>
	 * <td>{@link PacketOutputPinSetState}</td>
	 * </tr>
	 * <tr>
	 * <td>3 (or more)</td>
	 * <td>{@link PacketOutputPinDefine}</td>
	 * </tr>
	 * </table>
	 */
	public static final PacketBuilder<PacketOutputPin> BUILDER = new PacketBuilder<PacketOutputPin>() {
		@Override
		public PacketOutputPin build(List<String> parameters) {
			switch (parameters.size()) {
				case 0:
					return PacketOutputPinListImpl.BUILDER.build(parameters);
				case 1:
					return PacketOutputPinDeleteImpl.BUILDER.build(parameters);
				case 2:
					return PacketOutputPinSetStateImpl.BUILDER.build(parameters);
				default: // 3 or more
					return PacketOutputPinDefineImpl.BUILDER.build(parameters);
			}
		}
	};

	/**
	 * Constructs a new <code>PacketOutputPinImpl</code> with the specified raw
	 * packet parameters.
	 * 
	 * @param parameters
	 *            The packet parameters as described in
	 *            {@link Packet#getRawParameters()}.
	 */
	public PacketOutputPinImpl(List<String> parameters) {
		super(PacketOutputPin.TYPE_CHAR, parameters);
	}

	/**
	 * Constructs a new {@link PacketOutputPinImpl} with no parameters.
	 */
	public PacketOutputPinImpl() {
		super(PacketOutputPin.TYPE_CHAR);
	}

}
