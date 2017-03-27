package de.tenje.jtrain.dccpp.impl;

import java.util.Arrays;
import java.util.List;

import de.tenje.jtrain.ParameterValidator;
import de.tenje.jtrain.dccpp.PacketBuilder;
import de.tenje.jtrain.dccpp.PacketReadConfigurationByte;

/**
 * This class is a concrete implementation of the
 * {@link PacketReadConfigurationByte} interface.
 * 
 * @author Jonas Tennié
 */
public class PacketReadConfigurationByteImpl extends AbstractPacket implements PacketReadConfigurationByte {

	/**
	 * {@link PacketBuilder} to build a {@link PacketReadConfigurationByte}.
	 */
	public static final PacketBuilder<PacketReadConfigurationByte> BUILDER = new PacketBuilder<PacketReadConfigurationByte>() {
		@Override
		public PacketReadConfigurationByte build(List<String> parameters) {
			return new PacketReadConfigurationByteImpl(parameters);
		}
	};

	private final int callbackNum, callbackSum, variableLocation;

	/**
	 * Constructs a new {@link PacketReadConfigurationByteImpl} with the
	 * specified callback number/sum and variable location.
	 * 
	 * @param callbackNumber
	 *            The callback number.
	 * @param callbackSum
	 *            The callback sum.
	 * @param variableLocation
	 *            The variable location.
	 */
	public PacketReadConfigurationByteImpl(int callbackNumber, int callbackSum, int variableLocation) {
		super(PacketReadConfigurationByte.TYPE_CHAR, Arrays.asList(String.valueOf(callbackNumber),
				String.valueOf(callbackSum), String.valueOf(variableLocation)));
		this.callbackNum = ParameterValidator.validateCallbackNumber(callbackNumber);
		this.callbackSum = ParameterValidator.validateCallbackNumber(callbackSum);
		this.variableLocation = ParameterValidator.validateVariableLocation(variableLocation);
	}

	/**
	 * Constructs a new <code>PacketReadConfigurationByteImpl</code> with the raw
	 * packet parameters containing the callback number/sum and variable
	 * location.
	 * 
	 * @param parameters
	 *            The raw packet parameters. First index (0): callback number,
	 *            second index (1): callback sum, third index (2): variable
	 *            location.
	 * @throws NullPointerException
	 *             Thrown if one of the indexes 0-2 of <code>parameters</code> is
	 *             <code>null</code>.
	 * @throws IllegalArgumentException
	 *             Thrown if callback number, callback sum or variable location
	 *             lays out of valid range.
	 */
	public PacketReadConfigurationByteImpl(List<String> parameters) {
		super(PacketReadConfigurationByte.TYPE_CHAR, parameters);
		callbackNum = ParameterValidator.validateCallbackNumber(Integer.parseInt(parameters.get(0)));
		callbackSum = ParameterValidator.validateCallbackNumber(Integer.parseInt(parameters.get(1)));
		variableLocation = ParameterValidator.validateVariableLocation(Integer.parseInt(parameters.get(2)));
	}

	@Override
	public int getCallbackNumber() {
		return callbackNum;
	}

	@Override
	public int getCallbackSum() {
		return callbackSum;
	}

	@Override
	public int getVariableLocation() {
		return variableLocation;
	}

}
