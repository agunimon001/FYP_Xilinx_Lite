package com.xilinxlite.gui.functions;

import java.util.logging.Logger;

import com.xilinxlite.gui.MenuBarDesign;

import javafx.scene.control.MenuItem;

/**
 * Function implementation for MenuBarDesign GUI.
 * 
 * @author Ong Hock Leng
 *
 */
public class MenuBarMgr extends MenuBarDesign implements Updateable {

	private static final Logger logger = Logger.getLogger(MenuBarMgr.class.getName());

	private FunctionPack fnPack = FunctionPack.getInstance();

	@Override
	public void newProject() {
		if (!fnPack.getCommunicationMgr().isNone())
			fnPack.newProject();
	}

	@Override
	public void createFile() {
		logger.warning("createFile() not implemented.");
	}

	@Override
	public void openProject() {
		if (!fnPack.getCommunicationMgr().isNone())
			fnPack.openProject();
	}

	@Override
	public void closeProject() {
		if (!fnPack.getCommunicationMgr().isNone())
			fnPack.closeProject();
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

	@Override
	public void update() {
		boolean flag = !fnPack.getCommunicationMgr().isRemote();
		for (MenuItem i : menuItems) {
			i.setDisable(flag);
		}
	}

}
