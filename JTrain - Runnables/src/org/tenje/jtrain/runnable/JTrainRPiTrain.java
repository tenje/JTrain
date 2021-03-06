/*******************************************************************************
 * Copyright (c): Jonas Tenni� 2017
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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.tenje.jtrain.LongTrainAddress;
import org.tenje.jtrain.MultipleVolatileSoundFunction;
import org.tenje.jtrain.Order;
import org.tenje.jtrain.PermanentSoundTrainFunction;
import org.tenje.jtrain.Train;
import org.tenje.jtrain.TrainFunction;
import org.tenje.jtrain.TrainFunctionSet;
import org.tenje.jtrain.dccpp.PacketFactory;
import org.tenje.jtrain.dccpp.PacketListener;
import org.tenje.jtrain.dccpp.impl.PacketFactoryImpl;
import org.tenje.jtrain.dccpp.impl.PacketListeningTrainController;
import org.tenje.jtrain.dccpp.server.DccppSocket;
import org.tenje.jtrain.rpi.RPiPinTrainFunction;
import org.tenje.jtrain.rpi.RPiPinTrainFunctionDirectionDepend;
import org.tenje.jtrain.rpi.RPiTrain;
import org.tenje.jtrain.runnable.JTrainXmlReader.XmlReadException;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.RaspiPin;

/**
 * A stand-alone program to create a {@link RPiTrain}.
 * 
 * @author Jonas Tenni�
 */
public class JTrainRPiTrain {

	private static final int DEFAULT_ACCELERATION = 126;
	private static final int DEFAULT_MIN_POWER = 0;
	private static final int DEFAULT_MAX_POWER = 100;

	/**
	 * Starts the program.
	 * 
	 * 
	 * @param args
	 *            The arguments containing the station host address in the first
	 *            (0) index with format <code>address:port</code>.
	 * @throws SecurityException
	 *             Thrown if a security manager exists and if the caller does
	 *             not have LoggingPermission("control").
	 * @throws IOException
	 *             Thrown if an I/O error occurs when opening the output file.
	 */
	public static void main(String[] args) throws SecurityException, IOException {
		Logger logger = new JTrainLogger("Train Decoder", System.out,
				new FileOutputStream("train.log.txt"));
		try {
			start(args, logger);
		}
		catch (XmlReadException ex) {
			logger.log(Level.SEVERE, "Failed to start program, invalid configuration:"
					+ System.getProperty("line.separator") + "\t" + ex.getMessage());
		}
		catch (Throwable t) {
			logger.log(Level.SEVERE, "Failed to start program:", t);
		}
	}

	@SuppressWarnings("resource")
	private static void start(String[] args, Logger logger)
			throws LineUnavailableException, IOException, UnsupportedAudioFileException,
			JDOMException, InterruptedException {
		PacketFactory packetFactory = new PacketFactoryImpl();
		PacketFactoryImpl.regiserDefaultPackets(packetFactory);
		DccppSocket socket;
		String[] addressParts;
		InetAddress address;
		int port;
		if (args.length < 1) {
			throw new IllegalArgumentException("no station address defined");
		}
		addressParts = args[0].split(":");
		if (addressParts.length < 2) {
			throw new IllegalArgumentException("no station port defined");
		}
		address = InetAddress.getByName(addressParts[0]);
		port = Integer.parseInt(addressParts[1]);
		Train train = buildTrain();
		PacketListener trainListener = new PacketListeningTrainController(train);
		logger.info("Connecting to " + address + ":" + port + "...");
		while (true) {
			try {
				socket = new DccppSocket(address, port);
				logger.info("Connected");
			}
			catch (IOException ex) {
				Thread.sleep(1_000);
				logger.log(Level.WARNING, "Connection failed. Retrying...");
				continue; // Retry
			}
			PacketFactoryImpl.regiserDefaultPackets(socket.getPacketFactory());
			socket.addPacketListener(trainListener);
			synchronized (socket) {
				socket.wait(); // Wait until connection lost
			}
			logger.log(Level.WARNING, "Connection lost. Trying to reconnect...");
		}
	}

