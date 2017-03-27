package de.tenje.jtrain.dccpp;

/**
 * {@link Packet} to clear settings for turn-outs and sensors from the
 * non-volatile memory.
 * 
 * <table>
 * <tr>
 * <td>Type char:</td>
 * <td>'e'</td>
 * </tr>
 * <tr>
 * <td valign="top">Packet format:</td>
 * <td ><i>&lt;e&gt;</i></td>
 * </tr>
 * <tr>
 * <td>Return packet:</td>
 * <td>{@link PacketOperationSuccessful}</td>
 * </tr>
 * </table>
 * 
 * @author Jonas Tennié
 */
public interface PacketClearSettings extends Packet {

	/**
	 * The type char of the <code>PacketClearSettings</code>.
	 */
	public static final char TYPE_CHAR = 'e';

}
