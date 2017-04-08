package org.tenje.jtrain.runnable;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import org.tenje.jtrain.dccpp.server.DccppStation;

/**
 * A {@link DccppStation} controlled by a {@link javax.swing} GUI.
 * 
 * @author Jonas Tennié
 */
public class JTrainDccppStationGui {

	/**
	 * Starts the program.
	 * 
	 * @param args
	 *            Not used.
	 * @throws ClassNotFoundException
	 *             Never thrown.
	 * @throws InstantiationException
	 *             Never thrown.
	 * @throws IllegalAccessException
	 *             Never thrown.
	 * @throws UnsupportedLookAndFeelException
	 *             Never thrown.
	 */
	public static void main(String[] args)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException,
			UnsupportedLookAndFeelException {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		new JTrainDccppStationGui();
	}

	private DccppStation station;

	private JTrainDccppStationGui() {
		final JFrame frame = new JFrame("DCC++ station");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(null);

		JLabel controllerPortLabel = new JLabel("Controller port:");
		JLabel accessoryPortLabel = new JLabel("Accessory port:");
		final JSpinner controllerPortSelector = new JSpinner(
				new SpinnerNumberModel(2560, 0, 65535, 1));
		controllerPortSelector
				.setEditor(new JSpinner.NumberEditor(controllerPortSelector, "#"));
		final JSpinner accessoryPortSelector = new JSpinner(
				new SpinnerNumberModel(2561, 0, 65535, 1));
		accessoryPortSelector
				.setEditor(new JSpinner.NumberEditor(accessoryPortSelector, "#"));
		final JButton startStopButton = new JButton("Start");
		startStopButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				startStopButton.setEnabled(false);
				if (station == null) {
					int controllerPort = (int) controllerPortSelector.getValue();
					int accessoryPort = (int) accessoryPortSelector.getValue();
					System.out.println(
							"Starting station on controller port " + controllerPort
									+ " and accessory port " + accessoryPort + "...");
					try {
						station = new DccppStation(controllerPort, accessoryPort);
						System.out.println("Started");
					}
					catch (IOException ex) {
						ex.printStackTrace();
					}
					startStopButton.setText("Stop");
				}
				else {
					System.out.println("Stopping station...");
					try {
						station.close();
						System.out.println("Stopped");
					}
					catch (IOException ex) {
						ex.printStackTrace();
					}
					station = null;
					startStopButton.setText("Start");
				}
				startStopButton.setEnabled(true);
			}
		});
		final JTextArea logArea = new JTextArea();
		logArea.setEditable(false);
		logArea.setLineWrap(true);
		logArea.setWrapStyleWord(true);
		logArea.setFont(new Font(logArea.getFont().getFontName(),
				logArea.getFont().getStyle(), 18));
		PrintStream logStream = new PrintStream(new OutputStream() {
			private final StringBuilder sb = new StringBuilder();

			@Override
			public void write(int b) throws IOException {
				if (b == '\r') {
					return;
				}
				if (b == '\n') {
					String text = sb.toString() + "\n";
					logArea.append(text);
					sb.setLength(0);
				}
				else {
					sb.append((char) b);
				}
			}
		}, true);
		System.setOut(logStream);
		System.setErr(logStream);
		final JScrollPane logPane = new JScrollPane(logArea);
		logPane.setBorder(new TitledBorder(new EtchedBorder(), "Log"));
		logPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		controllerPortLabel.setBounds(10, 10, 150, 25);
		frame.add(controllerPortLabel);
		accessoryPortLabel.setBounds(10, 50, 150, 25);
		frame.add(controllerPortSelector);
		controllerPortSelector.setBounds(170, 8, 80, 29);
		frame.add(accessoryPortLabel);
		accessoryPortSelector.setBounds(170, 48, 80, 29);
		frame.add(accessoryPortSelector);
		startStopButton.setBounds(270, 48, 80, 30);
		frame.add(startStopButton);
		logPane.setLocation(10, 90);
		frame.add(logPane);
		frame.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				logPane.setSize(frame.getContentPane().getWidth() - 20,
						frame.getContentPane().getHeight() - 100);
			}
		});
		frame.setMinimumSize(new Dimension(410, 300));
		frame.setSize(500, 400);
		frame.setVisible(true);
	}

}
