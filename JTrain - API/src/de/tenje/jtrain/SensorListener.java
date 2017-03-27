package de.tenje.jtrain;

/**
 * Listens to {@link Sensor} state changes.
 * 
 * @author Jonas Tenni�
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
