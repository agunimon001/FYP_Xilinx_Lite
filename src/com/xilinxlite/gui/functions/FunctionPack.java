package com.xilinxlite.gui.functions;

import java.io.File;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.xilinxlite.communication.CommunicationMgr;
import com.xilinxlite.gui.functions.PropertiesController.KEY;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class FunctionPack {

	private static Logger logger = Logger.getLogger(FunctionPack.class.getName());

	private static FunctionPack instance = null;

	private CommunicationMgr cmdMgr = null;
	private Updateable updater = null;

	private FunctionPack(CommunicationMgr cmdMgr, Updateable updater) {
		this.cmdMgr = cmdMgr;
		this.updater = updater;
	}

	public static FunctionPack getInstance() {
		return instance;
	}

	public static FunctionPack getInstance(CommunicationMgr cmdMgr, Updateable updater) {
		if (instance == null) {
			instance = new FunctionPack(cmdMgr, updater);
		}

		return instance;
	}
	
	public CommunicationMgr getCommunicationMgr() {
		return cmdMgr;
	}

	private boolean checkAndCloseProject() {
		// Check if a project is currently opened, close if user accepts
		if (!cmdMgr.getProjectName().isEmpty()) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setContentText("Close current project?");
			alert.getButtonTypes().clear();
			alert.getButtonTypes().add(ButtonType.YES);
			alert.getButtonTypes().add(ButtonType.NO);
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.YES) {
				cmdMgr.closeProject();
				updater.update();
			} else {
				return false;
			}
		}
		
		return true;
	}

	public void openProject() {
		// Check for open project
		if (!checkAndCloseProject())
			return;

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
				// Updates updater if project opens successfully
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

	public void newProject() {
		// Check for open project
		checkAndCloseProject();

//		new ProjectSettingsMgr().launch();
	}
}
