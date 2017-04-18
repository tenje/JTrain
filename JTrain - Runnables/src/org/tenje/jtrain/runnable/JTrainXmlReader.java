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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.jdom2.Attribute;
import org.jdom2.Element;
import org.tenje.jtrain.MultipleVolatileSoundFunction;
import org.tenje.jtrain.Order;
import org.tenje.jtrain.PermanentSoundTrainFunction;
import org.tenje.jtrain.SwitchableScheduler;
import org.tenje.jtrain.SwitchableSchedulerBuilder;
import org.tenje.jtrain.TrainFunction;
import org.tenje.jtrain.rpi.RPiOutputPin;
import org.tenje.jtrain.rpi.RPiPinTrainFunction;
import org.tenje.jtrain.rpi.RPiPinTrainFunctionDirectionDepend;
import org.tenje.jtrain.rpi.RPiTrain;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.RaspiPin;

class JTrainXmlReader {

	static GpioPinDigitalInput getInputPin(Element elem, String name) {
		GpioController gpio = GpioFactory.getInstance();
		Pin pin = RaspiPin.getPinByAddress(getInt(elem, name));
		if (pin != null) {
			return gpio.provisionDigitalInputPin(pin, PinPullResistance.PULL_UP);
		}
		else {
			throw new XmlReadException(
					"pin does not exist: " + elem.getAttributeValue(name));
		}
	}

	static GpioPinDigitalOutput getOutputPin(Element elem, String name) {
		GpioController gpio = GpioFactory.getInstance();
		Pin pin = RaspiPin.getPinByAddress(getInt(elem, name));
		if (pin != null) {
			return gpio.provisionDigitalOutputPin(pin);
		}
		else {
			throw new XmlReadException(
					"pin does not exist: " + elem.getAttributeValue(name));
		}
	}

	static SwitchableScheduler getScheduler(Element elem) {
		SwitchableSchedulerBuilder builder = new SwitchableSchedulerBuilder();
		Attribute attribute;
		boolean loop = true;
		if ((attribute = elem.getAttribute("loop")) != null) {
			String attributeValue = attribute.getValue().toLowerCase();
			if (attributeValue.equals("false")) {
				loop = false;
			}
			else if (!attributeValue.equals("true")) {
				throw new XmlReadException("state argument: " + attribute.getValue());
			}
		}
		for (Element child : elem.getChildren()) {
			switch (child.getName()) {
				case "pinState": {
					GpioPinDigitalOutput pin = getOutputPin(child, "pin");
					boolean state = true;
					attribute = child.getAttribute("state");
					if (attribute == null) {
						throw new XmlReadException("no state defined: " + child);
					}
					String attributeValue = attribute.getValue().toLowerCase();
					if (attributeValue.equals("low")) {
						state = false;
					}
					else if (!attributeValue.equals("low")) {
						throw new XmlReadException(
								"illegal state argument: " + attribute.getValue());
					}
					builder.setState(new RPiOutputPin(pin), state);
				}
				break;
				case "sleep": {
					long time = getLong(child, "time");
					builder.sleep(time);
				}
				break;
			}
		}
		return builder.build(loop);
	}

	static TrainFunction getPinFunction(Element elem, RPiTrain train) {
		Attribute attribute;
		GpioController gpio = GpioFactory.getInstance();
		GpioPinDigitalOutput pin;
		if ((attribute = elem.getAttribute("pin")) != null) {
			pin = gpio.provisionDigitalOutputPin(
					RaspiPin.getPinByAddress(getInt(elem, "pin")));
			if (pin != null) {
				return new RPiPinTrainFunction(pin);
			}
			else {
				throw new XmlReadException("pin does not exist: " + attribute.getValue());
			}
		}
		else {
			int forwardPin = -1;
			int reversePin = -1;
			if ((attribute = elem.getAttribute("forwardPin")) != null) {
				forwardPin = Integer.parseInt(attribute.getValue());
			}
			if ((attribute = elem.getAttribute("reversePin")) != null) {
				reversePin = Integer.parseInt(attribute.getValue());
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

	static TrainFunction getVolatileSoundFunction(Element functionElem)
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

	static TrainFunction getPermanentSoundFunction(Element functionElem)
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

	static int getInt(Element elem, String name) {
		Attribute attribute = elem.getAttribute(name);
		if (attribute != null) {
			try {
				return Integer.parseInt(attribute.getValue());
			}
			catch (NumberFormatException ex) {
				throw new XmlReadException(name + " attribute of " + elem
						+ " is not a valid integer: " + attribute.getValue());
			}
		}
		throw new XmlReadException(name + " attribute missing for " + elem);
	}

	static long getLong(Element elem, String name) {
		Attribute attribute = elem.getAttribute(name);
		if (attribute != null) {
			try {
				return Long.parseLong(attribute.getValue());
			}
			catch (NumberFormatException ex) {
				throw new XmlReadException(name + " attribute of " + elem
						+ " is not a valid integer: " + attribute.getValue());
			}
		}
		throw new XmlReadException(name + " attribute missing for " + elem);
	}

	@SuppressWarnings("serial") // Never serialized
	static class XmlReadException extends RuntimeException {

		XmlReadException(String message) {
			super(message);
		}

	}

}
