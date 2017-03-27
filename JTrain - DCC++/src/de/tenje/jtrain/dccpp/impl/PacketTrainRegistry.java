package de.tenje.jtrain.dccpp.impl;

import java.io.IOException;
import java.util.Map.Entry;
import java.util.Set;

import de.tenje.jtrain.AbstractOutputRegistry;
import de.tenje.jtrain.OutputRegistry;
import de.tenje.jtrain.Train;
import de.tenje.jtrain.TrainFunction;
import de.tenje.jtrain.dccpp.LocalPacketBroker;
import de.tenje.jtrain.dccpp.Packet;
import de.tenje.jtrain.dccpp.PacketBroker;
import de.tenje.jtrain.dccpp.PacketEngineThrottle;
import de.tenje.jtrain.dccpp.PacketListener;
import de.tenje.jtrain.dccpp.PacketTrainFunction;

/**
 * An {@link OutputRegistry} and {@link PacketListener} for {@link Train}s.
 * Listens (and responds) to the following {@link Packet}s:
 * {@link PacketEngineThrottle}, {@link PacketTrainFunction}.
 * 
 * @author Jonas Tennié
 */
public class PacketTrainRegistry extends AbstractOutputRegistry<Train> implements PacketListener {

	@Override
	public void packetReceived(Packet packet, PacketBroker sender, LocalPacketBroker receiver) throws IOException {
		if (packet instanceof PacketEngineThrottle) {
			Set<Train> trains = getEntries().get(((PacketEngineThrottle) packet).getAddress());
			if (trains != null) {
				for (Train train : trains) {
					train.setDirection(((PacketEngineThrottle) packet).getDirection());
					train.setSpeed(((PacketEngineThrottle) packet).getSpeed(), false);
				}
			}
		}
		else if (packet instanceof PacketTrainFunction) {
			Set<Train> trains = getEntries().get(((PacketTrainFunction) packet).getAddress());
			if (trains != null) {
				TrainFunction function;
				for (Train train : trains) {
					for (Entry<Integer, Boolean> entry : ((PacketTrainFunction) packet).getFunctionValues()
							.entrySet()) {
						function = train.getFunction(entry.getKey());
						if (function != null) {
							function.setSwitched(entry.getValue());
						}
					}
				}
			}
		}
	}

}
