package com.xilinxlite.gui;

import java.io.File;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Pop-up GUI Design for local connection settings. Launches a pop-up window
 * upon calling launch(). Extend this class to include the required functions.
 * 
 * @author Ong Hock Leng
 *
 */
public abstract class LocalSettingDesign {

	private final Label xtclshLabel = new Label("Path for 'xtclsh.exe':");
	private final Label workingDirectoryLabel = new Label("Working Directory:");

	protected TextField xtclshPathField = new TextField();
	protected TextField workingDirectoryField = new TextField(
			new File(System.getProperty("user.home") + File.separator + "Xilinx_Lite").getAbsolutePath());
	protected Label errorMsg = new Label();

	/**
	 * Called to launch the pop-up window.
	 */
	public void launch() {
		initialize();

		Stage window = new Stage();
		window.setTitle("Local Settings");
		window.initModality(Modality.APPLICATION_MODAL);

		xtclshPathField.setEditable(false);
		workingDirectoryField.setEditable(false);

		Button btnChooser1 = new Button("select directory");
		Button btnChooser2 = new Button("select directory");
		Button btnSave = new Button("Save and close");
		Button btnClose = new Button("Close without Saving");

		btnChooser1.setOnAction(e -> fileChooser(xtclshPathField));
		btnChooser2.setOnAction(e -> directoryChooser(workingDirectoryField));
		btnSave.setOnAction(e -> save(window));
		btnClose.setOnAction(e -> close(window));

		VBox layout = new VBox(20);
		layout.setAlignment(Pos.CENTER);

		GridPane layout1 = new GridPane();
		layout1.setAlignment(Pos.CENTER);
		layout1.setPadding(new Insets(20));
		layout1.setHgap(20);
		layout1.setVgap(20);
		layout1.add(xtclshLabel, 0, 0);
		layout1.add(xtclshPathField, 1, 0);
		layout1.add(btnChooser1, 2, 0);
		layout1.add(workingDirectoryLabel, 0, 1);
		layout1.add(workingDirectoryField, 1, 1);
		layout1.add(btnChooser2, 2, 1);

		TilePane layout2 = new TilePane(Orientation.HORIZONTAL);
		layout2.setAlignment(Pos.CENTER);
		layout2.setPadding(new Insets(20));
		layout2.getChildren().addAll(btnSave, btnClose);

		layout.getChildren().addAll(layout1, layout2);
		Scene scene = new Scene(layout);
		window.setScene(scene);
		window.showAndWait();

	}

	/**
	 * Selects a file. Limited to 'xtclsh.exe'.
	 * 
	 * @param txt
	 *            The TextField to update text with
	 */
	private void fileChooser(TextField txt) {
		FileChooser chooser = new FileChooser();
		chooser.getExtensionFilters().add(new ExtensionFilter("xtclsh", "xtclsh.exe"));
		File file = chooser.showOpenDialog(new Stage());
		if (file != null) {
			txt.setText(file.getAbsolutePath());
		}
	}

	/**
	 * Selects a folder.
	 * 
	 * @param txt
	 *            The TextField to update text with
	 */
	private void directoryChooser(TextField txt) {
		DirectoryChooser chooser = new DirectoryChooser();
		File file = chooser.showDialog(new Stage());
		if (file != null) {
			txt.setText(file.getAbsolutePath());
		}
	}

	/**
	 * Initializes functionalities
	 */
	abstract protected void initialize();

	/**
	 * Saves settings and closes window. Pass the window as argument.
	 * 
	 * @param window
	 *            Current window
	 */
	abstract protected void save(Stage window);

	/**
	 * Closes window. Pass the window as argument.
	 * 
	 * @param window
	 *            Current window
	 */
	abstract protected void close(Stage window);

}
