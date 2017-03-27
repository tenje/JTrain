package de.tenje.jtrain;

/**
 * The {@link Address} of an analog output pin (e.g. Arduino or Raspberry Pi
 * output pins). An analog output pin has one of two states: High or low. Some
 * pins may be forbidden to use. If the address is passed as parameter, but the
 * address value is invalid, then the address will be ignored. No
 * {@link Exception} will be thrown.
 * 
 * @author Jonas Tennié
 */
public class OutputPinAddress implements Address {

	private final int address;

	/**
	 * Constructs a new {@link OutputPinAddress} with the specified address
	 * value.
	 * 
	 * @param address
	 *            The address value.
	 */
	public OutputPinAddress(int address) {
		if (address < 0 || address > 127) {
			throw new IllegalArgumentException("addres value out of valid range: " + address);
		}
		this.address = address;
	}

	@Override
	public int hashCode() {
		return address;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof OutputPinAddress)) {
			return false;
		}
		OutputPinAddress other = (OutputPinAddress) obj;
		if (address != other.address) {
			return false;
		}
		return true;
	}

	@Override
	public int getAddress() {
		return address;
	}

	@Override
	public int getMainAddress() {
		return getAddress();
	}

	@Override
	public int getSubAddress() {
		return 0;
	}
}
