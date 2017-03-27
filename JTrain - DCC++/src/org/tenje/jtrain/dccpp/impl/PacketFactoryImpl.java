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
package org.tenje.jtrain.dccpp.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.tenje.jtrain.dccpp.Packet;
import org.tenje.jtrain.dccpp.PacketAccessoryOperate;
import org.tenje.jtrain.dccpp.PacketBuilder;
import org.tenje.jtrain.dccpp.PacketClearSettings;
import org.tenje.jtrain.dccpp.PacketEngineThrottle;
import org.tenje.jtrain.dccpp.PacketFactory;
import org.tenje.jtrain.dccpp.PacketListBitContents;
import org.tenje.jtrain.dccpp.PacketOperationFailed;
import org.tenje.jtrain.dccpp.PacketOperationSuccessful;
import org.tenje.jtrain.dccpp.PacketOutputPin;
import org.tenje.jtrain.dccpp.PacketOutputPinDefine;
import org.tenje.jtrain.dccpp.PacketOutputPinDelete;
import org.tenje.jtrain.dccpp.PacketOutputPinList;
import org.tenje.jtrain.dccpp.PacketOutputPinSetState;
import org.tenje.jtrain.dccpp.PacketReadConfigurationByte;
import org.tenje.jtrain.dccpp.PacketReadCurrent;
import org.tenje.jtrain.dccpp.PacketReadStationState;
import org.tenje.jtrain.dccpp.PacketSensor;
import org.tenje.jtrain.dccpp.PacketSensorDefine;
import org.tenje.jtrain.dccpp.PacketSensorDelete;
import org.tenje.jtrain.dccpp.PacketSensorList;
import org.tenje.jtrain.dccpp.PacketSensorState;
import org.tenje.jtrain.dccpp.PacketSensorStateActive;
import org.tenje.jtrain.dccpp.PacketSensorStateInactive;
import org.tenje.jtrain.dccpp.PacketSensorStateList;
import org.tenje.jtrain.dccpp.PacketStationInfo;
import org.tenje.jtrain.dccpp.PacketStoreSettings;
import org.tenje.jtrain.dccpp.PacketTrackPowerOff;
import org.tenje.jtrain.dccpp.PacketTrackPowerOn;
import org.tenje.jtrain.dccpp.PacketTrackPowerState;
import org.tenje.jtrain.dccpp.PacketTrainFunction;
import org.tenje.jtrain.dccpp.PacketTurnout;
import org.tenje.jtrain.dccpp.PacketTurnoutDefine;
import org.tenje.jtrain.dccpp.PacketTurnoutDelete;
import org.tenje.jtrain.dccpp.PacketTurnoutList;
import org.tenje.jtrain.dccpp.PacketTurnoutState;
import org.tenje.jtrain.dccpp.PacketTurnoutThrow;

/**
 * This class is a concrete implementation of the {@link PacketFactory}
 * interface.
 * 
 * @author Jonas Tennié
 * @see PacketBuilder
 */
public class PacketFactoryImpl implements PacketFactory {

	// Used by #regiserDefaultPackets()
	private static final List<PacketBuilder<? extends Packet>> DEFAULT_PACKET_BUILDERS = new ArrayList<>();
	private static final List<Class<? extends Packet>> DEFAULT_PACKET_BUILDER_CLASSES = new ArrayList<>();
	private static final List<Character> DEFAULT_PACKET_BUILDER_CHARS = new ArrayList<>();

	// Initialize default packet builders
	static {
		addDefaultBuilder(PacketAccessoryOperateImpl.BUILDER,
				PacketAccessoryOperate.class, PacketAccessoryOperate.TYPE_CHAR);
		addDefaultBuilder(PacketClearSettingsImpl.BUILDER, PacketClearSettings.class,
				PacketClearSettings.TYPE_CHAR);
		addDefaultBuilder(PacketEngineThrottleImpl.BUILDER, PacketEngineThrottle.class,
				PacketEngineThrottle.TYPE_CHAR);
		addDefaultBuilder(PacketTrainFunctionImpl.BUILDER, PacketTrainFunction.class,
				PacketTrainFunction.TYPE_CHAR);
		addDefaultBuilder(PacketListBitContentsImpl.BUILDER, PacketListBitContents.class,
				PacketListBitContents.TYPE_CHAR);
		addDefaultBuilder(PacketOperationFailedImpl.BUILDER, PacketOperationFailed.class,
				PacketOperationFailed.TYPE_CHAR);
		addDefaultBuilder(PacketOperationSuccessfulImpl.BUILDER,
				PacketOperationSuccessful.class, PacketOperationSuccessful.TYPE_CHAR);
		addDefaultBuilder(PacketOutputPinImpl.BUILDER, PacketOutputPin.class,
				PacketOutputPin.TYPE_CHAR);
		addDefaultBuilder(PacketReadConfigurationByteImpl.BUILDER,
				PacketReadConfigurationByte.class, PacketReadConfigurationByte.TYPE_CHAR);
		addDefaultBuilder(PacketReadCurrentImpl.BUILDER, PacketReadCurrent.class,
				PacketReadCurrent.TYPE_CHAR);
		addDefaultBuilder(PacketReadStationStateImpl.BUILDER,
				PacketReadStationState.class, PacketReadStationState.TYPE_CHAR);
		addDefaultBuilder(PacketSensorImpl.BUILDER, PacketSensor.class,
				PacketSensor.TYPE_CHAR);
		addDefaultBuilder(PacketSensorStateImpl.BUILDER, PacketSensorState.class,
				PacketSensorState.TYPE_CHAR);
		addDefaultBuilder(PacketSensorStateInactiveImpl.BUILDER,
				PacketSensorStateInactive.class, PacketSensorStateInactive.TYPE_CHAR);
		addDefaultBuilder(PacketStationInfoImpl.BUILDER, PacketStationInfo.class,
				PacketStationInfo.TYPE_CHAR);
		addDefaultBuilder(PacketStoreSettingsImpl.BUILDER, PacketStoreSettings.class,
				PacketStoreSettings.TYPE_CHAR);
		addDefaultBuilder(PacketTrackPowerOffImpl.BUILDER, PacketTrackPowerOff.class,
				PacketTrackPowerOff.TYPE_CHAR);
		addDefaultBuilder(PacketTrackPowerOnImpl.BUILDER, PacketTrackPowerOn.class,
				PacketTrackPowerOn.TYPE_CHAR);
		addDefaultBuilder(PacketTrackPowerStateImpl.BUILDER, PacketTrackPowerState.class,
				PacketTrackPowerState.TYPE_CHAR);
		addDefaultBuilder(PacketTurnoutImpl.BUILDER, PacketTurnout.class,
				PacketTurnout.TYPE_CHAR);
		addDefaultBuilder(PacketTurnoutStateImpl.BUILDER, PacketTurnoutState.class,
				PacketTurnoutState.TYPE_CHAR);
	}

