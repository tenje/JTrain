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
package de.tenje.jtrain.dccpp;

import java.util.Collections;
import java.util.List;

/**
 * A packet of the DCC++ protocol. A raw packet is of the following form:
 * <i>&lt;C P1 P2 Pn&gt;</i>, where <i>C</i> is a single alphanumeric character
 * and P1-Pn are optional alphanumeric parameters. A detailed description can be
 * found <a href=
 * "https://github.com/DccPlusPlus/BaseStation/blob/master/DCC%2B%2B%20Arduino%20Sketch.pdf">here</a>.<br>
 * 
 * The table shows the packet types by packet char.
 * <table>
 * <tr>
 * <th>Type char</th>
 * <th align="left">Packet</th>
 * </tr>
 * <tr>
 * <td>'0'</td>
 * <td>{@link PacketTrackPowerOff}</td>
 * </tr>
 * <tr>
 * <td>'1'</td>
 * <td>{@link PacketTrackPowerOn}</td>
 * </tr>
 * <tr>
 * <td>'a'</td>
 * <td>{@link PacketAccessoryOperate}</td>
 * </tr>
 * <tr>
 * <td>'b'</td>
 * <td>{@link PacketWriteConfigurationBitMainTrack}</td>
 * </tr>
 * <tr>
 * <td>'B'</td>
 * <td>{@link PacketWriteConfigurationBitProgrammingTrack}</td>
 * </tr>
 * <tr>
 * <td>'c'</td>
 * <td>{@link PacketReadCurrent}</td>
 * </tr>
 * <tr>
 * <td>'D'</td>
 * <td>{@link PacketEnterDiagnosticMode}</td>
 * </tr>
 * <tr>
 * <td>'e'</td>
 * <td>{@link PacketClearSettings}</td>
 * </tr>
 * <tr>
 * <td>'E'</td>
 * <td>{@link PacketStoreSettings}</td>
 * </tr>
 * <tr>
 * <td>'f'</td>
 * <td>{@link PacketTrainFunction}</td>
 * </tr>
 * <tr>
 * <td>'H'</td>
 * <td>{@link PacketTurnoutState}</td>
 * </tr>
 * <tr>
 * <td>'L'</td>
 * <td>{@link PacketListBitContents}</td>
 * </tr>
 * <tr>
 * <td>'M'</td>
 * <td>{@link PacketWriteRawDccPacket}</td>
 * </tr>
 * <tr>
 * <td>'O'</td>
 * <td>{@link PacketOperationSuccessful}</td>
 * </tr>
 * <tr>
 * <td>'q'</td>
 * <td>{@link PacketSensorStateInactive}</td>
 * </tr>
 * <tr>
 * <td>'Q'</td>
 * <td>{@link PacketSensorState} (And sub packets)</td>
 * </tr>
 * <tr>
 * <td>'R'</td>
 * <td>{@link PacketReadConfigurationByte}</td>
 * </tr>
 * <tr>
 * <td>'s'</td>
 * <td>{@link PacketReadStationState}</td>
 * </tr>
 * <tr>
 * <td>'S'</td>
 * <td>{@link PacketSensor} (And sub packets)</td>
 * </tr>
 * <tr>
 * <td>'t'</td>
 * <td>{@link PacketEngineThrottle}</td>
 * </tr>
 * <tr>
 * <td>'T'</td>
 * <td>{@link PacketTurnout} (And sub packets)</td>
 * </tr>
 * <tr>
 * <td>'w'</td>
 * <td>{@link PacketWriteConfigurationByteMainTrack}</td>
 * </tr>
 * <tr>
 * <td>'W'</td>
 * <td>{@link PacketWriteConfigurationByteProgrammingTrack}</td>
 * </tr>
 * <tr>
 * <td>'X'</td>
 * <td>{@link PacketOperationFailed}</td>
 * </tr>
 * <tr>
 * <td>'Y'</td>
 * <td>{@link PacketOutputPinState}</td>
 * </tr>
 * <tr>
 * <td>'Z'</td>
 * <td>{@link PacketOutputPin} (And sub packets)</td>
 * </tr>
 * </table>
 * 
 * @author Jonas Tennié
 */
public interface Packet {

	/**
	 * The char to identify the packet type.
	 * 
	 * @return The chat to identify the packet type.
	 */
	char getTypeChar();

	/**
	 * Returns an unmodifiable {@link List} of {@link String}s containing the
	 * raw packet data. The list may be empty, but not <code>null</code>. The strings
	 * only contain alphanumeric chars.
	 * 
	 * @return An unmodifiable {@link List} of {@link String}s containing the
	 *         raw packet data.
	 * @see Collections#unmodifiableList(List)
	 */
	List<String> getRawParameters();

}
