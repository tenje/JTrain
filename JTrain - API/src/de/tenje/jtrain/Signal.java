package de.tenje.jtrain;

import java.util.Collections;
import java.util.Set;

/**
 * A railroad signal.
 * 
 * @author Jonas Tennié
 */
public interface Signal extends Addressable {

	@Override
	AccessoryDecoderAddress getAddress();

	/**
	 * Sets this signal's aspect.
	 * 
	 * @param aspect
	 *            The aspect to set.
	 * @throws UnsupportedOperationException
	 *             Thrown if the <code>aspect</code> is not supported by this
	 *             signal.
	 * @throws NullPointerException
	 *             Thrown if <code>aspect</code> is <code>null</code>.
	 */
	void setAspect(SignalAspect aspect);

	/**
	 * Returns this signal's current aspect.
	 * 
	 * @return This signal's aspect.
	 */
	SignalAspect getAspect();

	/**
	 * Returns an unmodifiable set of all {@link SignalAspect}s supported by
	 * this signal.
	 * 
	 * @return An unmodifiable set of all supported signal aspects.
	 * @see Collections#unmodifiableSet(Set)
	 */
	Set<SignalAspect> getSupportedAspects();

}
