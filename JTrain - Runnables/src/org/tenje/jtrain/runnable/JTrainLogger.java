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
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.logging.StreamHandler;

/**
 * A {@link Logger} used for the JTrain runnables. Two default {@link Handler}s
 * are registered: A {@link FileHandler} to write the log to a file
 * <code>&lt;name&gt;.log.txt</code> and a handler to log to the console.
 * 
 * @author Jonas Tennié
 */
public class JTrainLogger extends Logger {

	/**
	 * Constructs a new {@link JTrainLogger} with the specified name.
	 * 
	 * @param name
	 *            The name of the logger.
	 * @throws SecurityException
	 *             Thrown if a security manager exists and if the caller does
	 *             not have LoggingPermission("control").
	 * @throws IOException
	 *             Thrown if an I/O error occurs when opening the output file.
	 */
	public JTrainLogger(String name) throws SecurityException, IOException {
		super(name, null);
		Formatter formatter = new SimpleFormatter();
		Handler handler = new FileHandler(name + ".log.txt");
		handler.setFormatter(formatter);
		addHandler(handler);
		handler = new StreamHandler(System.out, formatter);
		addHandler(handler);
	}

}
