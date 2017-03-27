package de.tenje.jtrain.dccpp.impl;

import java.util.Arrays;
import java.util.List;

import de.tenje.jtrain.dccpp.PacketBuilder;
import de.tenje.jtrain.dccpp.PacketSensorDelete;

/**
 * This class is a concrete implementation of the {@link PacketSensorDelete}
 * interface.
 * 
 * @author Jonas Tennié
 */
public class PacketSensorDeleteImpl extends PacketSensorImpl implements PacketSensorDelete {

	/**
	 * {@link PacketBuilder} to build a {@link PacketSensorDelete}.
	 */
	public static final PacketBuilder<PacketSensorDelete> BUILDER = new PacketBuilder<PacketSensorDelete>() {
		@Override
		public PacketSensorDelete build(List<String> parameters) {
			return new PacketSensorDeleteImpl(parameters);
		}
	};

	private final int id;

	/**
	 * Constructs a new {@link PacketSensorDeleteImpl} with the specified id.
	 * 
	 * @param id
	 *            The ID in range (0-32767).
	 * @throws IllegalArgumentException
	 *             Thrown if <code>id</code> does not lay in range (0-32767).
	 */
	public PacketSensorDeleteImpl(int id) {
		super(Arrays.asList(String.valueOf(id)));
		if (id < 0 || id > 32767) {
			throw new IllegalArgumentException("id value out of valid range: " + id);
		}
		this.id = id;
	}

	/**
	 * Constructs a new {@link PacketSensorDeleteImpl} with the raw packet data
	 * containing the id.
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
	public PacketSensorDeleteImpl(List<String> parameters) {
		super(parameters);
		id = Integer.parseInt(parameters.get(0));
		if (id < 0 || id > 32767) {
			throw new IllegalArgumentException("id value out of valid range: " + id);
		}
	}

	@Override
	public int getId() {
		return id;
	}

}
