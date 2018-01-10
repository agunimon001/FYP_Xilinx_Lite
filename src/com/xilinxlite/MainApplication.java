package com.xilinxlite;

import com.xilinxlite.gui.MainGUI;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainApplication extends Application {
	/**
	 * Project settings (singleton)
	 */
	private ProjectSettings projectSettings = ProjectSettings.getInstance();
	
	/**
	 * Other Variables
	 */
	private final String DEFAULT_TITLE = "Xilinx Lite";

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
	 * Required by Application abstract class. Controls the overall layout of the
	 * main window.
	 */
	@Override
	public void start(Stage window) throws Exception {
		// Build window
		BorderPane mainLayout = new BorderPane();
		mainLayout.setTop(MainGUI.setMenuBar());
		mainLayout.setCenter(MainGUI.setSummary());
		BorderPane layout = new BorderPane();
		mainLayout.setBottom(layout);
		layout.setLeft(MainGUI.setProjectExplorer());

		window.setScene(new Scene(mainLayout, 800, 600));
		window.setTitle(DEFAULT_TITLE);
		window.show();
	}
}