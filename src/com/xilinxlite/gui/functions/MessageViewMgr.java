package com.xilinxlite.gui.functions;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.xilinxlite.gui.MessageViewDesign;

/**
 * Function implementations for MessageViewDesign.
 * 
 * @author Ong Hock Leng
 *
 */
public class MessageViewMgr extends MessageViewDesign implements Updateable {

	private static final Logger logger = Logger.getLogger(MessageViewMgr.class.getCanonicalName());

	private FunctionPack fnPack = FunctionPack.getInstance();

	@Override
	public void update() {
		if (fnPack.isProjectClosed()) {
			clearLog();
		}
	}

	@Override
	protected void initialize() {

	}

	@Override
	protected void appendLog(BufferedReader r) {
		String line;
		tp.getSelectionModel().select(logTab);
		try {
			while ((line = r.readLine()) != null) {
				logField.appendText(line);
			}
		} catch (IOException e) {
			logger.log(Level.WARNING, "Error getting reader", e);
		}
	}

	@Override
	protected void clearLog() {
		logField.clear();
	}

	@Override
	protected void viewFile(File file) {
		fileView.clear();
		tp.getSelectionModel().select(fileTab);
		if (file != null) {
			try {
				BufferedReader r = new BufferedReader(new FileReader(file));
				String line;
				while ((line = r.readLine()) != null) {
					if (line.matches("[^\n]*")) {
						line += "\n";
					}
					fileView.appendText(line);
				}
				r.close();
			} catch (FileNotFoundException e) {
				logger.log(Level.SEVERE, "File not found", e);
			} catch (IOException e) {
				logger.log(Level.WARNING, "Cannot read file", e);
			}

			// if (file.getName().endsWith(".syr")) {
			// viewSynthesisReport();
			// }
		} else {
			logger.log(Level.WARNING, "file is not valid");
		}

	}

	// private void viewSynthesisReport() {
	// String content = fileView.getText();
	// String output =
	//
	// Pattern p = Pattern.compile("Number of Slice LUTs:\\s+(\\d+\\s+out
	// of\\s+\\d+\\s+\\d+%)");
	// Matcher m = p.matcher(content);
	//
	// }

}
