package marcus.MA.com.GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.apache.commons.io.FileUtils;

import marcus.MA.com.Links;

public class Controller {
	/*
	 * This method lists the variables used for this class.
	 */
	private LinkCheckerGUI gui;
	private Links link;
	private File top;
	//The link checker should not run without being able to do so
	private boolean canRun = false;
	
	public Controller() {
		gui = new LinkCheckerGUI();
	}
	
	/*
	 * This method adds the actions to the GUI.
	 */
	public void addActions() {
		gui.addListeners(new ActionListener() {
			//Browse
			public void actionPerformed(ActionEvent e) {
				doBrowseLogic();
		}
		}, new ActionListener() {
			//Run
			public void actionPerformed(ActionEvent e) {
				doRunLogic();
			}
		}, new ActionListener() {
			//Save
			public void actionPerformed(ActionEvent e) {
				doSaveLogic();
			}
		},new ActionListener() {
			//Clear
			public void actionPerformed(ActionEvent e) {
				doClearLogic();
			}
		},new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				if (link != null) {
					link.stop();
				}
			}
		} );
	}
	
	/*
	 * This helper method performs all of the browsing logic.
	 * Most importantly, it allows the LinkChecker to run if the
	 * file can be read.
	 */
	private void doBrowseLogic() {
		JFileChooser fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fc.showOpenDialog(gui);
		top = fc.getSelectedFile();
		if (top != null) {
			gui.setFile(top.getAbsolutePath());
			if (top.canRead()) {
				canRun = true;
			} 
		}
	}
	
	/*
	 * This helper method performs all of the run logic.
	 */
	private void doRunLogic() {
		if (!canRun) {
			JOptionPane.showMessageDialog(gui, "Cannot read directory.");
		} else {
			gui.clearTextArea();
			if (top != null) {
				link = new Links(top.getAbsolutePath());
				link.start();
			}
		}
	}
	
	/*
	 * This helper method performs all of the clear logic.
	 */
	private void doClearLogic() {
		gui.clearTextArea();
		gui.setFile("");
		top = null;
	}
		
	/*
	 * This helper method performs the save logic.
	 */
	private void doSaveLogic() {
		 try {
			JFileChooser c = new JFileChooser();
			int val = c.showSaveDialog(gui);
		    if (val == JFileChooser.APPROVE_OPTION) {
		    	File file = c.getSelectedFile();
		    	FileUtils.writeStringToFile(file, gui.getText());
		    }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
