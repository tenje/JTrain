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

import java.io.IOException;

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
	 */
	public static void main(String[] args) {
		try {
			start(args);
		}
		catch (Throwable t) {
			System.err.print("Failed to start program: ");
			if (t.getMessage() != null) {
				System.err.println(t.getMessage());
			}
			else {
				System.err.println(t.getClass().getName());
			}
			System.err.println();
			System.err.println("Developer info:");
			t.printStackTrace();
		}
	}

	@SuppressWarnings("resource")
	private static void start(String[] args) throws IOException {
		if (args.length < 2) {
			throw new IllegalArgumentException("no ports defined");
		}
		int controllerPort = Integer.parseInt(args[0]);
		int accessoryPort = Integer.parseInt(args[1]);
		System.out.println("Starting station on controller port " + args[0]
				+ " and accessory port " + args[1] + "...");
		new DccppStation(controllerPort, accessoryPort);
		System.out.println("Started");
	}

}
