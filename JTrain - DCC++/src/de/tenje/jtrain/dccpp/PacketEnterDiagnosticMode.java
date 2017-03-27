package de.tenje.jtrain.dccpp;

/**
 * {@link Packet} to enter diagnostic mode
 * 
 * @author Jonas Tennié
 */
public interface PacketEnterDiagnosticMode extends Packet {

	/**
	 * The type char of the <code>PackageEnterDiagnosticMode</code>.
	 */
	public static final char TYPE_CHAR = 'D';

}
