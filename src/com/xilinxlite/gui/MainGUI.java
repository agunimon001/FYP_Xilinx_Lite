package com.xilinxlite.gui;

import com.xilinxlite.bean.BeanFactory;
import com.xilinxlite.bean.BeanInstantiationError;
import com.xilinxlite.gui.functions.MenuMgr;

import javafx.application.Platform;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.Pane;

public class MainGUI {

	public static MenuBar setMenuBar() throws BeanInstantiationError {
		// BeanFactory
		MenuMgr menuMgr = (MenuMgr) BeanFactory.getInstance().getBean("menuMgr");
		
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
		fMenuNewProject.setOnAction(e -> menuMgr.newProject());
		fMenuNewFile.setOnAction(e -> menuMgr.createFile());
		fMenuOpenProject.setOnAction(e -> menuMgr.openProject());
		fMenuSave.setOnAction(e -> menuMgr.saveProject());
		fMenuSaveAs.setOnAction(e -> menuMgr.saveProjectAs());
		fMenuClose.setOnAction(e -> menuMgr.closeProject());
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
		pMenuSettings.setOnAction(e -> menuMgr.projectSettings());
		
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
		rMenuSync.setOnAction(e -> menuMgr.synchronizeFiles());
		rMenuConnection.setOnAction(e -> menuMgr.connectionSettings());
		
		// Accelerators for Menu: Remote
		
		return mb;
	}
	
	public static Pane setSummary() {
		System.err.println("MainGUI.setSummary() not implemenet.");
		return null;
	}
	
	public static Pane setProjectExplorer() {
		System.err.println("MainGUI.setProjectExplorer() not implemented.");
		return null;
	}
}