	private static void addDefaultBuilder(PacketBuilder<? extends Packet> builder,
			Class<? extends Packet> clazz, char typeChar) {
		DEFAULT_PACKET_BUILDERS.add(builder);
		DEFAULT_PACKET_BUILDER_CLASSES.add(clazz);
		DEFAULT_PACKET_BUILDER_CHARS.add(typeChar);
	}

	/**
	 * Registers {@link PacketBuilder}s for the following {@link Packet} types
	 * for a {@link PacketFactory} using
	 * {@link PacketFactory#registerBuilder(PacketBuilder, Class, char)}.
	 * <code>Packet</code> types that are already registered are ignored:<br>
	 * {@link PacketAccessoryOperate}, {@link PacketClearSettings},
	 * {@link PacketEngineThrottle}, {@link PacketTrainFunction},
	 * {@link PacketListBitContents}, {@link PacketOperationFailed},
	 * {@link PacketOperationSuccessful}, {@link PacketOutputPin} (with sub
	 * packets: {@link PacketOutputPinDefine}, {@link PacketOutputPinDelete},
	 * {@link PacketOutputPinList}, {@link PacketOutputPinSetState}),
	 * {@link PacketReadConfigurationByte}, {@link PacketReadCurrent},
	 * {@link PacketReadStationState}, {@link PacketSensor} (with sub packets:
	 * {@link PacketSensorDefine}, {@link PacketSensorDelete},
	 * {@link PacketSensorList}), {@link PacketSensorState} (with sub packets:
	 * {@link PacketSensorStateActive}, {@link PacketSensorStateList}),
	 * {@link PacketSensorStateInactive}, @{@link PacketStationInfo},
	 * {@link PacketStoreSettings}, {@link PacketTrackPowerOff},
	 * {@link PacketTrackPowerOn}, {@link PacketTrackPowerState},
	 * {@link PacketTurnout} (with sub packets {@link PacketTurnoutDefine},
	 * {@link PacketTurnoutDelete}, {@link PacketTurnoutList},
	 * {@link PacketTurnoutThrow}),
	 * 
	 * @param factory
	 *            The packet factory to register the packets for.
	 */
	@SuppressWarnings("unchecked")
	public static void regiserDefaultPackets(PacketFactory factory) {
		for (int i = 0; i < DEFAULT_PACKET_BUILDERS.size(); i++) {
			try {
				factory.registerBuilder(
						(PacketBuilder<Packet>) DEFAULT_PACKET_BUILDERS.get(i),
						(Class<Packet>) DEFAULT_PACKET_BUILDER_CLASSES.get(i),
						DEFAULT_PACKET_BUILDER_CHARS.get(i));
			}
			catch (IllegalStateException ex) {}
		}
	}

	private final Map<Class<?>, PacketBuilder<?>> BUILDERS_BY_CLASS = new HashMap<>();
	private final Map<Character, PacketBuilder<?>> BUILDERS_BY_CHAR = new HashMap<>();

	@Override
	public <P extends Packet> void registerBuilder(PacketBuilder<P> builder,
			Class<P> clazz, char typeChar) {
		Objects.requireNonNull(builder, "builder");
		Objects.requireNonNull(clazz, "clazz");
		if (BUILDERS_BY_CLASS.containsKey(clazz)) {
			throw new IllegalStateException(
					"builder for class " + clazz.getName() + " already exists");
		}
		if (BUILDERS_BY_CHAR.containsKey(typeChar)) {
			throw new IllegalStateException(
					"builder for char '" + typeChar + "' already exists");
		}
		BUILDERS_BY_CLASS.put(clazz, builder);
		BUILDERS_BY_CHAR.put(typeChar, builder);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <P extends Packet> P buildPacket(Class<P> packetClass,
			List<String> parameters) {
		PacketBuilder<P> builder = (PacketBuilder<P>) BUILDERS_BY_CLASS.get(packetClass);
		if (builder != null) {
			return builder.build(parameters);
		}
		return null;
	}

	@Override
	public Packet buildPacket(char typeChar, List<String> parameters) {
		PacketBuilder<?> builder = BUILDERS_BY_CHAR.get(typeChar);
		if (builder != null) {
			return builder.build(parameters);
		}
		return null;
	}

}
