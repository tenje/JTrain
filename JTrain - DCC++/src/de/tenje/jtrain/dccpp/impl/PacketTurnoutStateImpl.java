package de.tenje.jtrain.dccpp.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import de.tenje.jtrain.AccessoryDecoderAddress;
import de.tenje.jtrain.Address;
import de.tenje.jtrain.ParameterValidator;
import de.tenje.jtrain.dccpp.PacketBuilder;
import de.tenje.jtrain.dccpp.PacketTurnoutState;

/**
 * This class is a concrete implementation of the {@link PacketTurnoutState}
 * interface.
 * 
 * @author Jonas Tennié
 */
public class PacketTurnoutStateImpl extends AbstractPacket implements PacketTurnoutState {

	/**
	 * {@link PacketBuilder} to build a {@link PacketTurnoutState}.
	 */
	public static final PacketBuilder<PacketTurnoutState> BUILDER = new PacketBuilder<PacketTurnoutState>() {
		@Override
		public PacketTurnoutState build(List<String> parameters) {
			return new PacketTurnoutStateImpl(parameters);
		}
	};

	private final int id;
	private final AccessoryDecoderAddress address;
	private final boolean thrown;

	/**
	 * Constructs a new {@link PacketTurnoutStateImpl} with the specified id,
	 * address and throw state.
	 * 
	 * @param id
	 *            The ID in range (0-32767).
	 * @param address
	 *            The address.
	 * @param thrown
	 *            The throw state of the turnout.
	 * @throws IllegalArgumentException
	 *             Thrown if id does not lay in range (0-32767).
	 * @throws NullPointerException
	 *             Thrown if <code>address</code> is <code>null</code>.
	 */
	public PacketTurnoutStateImpl(int id, AccessoryDecoderAddress address, boolean thrown) {
		super(PacketTurnoutState.TYPE_CHAR, Arrays.asList(String.valueOf(id), String.valueOf(address.getMainAddress()),
				String.valueOf(address.getSubAddress()), thrown ? "1" : "0"));
		this.id = ParameterValidator.validateRegistrationId(id);
		this.address = Objects.requireNonNull(address, "address");
		this.thrown = thrown;
	}

	/**
	 * Constructs a new {@link PacketTurnoutStateImpl} with the raw packet data
	 * containing the id and throw state.
	 * 
	 * @param parameters
	 *            The raw packet parameters. The first index (0) is the id. The
	 *            second index (1) and third (2) index is the address. The
	 *            fourth index (3) is the throw state ({@code "1"} for
	 *            <code>true</code>, other value (not <code>null</code>) for
	 *            <code>false</code>).
	 * @throws IllegalArgumentException
	 *             Thrown if id does not lay in range (0-32767) or address does
	 *             not lay in range.
	 * @throws NumberFormatException
	 *             Thrown if one of the indexes 0-2 is not a number or is
	 *             <code>null</code>.
	 * @throws IndexOutOfBoundsException
	 *             Thrown if size of <code>parameters</code> less than three.
	 * @throws NullPointerException
	 *             Thrown if second index (1) is <code>null</code>.
	 */
	public PacketTurnoutStateImpl(List<String> parameters) {
		super(PacketTurnoutState.TYPE_CHAR, parameters);
		id = ParameterValidator.validateRegistrationId(Integer.parseInt(parameters.get(0)));
		address = new AccessoryDecoderAddress(Integer.parseInt(parameters.get(1)), Integer.parseInt(parameters.get(2)));
		thrown = parameters.get(3).equals("1");
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public Address getAddress() {
		return address;
	}

	@Override
	public boolean isThrown() {
		return thrown;
	}

}
