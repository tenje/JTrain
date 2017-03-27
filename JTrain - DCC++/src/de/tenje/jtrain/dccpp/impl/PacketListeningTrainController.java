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
import java.util.Objects;

import de.tenje.jtrain.Address;
import de.tenje.jtrain.Addressable;
import de.tenje.jtrain.Train;
import de.tenje.jtrain.TrainFunction;
import de.tenje.jtrain.dccpp.LocalPacketBroker;
import de.tenje.jtrain.dccpp.Packet;
import de.tenje.jtrain.dccpp.PacketBroker;
import de.tenje.jtrain.dccpp.PacketEngineThrottle;
import de.tenje.jtrain.dccpp.PacketListener;
import de.tenje.jtrain.dccpp.PacketTrainFunction;

/**
 * A {@link PacketListener} listening to a specific {@link Train} address and
 * controlling the specified {@link Train} with this address (listens to:
 * {@link PacketEngineThrottle} and {@link PacketTrainFunction}).
 * 
 * @author Jonas Tennié
 */
public class PacketListeningTrainController implements PacketListener {

	private final Train train;

	/**
	 * Constructs a new {@link PacketListeningTrainController} for the specified
	 * {@link Train}. Listens to the {@link Address} returned by
	 * {@link Train#getAddress()}.
	 * 
	 * @param train
	 *            The train to control.
	 * @throws NullPointerException
	 *             Thrown if <code>train</code> is <code>null</code>.
	 */
	public PacketListeningTrainController(Train train) {
		this.train = Objects.requireNonNull(train, "train");
	}

	@Override
	public void packetReceived(Packet packet, PacketBroker sender,
			LocalPacketBroker receiver) throws IOException {
		if (packet instanceof Addressable) {
			if (((Addressable) packet).getAddress().equals(train.getAddress())) {
				if (packet instanceof PacketEngineThrottle) {
					train.setDirection(((PacketEngineThrottle) packet).getDirection());
					train.setSpeed(((PacketEngineThrottle) packet).getSpeed(), false);
				}
				else if (packet instanceof PacketTrainFunction) {
					TrainFunction function;
					for (Entry<Integer, Boolean> entry : ((PacketTrainFunction) packet)
							.getFunctionValues().entrySet()) {
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
