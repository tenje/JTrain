package de.tenje.jtrain;

/**
 * This class provides a skeletal implementation of the {@link Switchable}
 * interface to minimize the effort required to implement this interface.
 * 
 * @author Jonas Tenni�
 */
public abstract class AbstractSwitchable implements Switchable {

	@Override
	public void toggle() {
		setSwitched(!isSwitched());
	}

}