	private static Train buildTrain() throws LineUnavailableException, IOException,
			UnsupportedAudioFileException, JDOMException {
		SAXBuilder builder = new SAXBuilder();
		Document document = builder.build(new FileInputStream("train.xml"));
		Element root = document.getRootElement();
		int id, addressValue, forwardPin, reversePin, acceleration = DEFAULT_ACCELERATION,
				minPower = DEFAULT_MIN_POWER, maxPower = DEFAULT_MAX_POWER;
		TrainFunction function = null;
		RPiTrain train;

		addressValue = JTrainXmlReader.getInt(root, "address");
		forwardPin = JTrainXmlReader.getInt(root, "forwardPin");
		reversePin = JTrainXmlReader.getInt(root, "reversePin");

		// Parse optional attributes
		if (root.getAttribute("acceleration") != null) {
			acceleration = JTrainXmlReader.getInt(root, "acceleration");
		}
		if (root.getAttribute("minPower") != null) {
			acceleration = JTrainXmlReader.getInt(root, "minPower");
		}
		if (root.getAttribute("maxPower") != null) {
			acceleration = JTrainXmlReader.getInt(root, "maxPower");
		}

		// Create train
		train = new RPiTrain(new LongTrainAddress(addressValue), forwardPin, reversePin,
				acceleration, minPower, maxPower);
		for (Element functionElem : root.getChild("functions").getChildren()) {
			id = JTrainXmlReader.getInt(functionElem, "id");
			switch (functionElem.getName()) {
				case "gpioFunction": {
					function = getPinFunction(functionElem, train);
				}
				break;
				case "volatileSoundFunction": {
					function = getVolatileSoundFunction(functionElem);
				}
				break;
				case "permanentSoundFunction": {
					function = getPermanentSoundFunction(functionElem);
				}
				break;
				case "functionCollection": {
					Set<TrainFunction> subFunctions = new HashSet<>(
							functionElem.getChildren().size());
					for (Element subFunctionElem : functionElem.getChildren()) {
						switch (subFunctionElem.getName()) {
							case "gpioFunction": {
								subFunctions.add(getPinFunction(subFunctionElem, train));
							}
							break;
							case "volatileSoundFunction": {
								subFunctions
										.add(getVolatileSoundFunction(subFunctionElem));
							}
							break;
							case "permanentSoundFunction": {
								subFunctions
										.add(getPermanentSoundFunction(subFunctionElem));
							}
							break;
						}
					}
					function = new TrainFunctionSet(subFunctions);
				}
				break;
			}
			train.setFunction(id, function);
		}
		return train;
	}

	private static TrainFunction getPinFunction(Element functionElem, RPiTrain train) {
		GpioController gpio = GpioFactory.getInstance();
		if (functionElem.getAttribute("pin") != null) {
			return new RPiPinTrainFunction(
					JTrainXmlReader.getOutputPin(functionElem, "pin"));
		}
		else {
			int forwardPin = -1;
			int reversePin = -1;
			if (functionElem.getAttribute("forwardPin") != null) {
				forwardPin = JTrainXmlReader.getInt(functionElem, "forwardPin");
			}
			if (functionElem.getAttribute("reversePin") != null) {
				reversePin = JTrainXmlReader.getInt(functionElem, "reversePin");
			}
			if (forwardPin == -1 && reversePin == -1) {
				throw new XmlReadException("no pin defined in gpioFunction");
			}
			GpioPinDigitalOutput forward = gpio
					.provisionDigitalOutputPin(RaspiPin.getPinByAddress(forwardPin));
			if (forward == null) {
				throw new XmlReadException("pin does not exist: " + forwardPin);
			}
			GpioPinDigitalOutput reverse = gpio
					.provisionDigitalOutputPin(RaspiPin.getPinByAddress(reversePin));
			if (reverse == null) {
				throw new XmlReadException("pin does not exist: " + reversePin);
			}
			return new RPiPinTrainFunctionDirectionDepend(forward, reverse, train);
		}
	}

	private static TrainFunction getVolatileSoundFunction(Element functionElem)
			throws LineUnavailableException, IOException, UnsupportedAudioFileException {
		List<Element> soundElems = functionElem.getChildren("sound");
		List<Clip> clips = new ArrayList<>(soundElems.size());
		Attribute attribute = functionElem.getAttribute("order");
		Order order;
		if (attribute != null && soundElems.size() > 1) {
			order = Order.valueOf(attribute.getValue().toUpperCase());
		}
		else { // Not defined or only one element
			order = Order.RANDOM;
		}
		for (Element soundElem : soundElems) {
			Line.Info linfo = new Line.Info(Clip.class);
			Line line = AudioSystem.getLine(linfo);
			Clip clip = (Clip) line;
			clip.open(AudioSystem
					.getAudioInputStream(new File(soundElem.getTextNormalize())));
			clips.add(clip);
		}
		return new MultipleVolatileSoundFunction(clips, order);
	}

	private static TrainFunction getPermanentSoundFunction(Element functionElem)
			throws LineUnavailableException, IOException, UnsupportedAudioFileException {
		Element child;
		Clip enableClip, loopClip, disableClip;
		if ((child = functionElem.getChild("enableSound")) != null) {
			Line.Info linfo = new Line.Info(Clip.class);
			Line line = AudioSystem.getLine(linfo);
			enableClip = (Clip) line;
			enableClip.open(
					AudioSystem.getAudioInputStream(new File(child.getTextNormalize())));
		}
		else {
			enableClip = null;
		}
		if ((child = functionElem.getChild("loopSound")) != null) {
			Line.Info linfo = new Line.Info(Clip.class);
			Line line = AudioSystem.getLine(linfo);
			loopClip = (Clip) line;
			loopClip.open(
					AudioSystem.getAudioInputStream(new File(child.getTextNormalize())));
		}
		else {
			throw new XmlReadException("no loop sound defined: " + functionElem);
		}
		if ((child = functionElem.getChild("enableSound")) != null) {
			Line.Info linfo = new Line.Info(Clip.class);
			Line line = AudioSystem.getLine(linfo);
			disableClip = (Clip) line;
			disableClip.open(
					AudioSystem.getAudioInputStream(new File(child.getTextNormalize())));
		}
		else {
			disableClip = null;
		}
		return new PermanentSoundTrainFunction(enableClip, loopClip, disableClip);
	}

}
