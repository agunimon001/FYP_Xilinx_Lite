package com.xilinxlite.controller;

import com.xilinxlite.model.ProjectSettings;

/**
 * Functions for the menu bar of the main GUI.
 * 
 * @author Ong Hock Leng
 *
 */
public interface MenuMgr {

	// File
	public ProjectSettings newProject();
	
	public void createFile();
	
	public ProjectSettings openProject();
	
	public void saveProject();
	
	public void saveProjectAs();
	
	public void closeProject();
	
	// Project
	public void projectSettings();
	
	// Remote
	public void synchronizeFiles();
	
	public void connectionSettings();
}
