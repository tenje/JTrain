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
 * @author Jonas Tenni�
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