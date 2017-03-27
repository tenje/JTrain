package de.tenje.jtrain.dccpp;

/**
 * {@link Packet} to return an output pin's state.
 * 
 * @author Jonas Tennié
 */
public interface PacketOutputPinState extends RegistrationIdHolder, Packet {

	/**
	 * The type char of the {@link PacketOutputPinState}.
	 */
	public static final char TYPE_CHAR = 'Y';

	/**
	 * Returns the state of the output pin.
	 * 
	 * @return The state of the output pin. <code>true</code> for HIGH, <code>false</code>
	 *         for LOW.
	 */
	boolean getState();

}
