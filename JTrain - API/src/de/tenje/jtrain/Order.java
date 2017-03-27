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
