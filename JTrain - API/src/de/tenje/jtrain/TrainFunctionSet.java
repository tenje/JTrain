package de.tenje.jtrain;

import java.util.Objects;
import java.util.Set;

/**
 * A set of {@link TrainFunction}s which can be registered for the same function
 * id.
 * 
 * @author Jonas Tennié
 */
public class TrainFunctionSet extends AbstractSwitchable implements TrainFunction {

	private final Set<TrainFunction> functions;
	private boolean enabled;

	/**
	 * Constructs a new {@link TrainFunctionSet} as a representation of the
	 * defined set.
	 * 
	 * @param functions
	 *            The function set.
	 * @throws NullPointerException
	 *             Thrown if <code>functions</code> is <code>null</code>.
	 */
	public TrainFunctionSet(Set<TrainFunction> functions) {
		this.functions = Objects.requireNonNull(functions, "functions");
	}

	/**
	 * Enables or disables all functions which are currently in the function set
	 * defined in {@link #TrainFunctionSet(Set)}.
	 */
	@Override
	public void setSwitched(boolean switched) {
		enabled = switched;
		for (TrainFunction function : functions) {
			if (function != null) {
				function.setSwitched(switched);
			}
		}
	}

	@Override
	public boolean isSwitched() {
		return enabled;
	}

}
