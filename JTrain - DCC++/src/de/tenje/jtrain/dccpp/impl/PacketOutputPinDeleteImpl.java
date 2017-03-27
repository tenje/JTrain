package de.tenje.jtrain.dccpp.impl;

import java.util.Arrays;
import java.util.List;

import de.tenje.jtrain.dccpp.PacketBuilder;
import de.tenje.jtrain.dccpp.PacketOutputPinDelete;

/**
 * This class is a concrete implementation of the {@link PacketOutputPinDelete}
 * interface.
 * 
 * @author Jonas Tennié
 */
public class PacketOutputPinDeleteImpl extends PacketOutputPinImpl implements PacketOutputPinDelete {

	/**
	 * {@link PacketBuilder} to build a {@link PacketOutputPinDelete}.
	 */
	public static final PacketBuilder<PacketOutputPinDelete> BUILDER = new PacketBuilder<PacketOutputPinDelete>() {
		@Override
		public PacketOutputPinDelete build(List<String> parameters) {
			return new PacketOutputPinDeleteImpl(parameters);
		}
	};

	private final int id;

	/**
	 * Constructs a new {@link PacketOutputPinDeleteImpl} with the raw packet
	 * data containing the id.
	 * 
	 * @param parameters
	 *            The raw packet parameters. The first index (0) is the id.
	 * @throws IllegalArgumentException
	 *             Thrown if id does not lay in range (0-32767).
	 * @throws NumberFormatException
	 *             Thrown if the first index (0) is not a number or is
	 *             <code>null</code>.
	 * @throws IndexOutOfBoundsException
	 *             Thrown if <code>parameters</code> is empty.
	 */
	public PacketOutputPinDeleteImpl(List<String> parameters) {
		super(parameters);
		id = Integer.parseInt(parameters.get(0));
		if (id < 0 || id > 32767) {
			throw new IllegalArgumentException("id value out of valid range: " + id);
		}
	}

	/**
	 * Constructs a new {@link PacketOutputPinDeleteImpl} with the specified id.
	 * 
	 * @param id
	 *            The ID in range (0-32767).
	 * @throws IllegalArgumentException
	 *             Thrown if <code>id</code> does not lay in range (0-32767).
	 */
	public PacketOutputPinDeleteImpl(int id) {
		super(Arrays.asList(String.valueOf(id)));
		if (id < 0 || id > 32767) {
			throw new IllegalArgumentException("id value out of valid range: " + id);
		}
		this.id = id;
	}

	@Override
	public int getId() {
		return id;
	}

}
