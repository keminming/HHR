package ui;

import hhr.HHRScanner;
import java.awt.Container;
import java.awt.GridLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

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

	public JPanel getMiddlePanel() {
		return middlePanel;
	}

	public void setMiddlePanel(JPanel middlePanel) {
		this.middlePanel = middlePanel;
	}

	public JPanel getButtomPanel() {
		return buttomPanel;
	}

	public void setButtomPanel(JPanel buttomPanel) {
		this.buttomPanel = buttomPanel;
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
	private HHRConfig config;
	private HHRBackup backup;
	private ProgressChecker checker;
	
	private JTextField inputDirChooser;
	private JTextField outputDirChooser;
	private JTextField dependencyDirChooser;
	private JTextField ffmpegDirChooser;
	
	private JPanel upPanel;
	private JPanel middlePanel;
	private JPanel buttomPanel;
	
	private JTextArea console;
	private JTextArea stat;
	private JScrollPane scroll;
	
	private JButton start;
	private JButton save;

	private Thread workerThread;
	private Thread statThread;
	CustomOutputStream guiPrintStream;
	
	boolean stopFlag = true;
	
	public HHRUI() {
		super("HRR Scanner");
	
		Container panel = this.getContentPane();
		panel.setLayout(new BoxLayout(panel,BoxLayout.PAGE_AXIS));
		
		inputDirChooser = new JTextField();
		outputDirChooser = new JTextField();
		dependencyDirChooser = new JTextField();
		ffmpegDirChooser = new JTextField();	
		start = new JButton("Scan");
		
		inputDirChooser.setBorder(new TitledBorder("Input Root Directory"));
		outputDirChooser.setBorder(new TitledBorder("Output Root Directory"));
		dependencyDirChooser.setBorder(new TitledBorder("Dependency Directory"));
		ffmpegDirChooser.setBorder(new TitledBorder("FFMPEG Directory"));

		
		upPanel = new JPanel();
		upPanel.setLayout(new GridLayout(0,2));
		upPanel.add(inputDirChooser);
		upPanel.add(outputDirChooser);
		upPanel.add(dependencyDirChooser);
		upPanel.add(ffmpegDirChooser);
		panel.add(upPanel);
		
		console = new JTextArea("Waiting for start...",10,50);
		stat = new JTextArea("",10,50);
		stat.setBorder(new TitledBorder("Status"));
		
		scroll = new JScrollPane(console);
		scroll.setBorder(new TitledBorder("Console"));
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		middlePanel = new JPanel();
		middlePanel.setLayout(new GridLayout(0,2));
		middlePanel.add(stat);
		middlePanel.add(scroll);
		panel.add(middlePanel);
		
		buttomPanel = new JPanel();
		buttomPanel.setLayout(new GridLayout(0,1));
		buttomPanel.add(start);
		panel.add(buttomPanel);
		
		scanner = HHRScanner.getInstance();
		checker = new ProgressChecker(this,scanner.getStat());
		config = new HHRConfig();
		backup = HHRBackup.getInstance();
		backup.init(config);
		
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
					
					console.setText("");
					console.append("Scanning...\n");
					guiPrintStream = new CustomOutputStream(System.out,console);
					System.setOut(guiPrintStream);
					System.setErr(guiPrintStream);
								
					scanner.setSource(inputDirChooser.getText());
				    scanner.setTarget(outputDirChooser.getText());
				    scanner.setDependency(dependencyDirChooser.getText());
					scanner.setFfmpeg(ffmpegDirChooser.getText());
	
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
