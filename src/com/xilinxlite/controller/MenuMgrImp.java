package com.xilinxlite.controller;

import com.xilinxlite.model.ProjectSettings;

public class MenuMgrImp implements MenuMgr {

	@Override
	public ProjectSettings newProject() {
		System.err.println("newProject() not implemented.");
		return null;
	}

	@Override
	public void createFile() {
		System.err.println("createFile() not implemented.");
	}

	@Override
	public ProjectSettings openProject() {
		System.err.println("openProject() not implemented.");
		return null;
	}

	@Override
	public void saveProject() {
		System.err.println("saveProject() not implemented.");
	}

	@Override
	public void saveProjectAs() {
		System.err.println("saveProjectAs() not implemented.");
	}

	@Override
	public void closeProject() {
		System.err.println("closeProject() not implemented.");
	}

	@Override
	public void projectSettings() {
		System.err.println("projectSettings() not implemented");
	}

	@Override
	public void synchronizeFiles() {
		System.err.println("synchronizeFiles() not implemented.");
	}

	@Override
	public void connectionSettings() {
		System.err.println("connectionSettings() not implemented.");
	}

}
