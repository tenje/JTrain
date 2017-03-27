package de.tenje.jtrain.dccpp.impl;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import de.tenje.jtrain.dccpp.PacketBuilder;
import de.tenje.jtrain.dccpp.PacketStationInfo;

/**
 * This class is a concrete implementation of the {@link PacketStationInfo}
 * interface.
 * 
 * @author Jonas Tennié
 */
public class PacketStationInfoImpl extends AbstractPacket implements PacketStationInfo {

	/**
	 * {@link PacketBuilder} to build a {@link PacketStationInfo}.
	 */
	public static final PacketBuilder<PacketStationInfo> BUILDER = new PacketBuilder<PacketStationInfo>() {
		@Override
		public PacketStationInfo build(List<String> parameters) {
			return new PacketStationInfoImpl(parameters);
		}
	};

	private final String info;

	/**
	 * Constructs a new {@link PacketStationInfoImpl} with the specified info
	 * string. The string will be trimmed and double spaces will be replaced
	 * with single spaces.
	 * 
	 * @param info
	 *            The info string.
	 * @throws NullPointerException
	 *             Thrown if <code>info</code> is <code>null</code>.
	 */
	public PacketStationInfoImpl(String info) {
		super(PacketStationInfo.TYPE_CHAR, Arrays.asList(info.split(" *")));
		this.info = Objects.requireNonNull(info, "info").trim().replaceAll(" *", " ");
	}

	/**
	 * Constructs a new {@link PacketStationInfoImpl} with the raw packet data
	 * containing the info string. The string will be trimmed and double spaces
	 * will be replaced with single spaces.
	 * 
	 * @param parameters
	 *            A list of of all words contained in the info string.
	 */
	public PacketStationInfoImpl(List<String> parameters) {
		super(PacketStationInfo.TYPE_CHAR, parameters);
		StringBuilder builder = new StringBuilder();
		Iterator<String> iter = parameters.iterator();
		String next;
		while (iter.hasNext()) {
			next = iter.next();
			if (next == null) {
				throw new NullPointerException("at least one index is null");
			}
			builder.append(next);
			if (iter.hasNext()) {
				builder.append(' ');
			}
		}
		info = builder.toString().trim().replaceAll(" *", " ");
	}

	@Override
	public String getInfo() {
		return info;
	}

}
