package com.xilinxlite.gui;

import java.io.File;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

public abstract class ProjectSettingsDesign {

	protected Stage window;

	private Label projectNameLabel = new Label("Project Name");
	private Label workingDirectoryLabel = new Label("Working Directory");
	private Label familyLabel = new Label("Family");
	private Label deviceLabel = new Label("Device");
	private Label packageLabel = new Label("Package");
	private Label speedLabel = new Label("Speed");
	private Label topLevelSourceTypeLabel = new Label("Top-Level Source Type");
	private Label synthesisToolLabel = new Label("Synthesis Tool");
	private Label simulatorLabel = new Label("Simulator");
	private Label preferredLanguageLabel = new Label("Preferred Language");

	protected TextField projectNameField = new TextField();
	protected TextField workingDirectoryField = new TextField();
	protected Button directoryChooserBtn = new Button("Choose directory");
	protected ComboBox<String> familyField = new ComboBox<>();
	protected ComboBox<String> deviceField = new ComboBox<>();
	protected ComboBox<String> packageField = new ComboBox<>();
	protected ComboBox<String> speedField = new ComboBox<>();
	protected ComboBox<String> topLevelSourceTypeField = new ComboBox<>();
	protected ComboBox<String> synthesisToolField = new ComboBox<>();
	protected ComboBox<String> simulatorField = new ComboBox<>();
	protected ComboBox<String> preferredLanguageField = new ComboBox<>();

	protected Button defaultBtn = new Button("Default");
	protected Button saveBtn = new Button("Save and close");
	protected Button closeBtn = new Button("Close");

	public void launch() {
		initialize();

		window = new Stage();

		int row = 0;

		VBox layout = new VBox();
		layout.setAlignment(Pos.TOP_CENTER);
		layout.setPadding(new Insets(10));

		GridPane layout1 = new GridPane();
		layout.getChildren().add(layout1);
		layout1.setAlignment(Pos.TOP_CENTER);
		layout1.setPadding(new Insets(20));
		layout1.setVgap(10);
		layout1.setHgap(10);

		directoryChooserBtn.setOnAction(e -> directoryChooser());
		familyField.setOnAction(e -> loadFamily());
		deviceField.setOnAction(e -> loadDevice());
		packageField.setOnAction(e -> loadPackage());
		speedField.setOnAction(e -> loadSpeed());
		topLevelSourceTypeField.setOnAction(e -> loadTopLevelSourceType());
		synthesisToolField.setOnAction(e -> loadSynthesisTool());
		simulatorField.setOnAction(e -> loadSimulator());
		preferredLanguageField.setOnAction(e -> loadPreferredLanguage());

		layout1.add(projectNameLabel, 0, row);
		layout1.add(new Label(":"), 1, row);
		layout1.add(projectNameField, 2, row++);

		layout1.add(workingDirectoryLabel, 0, row);
		layout1.add(new Label(":"), 1, row);
		layout1.add(workingDirectoryField, 2, row);
		layout1.add(directoryChooserBtn, 3, row++);

		layout1.add(familyLabel, 0, row);
		layout1.add(new Label(":"), 1, row);
		layout1.add(familyField, 2, row++);

		layout1.add(deviceLabel, 0, row);
		layout1.add(new Label(":"), 1, row);
		layout1.add(deviceField, 2, row++);

		layout1.add(packageLabel, 0, row);
		layout1.add(new Label(":"), 1, row);
		layout1.add(packageField, 2, row++);

		layout1.add(speedLabel, 0, row);
		layout1.add(new Label(":"), 1, row);
		layout1.add(speedField, 2, row++);

		layout1.add(topLevelSourceTypeLabel, 0, row);
		layout1.add(new Label(":"), 1, row);
		layout1.add(topLevelSourceTypeField, 2, row++);

		layout1.add(synthesisToolLabel, 0, row);
		layout1.add(new Label(":"), 1, row);
		layout1.add(synthesisToolField, 2, row++);

		layout1.add(simulatorLabel, 0, row);
		layout1.add(new Label(":"), 1, row);
		layout1.add(simulatorField, 2, row++);

		layout1.add(preferredLanguageLabel, 0, row);
		layout1.add(new Label(":"), 1, row);
		layout1.add(preferredLanguageField, 2, row++);

		HBox layout2 = new HBox(10);
		layout.getChildren().add(layout2);
		layout2.setAlignment(Pos.CENTER);
		layout2.setPadding(new Insets(10));
		layout2.getChildren().addAll(defaultBtn, saveBtn, closeBtn);

		defaultBtn.setOnAction(e -> setDefault());
		saveBtn.setOnAction(e -> save());
		closeBtn.setOnAction(e -> close());

		Scene scene = new Scene(layout);

		window.setScene(scene);
		window.setTitle("Project Settings");
		window.initModality(Modality.APPLICATION_MODAL);
		window.showAndWait();
	}

	private void directoryChooser() {
		DirectoryChooser dc = new DirectoryChooser();
		dc.setTitle("Select working directory...");
		File file = dc.showDialog(new Stage());
		if (file != null) {
			workingDirectoryField.setText(file.getAbsolutePath());
		}
	}

	/**
	 * Initializes functionalities
	 */
	protected abstract void initialize();

	/**
	 * Loads Family data
	 */
	protected abstract void loadFamily();

	/**
	 * Loads Device data
	 */
	protected abstract void loadDevice();

	/**
	 * Loads Package data
	 */
	protected abstract void loadPackage();

	/**
	 * Loads Speed data
	 */
	protected abstract void loadSpeed();

	/**
	 * Loads Top-Level Source Type data
	 */
	protected abstract void loadTopLevelSourceType();

	/**
	 * Loads Synthesis Tool data
	 */
	protected abstract void loadSynthesisTool();

	/**
	 * Loads Simulator data
	 */
	protected abstract void loadSimulator();

	/**
	 * Loads Preferred Language data
	 */
	protected abstract void loadPreferredLanguage();

	/**
	 * Sets default values to all fields
	 */
	protected abstract void setDefault();

	/**
	 * Saves settings and closes window
	 */
	protected abstract void save();

	/**
	 * Closes window without saving settings
	 */
	protected abstract void close();
}
