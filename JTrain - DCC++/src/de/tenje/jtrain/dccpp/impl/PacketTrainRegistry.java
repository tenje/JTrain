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
package de.tenje.jtrain.dccpp.impl;

import java.io.IOException;
import java.util.Map.Entry;

import org.tenje.jtrain.AbstractOutputRegistry;
import org.tenje.jtrain.OutputRegistry;
import org.tenje.jtrain.Train;
import org.tenje.jtrain.TrainFunction;

import java.util.Set;

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
