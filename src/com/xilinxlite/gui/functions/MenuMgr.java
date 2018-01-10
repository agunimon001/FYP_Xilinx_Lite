package com.xilinxlite.gui.functions;

import com.xilinxlite.ProjectSettings;

/**
 * Functions for the menu bar of the main GUI.
 * 
 * @author Ong Hock Leng
 *
 */
public interface MenuMgr {

	// File
	public void newProject();
	
	public void createFile();
	
	public void openProject();
	
	public void saveProject();
	
	public void saveProjectAs();
	
	public void closeProject();
	
	// Project
	public void projectSettings();
	
	// Remote
	public void synchronizeFiles();
	
	public void connectionSettings();
	
	// Help
	public void help();
}
