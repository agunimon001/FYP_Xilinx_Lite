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
	 * @return
	 */
	public MenuBar getInstance() {
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
		MenuItem fMenuSave = new MenuItem("Save");
		MenuItem fMenuSaveAs = new MenuItem("Save as...");
		MenuItem fMenuClose = new MenuItem("Close Project");
		MenuItem fMenuExit = new MenuItem("Exit");
		fileMenu.getItems().addAll(fMenuNew, fMenuOpenProject, fMenuSave, fMenuSaveAs, fMenuClose, fMenuExit);
		mb.getMenus().add(fileMenu);

		// Functions for Menu: File
		fMenuNewProject.setOnAction(e -> newProject());
		fMenuNewFile.setOnAction(e -> createFile());
		fMenuOpenProject.setOnAction(e -> openProject());
		fMenuSave.setOnAction(e -> saveProject());
		fMenuSaveAs.setOnAction(e -> saveProjectAs());
		fMenuClose.setOnAction(e -> closeProject());
		fMenuExit.setOnAction(e -> Platform.exit());

		// Accelerators for Menu: File
		fMenuNew.setAccelerator(KeyCombination.keyCombination("shortcut+N"));
		fMenuOpenProject.setAccelerator(KeyCombination.keyCombination("shortcut+O"));
		fMenuSave.setAccelerator(KeyCombination.keyCombination("shortcut+S"));
		fMenuSaveAs.setAccelerator(KeyCombination.keyCombination("shortcut+shift+S"));

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

	protected abstract void newProject();

	protected abstract void createFile();

	protected abstract void openProject();

	protected abstract void saveProject();

	protected abstract void saveProjectAs();

	protected abstract void closeProject();

	protected abstract void projectSettings();

	protected abstract void synchronizeFiles();

	protected abstract void connectionSettings();

	protected abstract void help();
}
