package com.xilinxlite.gui.functions;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.xilinxlite.communication.CommunicationMgr;
import com.xilinxlite.gui.MenuBarDesign;
import com.xilinxlite.gui.functions.PropertiesController.KEY;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

/**
 * Function implementation for MenuBarDesign GUI.
 * 
 * @author Ong Hock Leng
 *
 */
public class MenuBarMgr extends MenuBarDesign {

	private static final Logger logger = Logger.getLogger(MenuBarMgr.class.getName());

	private CommunicationMgr cmdMgr;
	private UpdateController updater;

	public MenuBarMgr(CommunicationMgr cmdMgr, UpdateController updater) {
		this.cmdMgr = cmdMgr;
		this.updater = updater;
	}

	/**
	 * Function for creating new Project
	 */
	@Override
	public void newProject() {
		logger.warning("newProject() not implemented.");
	}

	/**
	 * Function for creating a new file. Not an IDE, so don't need to allow editing.
	 * Let user edit with their own editor.
	 */
	@Override
	public void createFile() {
		logger.warning("createFile() not implemented.");
	}

	/**
	 * Opens project. If project is in a different working directory, the working
	 * directory is changed to the new directory.
	 */
	@Override
	public void openProject() {
		// Execute FileChooser to obtain project path
		FileChooser fc = new FileChooser();
		fc.getExtensionFilters().add(new ExtensionFilter("Xilinx ISE project", "*.xise"));
		fc.setInitialDirectory(new File(cmdMgr.getWorkingDirectory()));
		File project = fc.showOpenDialog(new Stage());

		// A file has been selected
		if (project != null) {
			// Update with new working directory if different from preset working directory
			if (!project.getParent().equals(cmdMgr.getWorkingDirectory())) {
				cmdMgr.setWorkingDirectory(project.getParent());

				// Save properties
				PropertiesController props = PropertiesController.load();
				if (props != null) {
					props.put(KEY.KEY_WD, project.getParent());
					props.save();
				}

				// Inform user working directory has been changed
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setContentText("Working Directory has been changed to your project's folder");
				alert.showAndWait();
			}

			// Extract project name for use to ensure project is indeed in working directory
			Pattern p = Pattern.compile(".*(\\\\|/)(?<name>[^\\\\/]+).xise");
			Matcher m = p.matcher(project.getAbsolutePath());
			if (m.matches()) {
				// Updates fnController if project opens successfully
				if (cmdMgr.openProject(m.group("name"))) {
					updater.update();
				}

				// Project fails to open
				else {
					logger.log(Level.SEVERE, "Problem opening project at " + project.getAbsolutePath(),
							new Exception());

					// Execute pop-up alert to inform user of failure
					Alert alert = new Alert(AlertType.NONE);
					alert.setContentText("Cannot open project");
					alert.showAndWait();
				}
			}
		}

	}

	/**
	 * Saves currently opened project.
	 */
	@Override
	public void saveProject() {
		logger.warning("saveProject() not implemented.");
	}

	/**
	 * Saves currently opened project as another name.
	 */
	@Override
	public void saveProjectAs() {
		logger.warning("saveProjectAs() not implemented.");
	}

	/**
	 * Closes currently opened project.
	 */
	@Override
	public void closeProject() {
		logger.warning("closeProject() not implemented.");
	}

	/**
	 * Opens project settings window.
	 */
	@Override
	public void projectSettings() {
		logger.warning("projectSettings() not implemented");
	}

	/**
	 * Synchronize files with remote server.
	 */
	@Override
	public void synchronizeFiles() {
		logger.warning("synchronizeFiles() not implemented.");
	}

	/**
	 * Opens connection settings window.
	 */
	@Override
	public void connectionSettings() {
		logger.warning("connectionSettings() not implemented.");
	}

	/**
	 * Show help.
	 */
	@Override
	public void help() {
		logger.warning("help() not implemented.");
		// MainGUI.about();
	}

}
