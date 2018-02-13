package com.xilinxlite.gui.functions;

import java.util.logging.Logger;

import com.xilinxlite.gui.MenuBarDesign;

/**
 * Function implementation for MenuBarDesign GUI.
 * 
 * @author Ong Hock Leng
 *
 */
public class MenuBarMgr extends MenuBarDesign {

	private static final Logger logger = Logger.getLogger(MenuBarMgr.class.getName());

	private FunctionPack fnPack = FunctionPack.getInstance();

	@Override
	public void newProject() {
		logger.warning("newProject() not implemented.");
	}

	@Override
	public void createFile() {
		logger.warning("createFile() not implemented.");
	}

	@Override
	public void openProject() {
		fnPack.openProject();
	}

	@Override
	public void closeProject() {
		logger.warning("closeProject() not implemented.");
	}

	@Override
	public void projectSettings() {
		new ProjectSettingsMgr().launch();
	}

	@Override
	public void synchronizeFiles() {
		logger.warning("synchronizeFiles() not implemented.");
	}

	@Override
	public void connectionSettings() {
		logger.warning("connectionSettings() not implemented.");
	}

	@Override
	public void help() {
		logger.warning("help() not implemented.");
		// MainGUI.about();
	}

}
