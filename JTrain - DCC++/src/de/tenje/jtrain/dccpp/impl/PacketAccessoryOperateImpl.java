package de.tenje.jtrain.dccpp.impl;

import java.util.Arrays;
import java.util.List;

import de.tenje.jtrain.AccessoryDecoderAddress;
import de.tenje.jtrain.dccpp.PacketAccessoryOperate;
import de.tenje.jtrain.dccpp.PacketBuilder;

/**
 * This class is a concrete implementation of the {@link PacketAccessoryOperate}
 * interface.
 * 
 * @author Jonas Tennié
 */
public class PacketAccessoryOperateImpl extends AbstractPacket implements PacketAccessoryOperate {

	/**
	 * {@link PacketBuilder} to build a {@link PacketAccessoryOperate}.
	 */
	public static final PacketBuilder<PacketAccessoryOperate> BUILDER = new PacketBuilder<PacketAccessoryOperate>() {
		@Override
		public PacketAccessoryOperate build(List<String> parameters) {
			return new PacketAccessoryOperateImpl(parameters);
		}
	};

	private final AccessoryDecoderAddress address;
	private final boolean isActive;

	/**
	 * Constructs a new {@link PacketTurnoutThrowImpl} with the specified
	 * address sub address and is-active value.
	 * 
	 * @param address
	 *            The address.
	 * @param isActive
	 *            Whether the decoder should turn on (active) or off (inactive).
	 * @throws NullPointerException
	 *             Thrown if <code>address</code> is <code>null</code>.
	 */
	public PacketAccessoryOperateImpl(AccessoryDecoderAddress address, boolean isActive) {
		super(PacketAccessoryOperate.TYPE_CHAR, Arrays.asList(String.valueOf(address.getMainAddress()),
				String.valueOf(address.getSubAddress()), isActive ? "1" : "0"));
		this.address = address;
		this.isActive = isActive;
	}

	/**
	 * Constructs a new {@link PacketAccessoryOperateImpl} with the raw packet
	 * data containing the address, sub address and is-active value.
	 * 
	 * @param parameters
	 *            The raw packet parameters. The first index (0) is the address.
	 *            The second index (1) is the sub address. The third (2) index
	 *            is the is-active value ({@code "1"} for <code>true</code>, other
	 *            value (not <code>null</code>) for <code>false</code>).
	 * @throws NullPointerException
	 *             Thrown if index 2 is <code>null</code>.
	 * @throws IllegalArgumentException
	 *             Thrown if address does not lay in range (0-511) or sub
	 *             address does not lay in range (0-3).
	 * @throws NumberFormatException
	 *             Thrown if one of the indexes 0-2 is not a number or is
	 *             <code>null</code>.
	 * @throws IndexOutOfBoundsException
	 *             Thrown if size of <code>parameters</code> less than three.
	 */
	public PacketAccessoryOperateImpl(List<String> parameters) {
		super(PacketAccessoryOperate.TYPE_CHAR, parameters);
		address = new AccessoryDecoderAddress(Integer.parseInt(parameters.get(0)), Integer.parseInt(parameters.get(0)));
		isActive = parameters.get(2).equals("1");
	}

	@Override
	public AccessoryDecoderAddress getAddress() {
		return address;
	}

	@Override
	public boolean isActive() {
		return isActive;
	}

}
