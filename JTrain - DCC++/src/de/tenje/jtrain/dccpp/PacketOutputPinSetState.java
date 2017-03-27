package de.tenje.jtrain.dccpp;

/**
 * {@link PacketOutputPin} to set the state of an output pin.
 * 
 * <table>
 * <tr>
 * <td>Type char:</td>
 * <td>'Z'</td>
 * </tr>
 * <tr>
 * <td valign="top">Format:</td>
 * <td><i>&lt;Z ID State&gt;</i></td>
 * </tr>
 * <tr>
 * <td valign="top">Return packets:</td>
 * <td>{@link PacketOutputPinState} on success,<br>
 * {@link PacketOperationFailed} on failure</td>
 * </tr>
 * </table>
 * 
 * @author Jonas Tennié
 */
public interface PacketOutputPinSetState extends RegistrationIdHolder, PacketOutputPin {

	/**
	 * Returns the state to set for the output pin.
	 * 
	 * @return The state to set for the output pin. <code>true</code> for HIGH,
	 *         <code>false</code> for LOW.
	 */
	boolean getState();

}
