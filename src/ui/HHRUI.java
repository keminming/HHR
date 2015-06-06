package ui;

import hhr.HHRScanner;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SpringLayout;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import tool.MediaConverter;

import configure.HHRBackup;
import configure.HHRConfig;

/**
 * Main GUI frame
 * @author ke
 *
 */
public class HHRUI extends JFrame{
	
	public HHRScanner getScanner() {
		return scanner;
	}

	public void setScanner(HHRScanner scanner) {
		this.scanner = scanner;
	}

	public HHRConfig getConfig() {
		return config;
	}

	public void setConfig(HHRConfig config) {
		this.config = config;
	}

	public HHRBackup getBackup() {
		return backup;
	}

	public void setBackup(HHRBackup backup) {
		this.backup = backup;
	}

	public ProgressChecker getChecker() {
		return checker;
	}

	public void setChecker(ProgressChecker checker) {
		this.checker = checker;
	}

	public JTextField getInputDirChooser() {
		return inputDirChooser;
	}

	public void setInputDirChooser(JTextField inputDirChooser) {
		this.inputDirChooser = inputDirChooser;
	}

	public JTextField getOutputDirChooser() {
		return outputDirChooser;
	}

	public void setOutputDirChooser(JTextField outputDirChooser) {
		this.outputDirChooser = outputDirChooser;
	}

	public JTextField getFfmpegDirChooser() {
		return ffmpegDirChooser;
	}

	public void setFfmpegDirChooser(JTextField ffmpegDirChooser) {
		this.ffmpegDirChooser = ffmpegDirChooser;
	}

	public JPanel getUpPanel() {
		return upPanel;
	}

	public void setUpPanel(JPanel upPanel) {
		this.upPanel = upPanel;
	}

	public JTextArea getConsole() {
		return console;
	}

	public void setConsole(JTextArea console) {
		this.console = console;
	}

	public JTextArea getStat() {
		return stat;
	}

	public void setStat(JTextArea stat) {
		this.stat = stat;
	}

	public JScrollPane getScroll() {
		return scroll;
	}

	public void setScroll(JScrollPane scroll) {
		this.scroll = scroll;
	}

	public JButton getStart() {
		return start;
	}

	public void setStart(JButton start) {
		this.start = start;
	}

	public JButton getSave() {
		return save;
	}

	public void setSave(JButton save) {
		this.save = save;
	}

	public Thread getWorkerThread() {
		return workerThread;
	}

	public void setWorkerThread(Thread workerThread) {
		this.workerThread = workerThread;
	}

	public Thread getStatThread() {
		return statThread;
	}

	public void setStatThread(Thread statThread) {
		this.statThread = statThread;
	}

	public CustomOutputStream getGuiPrintStream() {
		return guiPrintStream;
	}

	public void setGuiPrintStream(CustomOutputStream guiPrintStream) {
		this.guiPrintStream = guiPrintStream;
	}

	public boolean isStopFlag() {
		return stopFlag;
	}

	public void setStopFlag(boolean stopFlag) {
		this.stopFlag = stopFlag;
	}

	private HHRScanner scanner;
	private MediaConverter converter;
	private HHRConfig config;
	private HHRBackup backup;
	private ProgressChecker checker;
	
	private JTextField inputDirChooser;
	private JTextField outputDirChooser;
	private JTextField dependencyDirChooser;
	private JTextField ffmpegDirChooser;
	private SpinnerModel model;
	private JSpinner spinner;
	private JPanel upPanel;
	private JPanel left;
	private JPanel right;
	private JPanel buttom;
	
	private JTextArea console;
	private JTextArea stat;
	private JScrollPane scroll;
	
	private JButton start;
	private JButton save;

	private Thread workerThread;
	private Thread statThread;
	private CustomOutputStream guiPrintStream;
	
	boolean stopFlag = true;
	
