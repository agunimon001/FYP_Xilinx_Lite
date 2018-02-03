package com.xilinxlite.gui.functions;

import java.util.logging.Logger;

import com.xilinxlite.communication.CommunicationMgr;
import com.xilinxlite.gui.MenuBarDesign;

/**
 * Function implementation for MenuBarDesign GUI.
 * 
 * @author Ong Hock Leng
 *
 */
public class MenuBarMgr extends MenuBarDesign {

	private static final Logger logger = Logger.getLogger(MenuBarMgr.class.getName());

	private CommunicationMgr cmdMgr;
	private FunctionController fnController;

	public MenuBarMgr(CommunicationMgr cmdMgr, FunctionController fnController) {
		this.cmdMgr = cmdMgr;
		this.fnController = fnController;
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
	 * Opens project. TO-DO: Require further implementation.
	 */
	@Override
	public void openProject() {
		cmdMgr.openProject("testProj");
		System.out.println(">>" + cmdMgr.getProjectName());
		fnController.update();

		logger.warning("openProject() implemented test.");
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
