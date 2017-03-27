package de.tenje.jtrain;

import java.util.Objects;

/**
 * A {@link Turnout} which controls a single {@link Signal}'s
 * {@link SignalAspect}. Switch state set by {@link #setSwitched(boolean)} is
 * ignored. The signal's state is set if <code>switched</code> value is
 * <code>true</code> or <code>false</code>.
 * 
 * @author Jonas Tennié
 */
public class SignalAspectControlTurnout extends AbstractSwitchable implements Turnout {

	private final AccessoryDecoderAddress address;
	private final Signal signal;
	private final SignalAspect aspect;

	/**
	 * Constructs a new {@link SignalAspectControlTurnout} with the specified
	 * address for the specified signal and signal aspect.
	 * 
	 * @param address
	 *            The address.
	 * @param signal
	 *            The signal to handle.
	 * @param aspect
	 *            The aspect to set to the signal if switched.
	 */
	public SignalAspectControlTurnout(AccessoryDecoderAddress address, Signal signal,
			SignalAspect aspect) {
		this.address = Objects.requireNonNull(address, "address");
		this.signal = signal;
		this.aspect = aspect;
	}

	@Override
	public AccessoryDecoderAddress getAddress() {
		return address;
	}

	/**
	 * Sets the {@link SignalAspect} of the handled {@link Signal} to the
	 * handled aspect.
	 */
	@Override
	public void toggle() {
		signal.setAspect(aspect);
	}

	/**
	 * Equivalent to {@link #toggle()}.
	 */
	@Override
	public void setSwitched(boolean switched) {
		signal.setAspect(aspect);
	}

	/**
	 * Returns whether the current signal aspect is the same aspect as the
	 * aspect controlled by this turnout.
	 * 
	 * @return {@code true} if the handled signal's state is the same as the
	 *         handled signal aspect, else {@code false}.
	 */
	@Override
	public boolean isSwitched() {
		return signal.getAspect() == aspect;
	}

}
