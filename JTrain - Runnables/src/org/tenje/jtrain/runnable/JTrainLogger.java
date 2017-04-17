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
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.Objects;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

/**
 * A {@link Logger} used for the JTrain runnables. Two default {@link Handler}s
 * are registered: A {@link FileHandler} to write the log to a file
 * <code>&lt;name&gt;.log.txt</code> and a handler to log to the console.
 * 
 * @author Jonas Tennié
 */
public class JTrainLogger extends Logger {

	private static final Formatter FORMATTER = new Formatter();

	/**
	 * Constructs a new {@link JTrainLogger} with the specified name.
	 * 
	 * @param name
	 *            The name of the logger.
	 * @param logStreams
	 *            An array containing all streams to write the log to.
	 * @throws SecurityException
	 *             Thrown if a security manager exists and if the caller does
	 *             not have LoggingPermission("control").
	 * @throws IOException
	 *             Thrown if an I/O error occurs when opening the output file.
	 * @throws NullPointerException
	 *             Thrown if <code>logStreams</code> is <code>null</code> or any
	 *             element in <code>logStreams</code> is <code>null</code>.
	 */
	public JTrainLogger(String name, OutputStream... logStreams)
			throws SecurityException, IOException {
		super(name, null);
		Objects.requireNonNull(logStreams, "logStreams");
		for (OutputStream out : logStreams) {
			addLoggerStream(out);
		}
	}

	/**
	 * Adds an {@link OutputStream} as output for this logger.
	 * 
	 * @param out
	 *            The output stream to add.
	 * @throws NullPointerException
	 *             Thrown if <code>out</code> is <code>null</code>.
	 */
	public void addLoggerStream(OutputStream out) {
		Objects.requireNonNull(out, "out");
		addHandler(new StreamHandler(out, FORMATTER));
	}

	private static class Formatter extends java.util.logging.Formatter {

		// Static as only one Formatter instance exists
		private final String FORMAT = "[%1$tb %1$te %1$tY, %1$tH:%1$tM:%1$tS, %2$s]: %3$s %4$s\r\n";
		private final Date DATE = new Date();

		@Override
		public String format(LogRecord record) {
			DATE.setTime(record.getMillis());
			String message = formatMessage(record);
			String throwable = "";
			if (record.getThrown() != null) {
				StringWriter sw = new StringWriter();
				PrintWriter pw = new PrintWriter(sw);
				pw.println();
				record.getThrown().printStackTrace(pw);
				pw.close();
				throwable = sw.toString();
			}
			return String.format(FORMAT, DATE, record.getLevel().getLocalizedName(),
					message, throwable);
		}

	}

	private static class StreamHandler extends java.util.logging.StreamHandler {

		public StreamHandler(OutputStream out, Formatter formatter) {
			super(out, formatter);
		}

		@Override
		public synchronized void publish(LogRecord record) {
			super.publish(record);
			flush();
		}

	}

}