	public HHRUI() {
		super("HRR Scanner");
	
		JPanel panel = new JPanel();
		//panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
		panel.setLayout(new GridLayout(0,1));
		upPanel = new JPanel();
		SpringLayout layout = new SpringLayout();
		upPanel.setLayout(layout);
		upPanel.setBorder(new TitledBorder("Settings"));
		
		JLabel inputDirLabel = new JLabel("Input Root Directory:");
		JLabel outputDirLabel = new JLabel("Output Root Directory:");
		JLabel dependencyDirLabel = new JLabel("Dependency Directory:");
		JLabel ffmpegLabel = new JLabel("FFMPEG Directory:");
		JLabel ffmpegProcessLabel = new JLabel("FFMPEG Process:");
		
		inputDirChooser = new JTextField("",100);
		outputDirChooser = new JTextField("",100);
		dependencyDirChooser = new JTextField("",100);
		ffmpegDirChooser = new JTextField("",100);
	    model = new SpinnerNumberModel(5, 1, 20, 1);     
		spinner = new JSpinner(model);
		start = new JButton("Scan");
		start.setPreferredSize(new Dimension(100,30));
		
		upPanel.add(inputDirLabel);
		upPanel.add(inputDirChooser);
		upPanel.add(outputDirLabel);
		upPanel.add(outputDirChooser);
		upPanel.add(dependencyDirLabel);
		upPanel.add(dependencyDirChooser);
		upPanel.add(ffmpegLabel);
		upPanel.add(ffmpegDirChooser);
		upPanel.add(ffmpegProcessLabel);
		upPanel.add(spinner);
		upPanel.add(start);
		
		layout.putConstraint(SpringLayout.WEST, inputDirLabel,
                5,
                SpringLayout.WEST, upPanel);
		
		layout.putConstraint(SpringLayout.NORTH, inputDirLabel,
                5,
                SpringLayout.NORTH, upPanel);
		
		layout.putConstraint(SpringLayout.WEST, inputDirChooser,
                5,
                SpringLayout.WEST, upPanel);
		
		layout.putConstraint(SpringLayout.NORTH, inputDirChooser,
                5,
                SpringLayout.SOUTH, inputDirLabel);
		
		layout.putConstraint(SpringLayout.WEST, outputDirLabel,
                5,
                SpringLayout.WEST, upPanel);
		
		layout.putConstraint(SpringLayout.NORTH, outputDirLabel,
                5,
                SpringLayout.SOUTH, inputDirChooser);
		
		layout.putConstraint(SpringLayout.WEST, outputDirChooser,
                5,
                SpringLayout.WEST, upPanel);
		
		layout.putConstraint(SpringLayout.NORTH, outputDirChooser,
                5,
                SpringLayout.SOUTH, outputDirLabel);
		
		layout.putConstraint(SpringLayout.WEST, dependencyDirLabel,
                5,
                SpringLayout.WEST, upPanel);
		
		layout.putConstraint(SpringLayout.NORTH, dependencyDirLabel,
                5,
                SpringLayout.SOUTH, outputDirChooser);
		
		layout.putConstraint(SpringLayout.WEST, dependencyDirChooser,
                5,
                SpringLayout.WEST, upPanel);
		
		layout.putConstraint(SpringLayout.NORTH, dependencyDirChooser,
                5,
                SpringLayout.SOUTH, dependencyDirLabel);
		
		layout.putConstraint(SpringLayout.WEST, ffmpegLabel,
                5,
                SpringLayout.WEST, upPanel);
		
		layout.putConstraint(SpringLayout.NORTH, ffmpegLabel,
                5,
                SpringLayout.SOUTH, dependencyDirChooser);
		
		layout.putConstraint(SpringLayout.WEST, ffmpegDirChooser,
                5,
                SpringLayout.WEST, upPanel);
		
		layout.putConstraint(SpringLayout.NORTH, ffmpegDirChooser,
                5,
                SpringLayout.SOUTH, ffmpegLabel);

		layout.putConstraint(SpringLayout.WEST, ffmpegProcessLabel,
                5,
                SpringLayout.WEST, upPanel);
		
		layout.putConstraint(SpringLayout.NORTH, ffmpegProcessLabel,
                5,
                SpringLayout.SOUTH, ffmpegDirChooser);

		layout.putConstraint(SpringLayout.WEST, spinner,
                5,
                SpringLayout.WEST, upPanel);
		
		layout.putConstraint(SpringLayout.NORTH, spinner,
                5,
                SpringLayout.SOUTH, ffmpegProcessLabel);
		
		layout.putConstraint(SpringLayout.EAST, start,
                0,
                SpringLayout.EAST, ffmpegDirChooser);
		
		layout.putConstraint(SpringLayout.NORTH, start,
                15,
                SpringLayout.SOUTH, ffmpegDirChooser);

		panel.add(upPanel);
		
		buttom = new JPanel();
		buttom.setLayout(new GridLayout(0,2));
	
		left = new JPanel();
		left.setBorder(new TitledBorder("Statistic"));
		stat = new JTextArea("",12,50);
		left.add(stat);
		
		right = new JPanel();
		right.setBorder(new TitledBorder("Console"));
		console = new JTextArea("Waiting for start...",12,50);
		scroll = new JScrollPane(console);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		right.add(scroll);

		buttom.add(left);
		buttom.add(right);

		panel.add(buttom);

		this.add(panel);
		
		config = new HHRConfig();
		backup = HHRBackup.getInstance();
		backup.init(config);
		checker = new ProgressChecker(this);

		this.addWindowListener(new WindowAdapter() {
			public void windowOpened(WindowEvent e) {
				config.readConfig();
				inputDirChooser.setText(config.getSource());
				outputDirChooser.setText(config.getTarget());
				dependencyDirChooser.setText(config.getDependency());
				ffmpegDirChooser.setText(config.getFfmpeg());
			}
		});
		
		this.addWindowListener(new WindowAdapter(){

			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				config.setFfmpeg(ffmpegDirChooser.getText());
				config.setSource(inputDirChooser.getText());
				config.setTarget(outputDirChooser.getText());
				config.setDependency(dependencyDirChooser.getText());
				config.writeConfig();
			}
		});
		
		start.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if(!stopFlag)
				{
					return;
				}
				else
				{
					stopFlag = false;
					backup.backUp();
					
					scanner = HHRScanner.getInstance();
					checker.setStat(scanner.getStat());

					console.setText("");
					console.append("Scanning...\n");
					guiPrintStream = new CustomOutputStream(System.out,console);
					System.setOut(guiPrintStream);
					System.setErr(guiPrintStream);
								
					scanner.setSource(inputDirChooser.getText());
				    scanner.setTarget(outputDirChooser.getText());
				    scanner.setDependency(dependencyDirChooser.getText());
				    
					converter = MediaConverter.getInstance();
					converter.setFfmpeg(ffmpegDirChooser.getText());
					converter.setProcesses((int)spinner.getValue());
	
					statThread = new Thread(checker);		
					workerThread = new Thread(scanner);
		
					workerThread.start();
					statThread.start();
				}
			}
		});
				
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	}
	
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ignored) { }
		
		HHRUI frame = new HHRUI();
		frame.setResizable(false);
		Logger.getRootLogger().setLevel(Level.OFF);
	}
}
