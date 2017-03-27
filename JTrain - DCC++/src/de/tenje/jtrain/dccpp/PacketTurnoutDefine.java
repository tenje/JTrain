package de.tenje.jtrain.dccpp;

import de.tenje.jtrain.AccessoryDecoderAddress;
import de.tenje.jtrain.Addressable;

/**
 * A {@link PacketTurnout} to define a turnout ID with an address and sub
 * address.
 * 
 * @author Jonas Tennié
 */
public interface PacketTurnoutDefine extends Addressable, RegistrationIdHolder, PacketTurnout {

	@Override
	AccessoryDecoderAddress getAddress();

}
