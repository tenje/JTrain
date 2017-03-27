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
package de.tenje.jtrain;

import java.util.Collection;

/**
 * The order of elements in a collection.
 * 
 * @author Jonas Tennié
 */
public enum Order {

	/**
	 * Elements are returned randomly.
	 */
	RANDOM,

	/**
	 * Elements are returned infinity in the order they have in their
	 * {@link Collection}. {a, b, c} will return {a, b, c, a, b, c, a, ...}.
	 */
	FIRST_TO_LAST,

	/**
	 * Elements are returned in the order they have in their {@link Collection}
	 * and than backward in an infinity circle. {a, b, c} will return {a, b, c,
	 * b, a, b, ...}.
	 */
	FIRST_TO_LAST_TO_FIRST;

}
