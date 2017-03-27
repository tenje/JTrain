package de.tenje.jtrain;

/**
 * The aspect of a {@link Signal}.
 * 
 * @author Jonas Tennié
 */
public enum SignalAspect {

	/**
	 * Passing permitted.
	 */
	GREEN,

	/**
	 * Stop.
	 */
	RED,

	/**
	 * Reduce velocity; expect next signal to be red.
	 */
	YELLOW,

	/**
	 * Reduce velocity; thrown switches in the route. {@link #YELLOW} is shown
	 * if the {@link Signal} does not support 4 aspects.
	 */
	WHITE;

}
