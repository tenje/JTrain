/*******************************************************************************
 * Copyright (c): Jonas Tennié 2017
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Lesser Public License for more
 * details.
 * You should have received a copy of the GNU General Lesser Public License
 * along with this program. If not, see
 * <http://www.gnu.org/licenses/lgpl-3.0.html>.
 *******************************************************************************/
package org.tenje.jtrain;

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
