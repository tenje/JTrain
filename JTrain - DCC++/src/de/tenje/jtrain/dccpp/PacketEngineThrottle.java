package de.tenje.jtrain.dccpp;

import de.tenje.jtrain.Addressable;
import de.tenje.jtrain.LongTrainAddress;
import de.tenje.jtrain.TrainDirection;

/**
 * A {@link Packet} to set a train's speed and direction.
 * 
 * <table>
 * <tr>
 * <td>Type char:</td>
 * <td>'t'</td>
 * </tr>
 * <tr>
 * <td valign="top">Packet format:</td>
 * <td ><i>&lt;t Register Address Speed Direction&gt;</i><br>
 * <i>Register:</i> An internal register number to store the packet<br>
 * <i>Address:</i> Train decoder address in range (1-10293)<br>
 * <i>Speed:</i> Engine speed in range (0-126), -1 for emergency stop<br>
 * <i>Direction:</i> The train direction;
 * 0&#8793;{@link TrainDirection#REVERSE},
 * 1&#8793;{@link TrainDirection#FORWARD}. Also used for lighting</td>
 * </tr>
 * <tr>
 * <td>Return packet:</td>
 * <td>TODO</td>
 * </tr>
 * </table>
 * 
 * @author Jonas Tennié
 */
public interface PacketEngineThrottle extends Addressable, Packet, Registrable {

	/**
	 * The type char of the <code>PacketEngineThrottle</code>.
	 */
	public static final char TYPE_CHAR = 't';

	@Override
	LongTrainAddress getAddress();

	/**
	 * Returns the speed to set using 128-step speed (0-126, -1 for emergency
	 * break).
	 * 
	 * @return The speed to set.
	 */
	int getSpeed();

	/**
	 * Returns the direction of the train.
	 * 
	 * @return The direction of the train.
	 */
	TrainDirection getDirection();

}
