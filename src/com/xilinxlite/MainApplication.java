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
		BorderPane layout = new BorderPane();
		layout.setTop(MainGUI.setMenuBar());
		layout.setLeft(MainGUI.setProjectExplorer());

		window.setScene(new Scene(layout, 900, 600));
		window.show();
	}
}