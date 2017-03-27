package de.tenje.jtrain.dccpp;

import de.tenje.jtrain.AccessoryDecoderAddress;
import de.tenje.jtrain.Addressable;

/**
 * A {@link Packet} to turn an accessory (stationary) decoder's output on or
 * off.
 * <table>
 * <tr>
 * <td>Type char:</td>
 * <td>'a'</td>
 * </tr>
 * <tr>
 * <td valign="top">Packet format:</td>
 * <td ><i>&lt;a Address Sub-Address Activate&gt;</i><br>
 * <i>Address:</i> Decoder address in range (0-511)<br>
 * <i>Sub-Address:</i> Decoder Sub-Address in range (0-3)<br>
 * <i>Activate:</i> Whether decoder output should be activated (<i>1</i>) or
 * deactivated (<i>0</i>)</td>
 * </tr>
 * <tr>
 * <td>Return packet:</td>
 * <td>none</td>
 * </tr>
 * </table>
 * 
 * @author Jonas Tennié
 */
public interface PacketAccessoryOperate extends Addressable, Packet {

	/**
	 * The type char of the <code>PacketAccessory</code>.
	 */
	public static final char TYPE_CHAR = 'a';

	@Override
	AccessoryDecoderAddress getAddress();

	/**
	 * Returns whether the decoder should turn on (active) or off (inactive)
	 * 
	 * @return <code>true</code> to activate decoder output, <code>false</code> to
	 *         deactivate decoder output.
	 */
	boolean isActive();

}
