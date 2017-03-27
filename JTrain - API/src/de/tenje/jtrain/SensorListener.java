package de.tenje.jtrain;

/**
 * Listens to {@link Sensor} state changes.
 * 
 * @author Jonas Tennié
 */
public interface SensorListener {

	/**
	 * Called after a {@link Sensor}'s state changed.
	 * 
	 * @param sensor
	 *            The sensor which changed its state.
	 */
	void sensorStateChanged(Sensor sensor);

}
