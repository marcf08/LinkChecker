package marcus.MA.com.GUI;

import java.awt.event.ActionListener;
import java.io.PrintStream;
import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.Box;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EtchedBorder;
/*
 * LinkChecker is the GUI class. The controller class serves as the logic for the
 * GUI and works between it and the model. This is not the best MVC implementation,
 * but for this small project, it works fine.
 */
public class LinkCheckerGUI extends JFrame {
	/**
	 * Variables.
	 */
	private static final long serialVersionUID = 1L;
	private static final String INSTRUCTIONS = "1. Select a file via Browse.\n"
			+ "2. Run the LinkChecker with the button below.\n"
			+ "3. Program output and progress will appear here.\n";
	private static final String name = "LinkChecker";

	private JTextField fileField;
	private JTextArea textArea;	
	private JButton btnRun;
	private JButton btnSaveAsText;
	private JButton btnBrowse;
	private JButton btnClear;
	private JButton btnStop;
	private Dimension d = new Dimension(1000, 800);
	/**
	 * Create the application.
	 */
	public LinkCheckerGUI() {
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		getContentPane().setEnabled(false);

		setTitle("LinkChecker");
		getContentPane().setLayout(new BorderLayout(0, 0));

		//Create panel for buttons
		JPanel panel = new JPanel();
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		getContentPane().add(panel, BorderLayout.SOUTH);
	
		//Set up buttons
		btnSaveAsText = new JButton("Save As Text");
		panel.add(btnSaveAsText);
		
		btnClear = new JButton("Clear");
		panel.add(btnClear);
		
		btnRun = new JButton("Run Link Checker");
		panel.add(btnRun);
		
		btnStop  = new JButton("Stop");
		panel.add(btnStop);
		
		
		//Set up panel, text area and scroll pane
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		getContentPane().add(panel_1, BorderLayout.NORTH);
		
		JLabel lblFileName = new JLabel("File Name:");
		panel_1.add(lblFileName);
		
		fileField = new JTextField();
		panel_1.add(fileField);
		fileField.setColumns(20);
		
		btnBrowse = new JButton("Browse...");
		panel_1.add(btnBrowse);
		
		textArea = new JTextArea();
	    textArea.setWrapStyleWord(true);
	    JScrollPane scroll = new JScrollPane (textArea);
	 
	    scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	    textArea.setText(INSTRUCTIONS);
	    textArea.setEditable(false);
	    getContentPane().add(scroll, BorderLayout.CENTER);
	
	    //Configure output
		PrintStream printStream = new PrintStream(new CustomOutputStream(textArea));
		System.setOut(printStream);
		System.setErr(printStream);
		
		//Configure struts for sides
		Component horizontalStrut = Box.createHorizontalStrut(20);
		getContentPane().add(horizontalStrut, BorderLayout.WEST);
		Component horizontalStrut_1 = Box.createHorizontalStrut(20);
		getContentPane().add(horizontalStrut_1, BorderLayout.EAST);

		//Set size and name of window
		setSize(d);
		setTitle(name);
		setLocationRelativeTo(null);
		this.setVisible(true);

	}
	
	/*
	 * The controller class uses these methods to add listeners to the
	 * GUI.
	 */
	protected void addListeners(ActionListener browse, ActionListener run, ActionListener save, ActionListener clear, ActionListener stop) {
		btnBrowse.addActionListener(browse);
		btnRun.addActionListener(run);
		btnSaveAsText.addActionListener(save);
		btnClear.addActionListener(clear);
		btnStop.addActionListener(stop);
	}
	
	
/*
 * ****BEGIN GETTERS AND SETTERS FOR THE GUI**** 
 */
	/*
	 * Setter for the text area.
	 * @param text the text that fills the JTextArea
	 */
	protected void setText(String text) {
		textArea.setText(text);
	}
	
	/*
	 * Getter for the text area.
	 */
	protected String getText() {
		return textArea.getText();
	}
	
	/*
	 * Clears the text area.
	 */
	protected void clearTextArea() {
		textArea.setText("");
	}
	
	/*
	 * Setter for the file area.
	 * @param file a string representing the file path
	 */
	protected void setFile(String file) {
		fileField.setText(file);
	}

}
