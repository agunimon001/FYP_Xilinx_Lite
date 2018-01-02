package com.xilinxlite;

import com.xilinxlite.controller.BeanFactory;
import com.xilinxlite.controller.BeanInstantiationError;
import com.xilinxlite.controller.FileMgr;
import com.xilinxlite.model.ProjectSettings;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainApplication extends Application {
	/**
	 * Project settings (singleton)
	 */
	private ProjectSettings projectSettings = ProjectSettings.getInstance();
	
	/**
	 * Main method
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		launch(); // launches JavaFX
		System.out.println("\n-- Program terminated --");
	}

	/**
	 * Required by Application abstract class
	 */
	@Override
	public void start(Stage window) throws Exception {
		// Setup initialized program
		BorderPane rootNode = new BorderPane();
		setupMenuBar(rootNode);

		// Show scene
		Scene myScene = new Scene(rootNode, 800, 600);
		window.setScene(myScene);
		window.show();
	}

	/**
	 * Setup initial program window
	 * 
	 * @param window
	 * @throws BeanInstantiationError 
	 */
	private void setupMenuBar(BorderPane pane) throws BeanInstantiationError {
		// BeanFactory
		BeanFactory beanFactory = BeanFactory.getInstance();

		// Menu Bar
		MenuBar mb = new MenuBar();

		///////////////////////////////////////////
		// Menu: File
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

		// Get bean for Menu: File
		FileMgr fileMgr = (FileMgr) beanFactory.getBean("fileMgr");

		// Functions for Menu: File
		fMenuNewProject.setOnAction(e -> projectSettings = fileMgr.newProject());
		fMenuNewFile.setOnAction(e -> fileMgr.createFile());
		fMenuOpenProject.setOnAction(e -> fileMgr.openProject());
		fMenuSave.setOnAction(e -> fileMgr.saveProject());
		fMenuSaveAs.setOnAction(e -> fileMgr.saveProjectAs());
		fMenuClose.setOnAction(e -> fileMgr.closeProject());
		fMenuExit.setOnAction(e -> Platform.exit());

		// Accelerators for Menu: File
		fMenuNew.setAccelerator(KeyCombination.keyCombination("shortcut+N"));
		fMenuOpenProject.setAccelerator(KeyCombination.keyCombination("shortcut+O"));
		fMenuSave.setAccelerator(KeyCombination.keyCombination("shortcut+S"));
		fMenuSaveAs.setAccelerator(KeyCombination.keyCombination("shortcut+shift+S"));

		///////////////////////////////////////////
		// Menu: Project
		Menu projectMenu = new Menu("Project");
		MenuItem pMenuSettings = new MenuItem("Project Settings");
		
		// Get Bean
		ProjectMgr projectMgr = (ProjectMgr) beanFactory.getBean("projectMgr");
		
		// Set Menu Bar to scene
		pane.setTop(mb);
	}

}