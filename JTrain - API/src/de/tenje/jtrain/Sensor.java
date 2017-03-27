package de.tenje.jtrain;

/**
 * A simple sensor which may be triggered or not. A {@link SensorListener} can
 * be registered to listen to sensor states using
 * {@link #addSensorListener(SensorListener)}.
 * 
 * @author Jonas Tennié
 */
public interface Sensor extends Addressable {

	@Override
	AccessoryDecoderAddress getAddress();

	/**
	 * Returns whether this sensor is triggered.
	 * 
	 * @return <code>true</code> if triggered, else <code>false</code>.
	 */
	boolean isTriggered();

	/**
	 * Adds a {@link SensorListener} to this sensor. Equality is checked using
	 * {@link SensorListener#equals(Object)}.
	 * 
	 * @param listener
	 *            The listener to add.
	 * @return <code>true</code> if the listener was added, <code>false</code> if the
	 *         listener was already added to this sensor.
	 * @throws NullPointerException
	 *             Thrown if <code>listener</code> is <code>null</code>.
	 */
	boolean addSensorListener(SensorListener listener);

	/**
	 * Removes a {@link SensorListener} from this sensor. Equality is checked
	 * using {@link SensorListener#equals(Object)}.
	 * 
	 * @param listener
	 *            The listener to remove.
	 * @return <code>true</code> if the listener was removed, <code>false</code> if the
	 *         listener was not added to this sensor before.
	 * @throws NullPointerException
	 *             Thrown if <code>listener</code> is <code>null</code>.
	 */
	boolean removeSensorListener(SensorListener listener);

}
