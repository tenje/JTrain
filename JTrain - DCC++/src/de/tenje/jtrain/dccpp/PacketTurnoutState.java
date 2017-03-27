package de.tenje.jtrain.dccpp;

import de.tenje.jtrain.Addressable;

/**
 * A packet to set the state of a turnout. This interface is not a sub type of
 * {@link PacketTurnout} as it has an other type char.
 * 
 * @author Jonas Tennié
 */
public interface PacketTurnoutState extends RegistrationIdHolder, Addressable, Packet {

	/**
	 * The type char of the <code>PacketTurnoutState</code>.
	 */
	public static final char TYPE_CHAR = 'H';

	/**
	 * Returns whether the turnout is thrown.
	 * 
	 * @return <code>true</code> if thrown, <code>false</code> if unthrown.
	 */
	boolean isThrown();

}
