package de.tenje.jtrain.dccpp;

/**
 * {@link PacketTurnout} to set a turnout thrown or unthrown.
 * 
 * @author Jonas Tennié
 */
public interface PacketTurnoutThrow extends RegistrationIdHolder, PacketTurnout {

	/**
	 * Returns whether the turnout is thrown.
	 * 
	 * @return <code>true</code> if thrown, <code>false</code> if unthrown.
	 */
	boolean isThrown();

}
