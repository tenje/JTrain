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
package org.tenje.jtrain.runnable;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.tenje.jtrain.dccpp.server.DccppStation;

/**
 * A stand-alone program to create a {@link DccppStation}.
 * 
 * @author Jonas Tennié
 */
public class JTrainDccppStation {

	/**
	 * Starts the program. Constructs a new {@link DccppStation} with
	 * {@code args[0]} as controller port and {@code args[1]} as accessory port.
	 * 
	 * @param args
	 *            The arguments containing the two port numbers.
	 * @throws SecurityException
	 *             Thrown if a security manager exists and if the caller does
	 *             not have LoggingPermission("control").
	 * @throws IOException
	 *             Thrown if an I/O error occurs when opening the output file.
	 */
	public static void main(String[] args) throws SecurityException, IOException {
		Logger logger = new JTrainLogger("DCC++ Station", System.out,
				new FileOutputStream("dccpp_station.log.txt"));
		try {
			start(args, logger);
		}
		catch (Throwable t) {
			logger.log(Level.SEVERE, "Failed to start program:", t);
		}
	}

	@SuppressWarnings("resource")
	private static void start(String[] args, Logger logger) throws IOException {
		if (args.length < 2) {
			throw new IllegalArgumentException("no ports defined");
		}
		int controllerPort = Integer.parseInt(args[0]);
		int accessoryPort = Integer.parseInt(args[1]);
		logger.info("Starting station on controller port " + args[0]
				+ " and accessory port " + args[1] + "...");
		new DccppStation(controllerPort, accessoryPort);
		logger.info("Started");
	}

}
