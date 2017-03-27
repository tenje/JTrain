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
package de.tenje.jtrain.runnable;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.MissingFormatArgumentException;
import java.util.Set;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
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

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.RaspiPin;

import de.tenje.jtrain.dccpp.PacketFactory;
import de.tenje.jtrain.dccpp.PacketListener;
import de.tenje.jtrain.dccpp.impl.PacketFactoryImpl;
import de.tenje.jtrain.dccpp.impl.PacketListeningTrainController;
import de.tenje.jtrain.dccpp.server.DccppSocket;
import de.tenje.jtrain.rpi.RPiPinTrainFunction;
import de.tenje.jtrain.rpi.RPiPinTrainFunctionDirectionDepend;
import de.tenje.jtrain.rpi.RPiTrain;

/**
 * A stand-alone program to create a {@link RPiTrain}.
 * 
 * @author Jonas Tennié
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
	private static void start(String[] args) throws LineUnavailableException, IOException,
			UnsupportedAudioFileException, JDOMException, InterruptedException {
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
		System.out.println("Connecting to " + address + ":" + port + "...");
		while (true) {
			try {
				socket = new DccppSocket(address, port);
				System.out.println("Connected");
			}
			catch (IOException ex) {
				Thread.sleep(1_000);
				System.err.println("Connection failed. Retrying...");
				continue; // Retry
			}
			PacketFactoryImpl.regiserDefaultPackets(socket.getPacketFactory());
			socket.addPacketListener(trainListener);
			synchronized (socket) {
				socket.wait(); // Wait until connection lost
			}
			System.err.println("Connection lost. Trying to reconnect...");
		}
	}

	private static Train buildTrain() throws LineUnavailableException, IOException,
			UnsupportedAudioFileException, JDOMException {
		SAXBuilder builder = new SAXBuilder();
		Document document = builder.build(new FileInputStream("train.xml"));
		Element root = document.getRootElement();
		Attribute attribute;
		int id, addressValue, forwardPin, reversePin, acceleration = DEFAULT_ACCELERATION,
				minPower = DEFAULT_MIN_POWER, maxPower = DEFAULT_MAX_POWER;
		TrainFunction function = null;
		RPiTrain train;

		// Check and parse required attributes
		if ((attribute = root.getAttribute("address")) == null) {
			throw new MissingFormatArgumentException("train address missing");
		}
		else {
			addressValue = Integer.parseInt(attribute.getValue());
		}
		if ((attribute = root.getAttribute("forwardPin")) == null) {
			throw new MissingFormatArgumentException("train forwardPin missing");
		}
		else {
			forwardPin = Integer.parseInt(attribute.getValue());
		}
		if ((attribute = root.getAttribute("reversePin")) == null) {
			throw new MissingFormatArgumentException("train reversePin missing");
		}
		else {
			reversePin = Integer.parseInt(attribute.getValue());
		}

		// Parse optional attributes
		if ((attribute = root.getAttribute("acceleration")) != null) {
			acceleration = Integer.parseInt(attribute.getValue());
		}
		if ((attribute = root.getAttribute("minPower")) != null) {
			minPower = Integer.parseInt(attribute.getValue());
		}
		if ((attribute = root.getAttribute("maxPower")) != null) {
			maxPower = Integer.parseInt(attribute.getValue());
		}

		// Create train
		train = new RPiTrain(new LongTrainAddress(addressValue), forwardPin, reversePin,
				acceleration, minPower, maxPower);

		for (Element functionElem : root.getChild("functions").getChildren()) {
			if ((attribute = functionElem.getAttribute("id")) != null) {
				id = Integer.parseInt(attribute.getValue());
			}
			else {
				throw new MissingFormatArgumentException(
						"function id missing: " + functionElem);
			}
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
		Attribute attribute;
		GpioController gpio = GpioFactory.getInstance();
		GpioPinDigitalOutput pin;
		if ((attribute = functionElem.getAttribute("pin")) != null) {
			pin = gpio.provisionDigitalOutputPin(
					RaspiPin.getPinByAddress(Integer.parseInt(attribute.getValue())));
			if (pin != null) {
				return new RPiPinTrainFunction(pin);
			}
			else {
				throw new IllegalArgumentException(
						"pin does not exist: " + attribute.getValue());
			}
		}
		else {
			int forwardPin = -1;
			int reversePin = -1;
			if ((attribute = functionElem.getAttribute("forwardPin")) != null) {
				forwardPin = Integer.parseInt(attribute.getValue());
			}
			if ((attribute = functionElem.getAttribute("reversePin")) != null) {
				reversePin = Integer.parseInt(attribute.getValue());
			}
			if (forwardPin == -1 && reversePin == -1) {
				throw new MissingFormatArgumentException(
						"no pin defined in gpioFunction");
			}
			GpioPinDigitalOutput forward = gpio
					.provisionDigitalOutputPin(RaspiPin.getPinByAddress(forwardPin));
			if (forward == null) {
				throw new IllegalArgumentException("pin does not exist: " + forwardPin);
			}
			GpioPinDigitalOutput reverse = gpio
					.provisionDigitalOutputPin(RaspiPin.getPinByAddress(reversePin));
			if (reverse == null) {
				throw new IllegalArgumentException("pin does not exist: " + reversePin);
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
			Clip clip = AudioSystem.getClip();
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
			enableClip = AudioSystem.getClip();
			enableClip.open(
					AudioSystem.getAudioInputStream(new File(child.getTextNormalize())));
		}
		else {
			enableClip = null;
		}
		if ((child = functionElem.getChild("loopSound")) != null) {
			loopClip = AudioSystem.getClip();
			loopClip.open(
					AudioSystem.getAudioInputStream(new File(child.getTextNormalize())));
		}
		else {
			throw new MissingFormatArgumentException(
					"no loop sound defined: " + functionElem);
		}
		if ((child = functionElem.getChild("enableSound")) != null) {
			disableClip = AudioSystem.getClip();
			disableClip.open(
					AudioSystem.getAudioInputStream(new File(child.getTextNormalize())));
		}
		else {
			disableClip = null;
		}
		return new PermanentSoundTrainFunction(enableClip, loopClip, disableClip);
	}

}
