package com.xilinxlite.gui;

import javafx.application.Platform;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;

/**
 * Provides MenuBar GUI design. Use getInstance() to obtain a MenuBar. Extend
 * this class to implement required functions.
 * 
 * @author Ong Hock Leng
 *
 */
public abstract class MenuBarDesign {

	/**
	 * Call this method to get a MenuBar GUI. Best to use BorderPane to put this
	 * MenuBar to the edge.
	 * 
	 * @return Instance of MenuBar
	 */
	public MenuBar getMenubar() {
		// MenuBar
		MenuBar mb = new MenuBar();

		////////////////
		// Menu: File //
		////////////////

		Menu fileMenu = new Menu("File");
		Menu fMenuNew = new Menu("New...");
		MenuItem fMenuNewProject = new MenuItem("Project");
		MenuItem fMenuNewFile = new MenuItem("File");
		fMenuNew.getItems().addAll(fMenuNewProject, fMenuNewFile);
		MenuItem fMenuOpenProject = new MenuItem("Open Project");
		MenuItem fMenuClose = new MenuItem("Close Project");
		MenuItem fMenuExit = new MenuItem("Exit");
		fileMenu.getItems().addAll(fMenuNew, fMenuOpenProject, fMenuClose, fMenuExit);
		mb.getMenus().add(fileMenu);

		// Functions for Menu: File
		fMenuNewProject.setOnAction(e -> newProject());
		fMenuNewFile.setOnAction(e -> createFile());
		fMenuOpenProject.setOnAction(e -> openProject());
		fMenuClose.setOnAction(e -> closeProject());
		fMenuExit.setOnAction(e -> Platform.exit());

		// Accelerators for Menu: File
		fMenuNew.setAccelerator(KeyCombination.keyCombination("shortcut+N"));
		fMenuOpenProject.setAccelerator(KeyCombination.keyCombination("shortcut+O"));

		///////////////////
		// Menu: Project //
		///////////////////

		Menu projectMenu = new Menu("Project");
		MenuItem pMenuSettings = new MenuItem("Project Settings");
		projectMenu.getItems().add(pMenuSettings);
		mb.getMenus().add(projectMenu);

		// Functions for Menu: Project
		pMenuSettings.setOnAction(e -> projectSettings());

		// Accelerators for Menu: Project

		//////////////////
		// Menu: Remote //
		//////////////////

		Menu remoteMenu = new Menu("Remote");
		MenuItem rMenuSync = new MenuItem("Synchronize");
		MenuItem rMenuConnection = new MenuItem("Connection Settings");
		remoteMenu.getItems().addAll(rMenuSync, rMenuConnection);
		mb.getMenus().add(remoteMenu);

		// Functions for Menu: Remote
		rMenuSync.setOnAction(e -> synchronizeFiles());
		rMenuConnection.setOnAction(e -> connectionSettings());

		// Accelerators for Menu: Remote

		////////////////
		// Menu: Help //
		////////////////

		Menu helpMenu = new Menu("Help");
		MenuItem hMenuAbout = new MenuItem("About Xilinx Lite");
		helpMenu.getItems().add(hMenuAbout);
		mb.getMenus().add(helpMenu);

		// Functions for Menu: Help
		hMenuAbout.setOnAction(e -> help());

		return mb;
	}

	/**
	 * Function for creating new Project
	 */
	protected abstract void newProject();

	/**
	 * Function for creating a new file. Not an IDE, so don't need to allow editing.
	 * Let user edit with their own editor.
	 */
	protected abstract void createFile();

	/**
	 * Opens project. If project is in a different working directory, the working
	 * directory is changed to the new directory.
	 */
	protected abstract void openProject();

	/**
	 * Closes currently opened project.
	 */
	protected abstract void closeProject();

	/**
	 * Opens project settings window.
	 */
	protected abstract void projectSettings();

	/**
	 * Synchronize files with remote server.
	 */
	protected abstract void synchronizeFiles();

	/**
	 * Opens connection settings window.
	 */
	protected abstract void connectionSettings();

	/**
	 * Show help.
	 */
	protected abstract void help();
}
