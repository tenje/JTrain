package de.tenje.jtrain;

import java.util.Objects;

/**
 * This class provides a skeletal implementation of the {@link Train} interface
 * to minimize the effort required to implement this interface.
 * 
 * @author Jonas Tennié
 */
public abstract class AbstractTrain implements Train {

	private final LongTrainAddress address;
	private final TrainFunction[] functions = new TrainFunction[29];

	/**
	 * Constructs a new {@link AbstractTrain} with the specified
	 * {@link LongTrainAddress}.
	 * 
	 * @param address
	 *            The train's address.
	 * @throws NullPointerException
	 *             Thrown if <code>address</code> is <code>null</code>.
	 */
	public AbstractTrain(LongTrainAddress address) {
		this.address = Objects.requireNonNull(address, "address");
	}

	@Override
	public LongTrainAddress getAddress() {
		return address;
	}

	@Override
	public TrainFunction getFunction(int id) {
		return functions[id];
	}

	@Override
	public void setFunction(int id, TrainFunction function) {
		functions[id] = function;
	}

}
