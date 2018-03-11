package com.xilinxlite.gui.functions;

import java.io.File;
import java.util.List;
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

/**
 * Singleton. Contains access to functions for global use.
 * 
 * @author Ong Hock Leng
 *
 */
public class FunctionPack {

	private static Logger logger = Logger.getLogger(FunctionPack.class.getName());

	private static FunctionPack instance = null;

	private CommunicationMgr cmdMgr = null;
	private Updateable updater = null;
	private File ROOT_FOLDER = null;

	/**
	 * Private constructor for singleton.
	 * 
	 * @param cmdMgr
	 *            One instance of CommunicationMgr
	 * @param updater
	 *            One instance of Updateable
	 */
	private FunctionPack(CommunicationMgr cmdMgr, Updateable updater, File ROOT_FOLDER) {
		this.cmdMgr = cmdMgr;
		this.updater = updater;
		this.ROOT_FOLDER = ROOT_FOLDER;
	}

	/**
	 * Allows getting instance from anywhere
	 * 
	 * @return FunctionPack instance if available; null if otherwise
	 */
	public static FunctionPack getInstance() {
		return instance;
	}

	/**
	 * 
	 * @param cmdMgr
	 * @param updater
	 * @return FunctionPack instance if instantiated successfully; null if otherwise
	 */
	public static FunctionPack getInstance(CommunicationMgr cmdMgr, Updateable updater, File ROOT_FOLDER) {
		if (instance == null && cmdMgr != null && updater != null && ROOT_FOLDER != null) {
			instance = new FunctionPack(cmdMgr, updater, ROOT_FOLDER);
		}

		return instance;
	}

	/**
	 * Gets instance of CommunicationMgr
	 * 
	 * @return instance of CommunicationMgr
	 */
	public CommunicationMgr getCommunicationMgr() {
		return cmdMgr;
	}

	/**
	 * Checks if project is closed
	 * 
	 * @return True if no project is opened; false if otherwise
	 */
	public boolean isProjectClosed() {
		return cmdMgr.getProjectName().isEmpty();
	}

	/**
	 * Checks if any project is opened. Prompt user if user wish to close existing
	 * project.
	 * 
	 * @return True if no project is left open; false if otherwise
	 */
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

	/**
	 * Prompts user to locate a project to open.
	 */
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

	/**
	 * Creates new project. Checks if any project is opened before creating a new
	 * one.
	 */
	public void newProject() {
		// Check for open project
		if (checkAndCloseProject()) {
			new ProjectSettingsMgr(true).launch();
		}
	}

	/**
	 * Closes opened project if any.
	 */
	public void closeProject() {
		cmdMgr.closeProject();
		updater.update();
	}

	/**
	 * Execute synthesis
	 */
	public void synthesize() {
		cmdMgr.synthesize();
		updater.update();
	}

	/**
	 * Updates assigned Updateable.
	 */
	public void update() {
		updater.update();
	}

	/**
	 * Get list of verilog files used by Xilinx project
	 * 
	 * @return list of verilog files
	 */
	public List<String> getVerilogFiles() {
		return cmdMgr.getFiles();
	}

	/**
	 * Gets current working directory
	 * 
	 * @return current working directory
	 */
	public String getWorkingDirectory() {
		return cmdMgr.getWorkingDirectory();
	}

	/**
	 * Adds file indicated by filepath to project.
	 * 
	 * @param filepath
	 * @return True if added successfully; false if otherwise
	 */
	public boolean addFile(String filepath) {
		return cmdMgr.addFile(filepath);
	}

	/**
	 * Removes file indicated by filepath from project.
	 * 
	 * @param filepath
	 * @return True if file is removed successfully; false if otherwise
	 */
	public boolean removeFile(String filepath) {
		return cmdMgr.removeFile(filepath);
	}

	/**
	 * Returns current project name.
	 * 
	 * @return Project name; empty String if unavailable.
	 */
	public String getProjectName() {
		return cmdMgr.getProjectName();
	}

	/**
	 * Returns current connection status. Available status are LOCAL, REMOTE and
	 * NONE.
	 * 
	 * @return Connection status in String format
	 */
	public String getConnectionStatus() {
		return cmdMgr.getConnectiontype().toString();
	}

	/**
	 * Gets the top module of the project.
	 * 
	 * @return Value of the top module; empty string if doesn't exist
	 */
	public String getTopModule() {
		return cmdMgr.getTopModule();
	}

	/**
	 * Gets the list of available modules to be set as top module.
	 * 
	 * @return list of modules; empty list if nothing
	 */
	public List<String> getTopModules() {
		return cmdMgr.getTopModules();
	}

	/**
	 * Set top module to the project for synthesis.
	 * 
	 * @param topModule
	 *            Module to be set as top module for the project
	 */
	public void setTopModule(String topModule) {
		cmdMgr.setTopModule(topModule);
	}

	/**
	 * Does Simulation based on the opened project.
	 */
	public void simulate() {
		// TODO: simulate
	}
}
