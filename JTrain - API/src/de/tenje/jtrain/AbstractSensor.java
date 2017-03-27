package de.tenje.jtrain;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * This class provides a skeletal implementation of the {@link Sensor} interface
 * to minimize the effort required to implement this interface.
 * 
 * @author Jonas Tenni�
 */
public abstract class AbstractSensor implements Sensor {

	private final AccessoryDecoderAddress address;
	private final Set<SensorListener> listeners = new HashSet<>();

	/**
	 * Constructs a new {@link AbstractSensor} with the specified address.
	 * 
	 * @param address
	 *            The sensor address.
	 * @throws NullPointerException
	 *             Thrown if <code>address</code> is <code>null</code>.
	 */
	public AbstractSensor(AccessoryDecoderAddress address) {
		this.address = Objects.requireNonNull(address, "address");
	}

	@Override
	public AccessoryDecoderAddress getAddress() {
		return address;
	}

	@Override
	public boolean addSensorListener(SensorListener listener) {
		Objects.requireNonNull(listener, "listener");
		return listeners.add(listener);
	}

	@Override
	public boolean removeSensorListener(SensorListener listener) {
		Objects.requireNonNull(listener, "listener");
		return listeners.remove(listener);
	}

	/**
	 * Notifies all registered sensors that the sensor state changed using
	 * {@link SensorListener#sensorStateChanged(Sensor)}.
	 */
	protected void notifyListeners() {
		for (SensorListener listener : listeners) {
			listener.sensorStateChanged(this);
		}
	}

}
