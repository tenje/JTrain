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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.MissingFormatArgumentException;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.tenje.jtrain.AccessoryDecoderAddress;
import org.tenje.jtrain.Sensor;
import org.tenje.jtrain.Signal;
import org.tenje.jtrain.SignalAspect;
import org.tenje.jtrain.SignalAspectControlTurnout;
import org.tenje.jtrain.Turnout;
import org.tenje.jtrain.dccpp.impl.PacketFactoryImpl;
import org.tenje.jtrain.dccpp.impl.PacketSensorRegistry;
import org.tenje.jtrain.dccpp.impl.PacketTurnoutRegistry;
import org.tenje.jtrain.dccpp.server.DccppSocket;
import org.tenje.jtrain.rpi.RPiSensor;
import org.tenje.jtrain.rpi.RPiSignal;
import org.tenje.jtrain.rpi.RPiTurnout;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.RaspiPin;

/**
 * A stand-alone program to control some accessories.
 * 
 * @author Jonas Tennié
 */
public class JTrainAccessories {

	/**
	 * Starts the program.
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

	private static void start(String[] args) throws FileNotFoundException, JDOMException,
			IOException, InterruptedException {
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
		// -

		PacketSensorRegistry sensorRegistry = null;
		PacketTurnoutRegistry turnoutRegistry = new PacketTurnoutRegistry();

		SAXBuilder builder = new SAXBuilder();
		Document document = builder.build(new FileInputStream("accessories.xml"));
		Attribute attribute;
		AccessoryDecoderAddress accessoryAddress;
		List<Sensor> sensors = new ArrayList<>();
		for (Element accessoryElem : document.getRootElement().getChildren()) {
			if (accessoryElem.getName().equals("lightSignal")) {
				Map<SignalAspect, GpioPinDigitalOutput> pins = new EnumMap<>(
						SignalAspect.class);
				Map<AccessoryDecoderAddress, SignalAspect> addresses = new HashMap<>();
				Signal signal;
				SignalAspect aspect;
				AccessoryDecoderAddress aspectAddress = null;
				GpioPinDigitalOutput pin;
				for (Element aspectElem : accessoryElem.getChildren()) {
					if ((attribute = aspectElem.getAttribute("color")) != null) {
						aspect = SignalAspect.valueOf(attribute.getValue().toUpperCase());
					}
					else {
						throw new MissingFormatArgumentException(
								"no color defined: " + aspectElem);
					}
					if ((attribute = aspectElem.getAttribute("address")) != null) {
						aspectAddress = new AccessoryDecoderAddress(
								Integer.parseInt(attribute.getValue()), 0);
					}
					else {
						throw new MissingFormatArgumentException(
								"no address defined: " + accessoryElem);
					}
					pin = getOutputPin(aspectElem, "pin");
					if (pins.put(aspect, pin) != null) {
						throw new IllegalArgumentException(
								"color already defined: " + aspect.name().toLowerCase());
					}
					if (addresses.put(aspectAddress, aspect) != null) {
						throw new IllegalArgumentException("address already defined: "
								+ aspectAddress.getMainAddress());
					}
				}
				if (!pins.isEmpty()) {
					signal = new RPiSignal(aspectAddress, pins);
					for (Entry<AccessoryDecoderAddress, SignalAspect> entry : addresses
							.entrySet()) {
						turnoutRegistry.register(new SignalAspectControlTurnout(
								entry.getKey(), signal, entry.getValue()));
					}
				}
			}
			else {
				if ((attribute = accessoryElem.getAttribute("address")) != null) {
					accessoryAddress = new AccessoryDecoderAddress(
							Integer.parseInt(attribute.getValue()), 0);
					switch (accessoryElem.getName()) {
						case "sensor": {
							Sensor sensor = new RPiSensor(accessoryAddress,
									getInputPin(accessoryElem, "pin"));
							sensors.add(sensor);
						}
						break;
						case "turnout": {
							int switchTime = 0;
							if ((attribute = accessoryElem
									.getAttribute("switchTime")) != null) {
								switchTime = Integer.parseInt(attribute.getValue());
							}
							Turnout turnout = new RPiTurnout(accessoryAddress,
									getOutputPin(accessoryElem, "straightPin"),
									getOutputPin(accessoryElem, "thrownPin"), switchTime);
							turnoutRegistry.register(turnout);
						}
						break;
					}
				}
				else {
					throw new MissingFormatArgumentException(
							"no address defined: " + accessoryElem);
				}
			}
		}

		// --
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
			if (sensorRegistry == null) {
				sensorRegistry = new PacketSensorRegistry(socket,
						socket.getConnectedBroker());
				for (Sensor sensor : sensors) {
					sensorRegistry.register(sensor);
				}
				sensors = null;
			}
			else {
				sensorRegistry.setReceiver(socket.getConnectedBroker());
			}
			socket.addPacketListener(sensorRegistry);
			socket.addPacketListener(turnoutRegistry);
			synchronized (socket) {
				socket.wait(); // Wait until connection lost
			}
			System.err.println("Connection lost. Trying to reconnect...");
		}
	}

	private static GpioPinDigitalInput getInputPin(Element elem, String name) {
		GpioController gpio = GpioFactory.getInstance();
		Attribute attribute;
		if ((attribute = elem.getAttribute(name)) != null) {
			Pin pin = RaspiPin.getPinByAddress(Integer.parseInt(attribute.getValue()));
			if (pin != null) {
				return gpio.provisionDigitalInputPin(pin, PinPullResistance.PULL_UP);
			}
			else {
				throw new IllegalArgumentException(
						"pin does not exist: " + attribute.getValue());
			}
		}
		else {
			throw new MissingFormatArgumentException(
					"pin attribute missing for " + elem + ": " + name);
		}
	}

	private static GpioPinDigitalOutput getOutputPin(Element elem, String name) {
		GpioController gpio = GpioFactory.getInstance();
		Attribute attribute;
		if ((attribute = elem.getAttribute(name)) != null) {
			Pin pin = RaspiPin.getPinByAddress(Integer.parseInt(attribute.getValue()));
			if (pin != null) {
				return gpio.provisionDigitalOutputPin(pin);
			}
			else {
				throw new IllegalArgumentException(
						"pin does not exist: " + attribute.getValue());
			}
		}
		else {
			throw new MissingFormatArgumentException(
					"pin attribute missing for " + elem + ": " + name);
		}
	}

}
