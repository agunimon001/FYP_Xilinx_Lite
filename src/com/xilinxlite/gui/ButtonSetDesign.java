package com.xilinxlite.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;

/**
 * GUI design for button group.
 * 
 * @author Ong Hock Leng
 *
 */
public abstract class ButtonSetDesign implements DesignManager {

	protected Button createProjectBtn = new Button("Create Project");
	protected Button openProjectBtn = new Button("Open Project");
	protected Button synthesizeBtn = new Button("Synthesize");
	protected Button tbBtn = new Button("Generate Verilog Test Fixture");
	protected Button simulateBtn = new Button("Simulate");
	protected Button generatePrgFileBtn = new Button("Generate Programming File");
	protected Button filterBtn = new Button("Filter");

	@Override
	public Pane getLayout() {

		initialize();

		FlowPane layout = new FlowPane(20, 20);
		layout.setAlignment(Pos.CENTER_LEFT);
		layout.setPadding(new Insets(20));

		createProjectBtn.setOnAction(e -> createProject());
		openProjectBtn.setOnAction(e -> openProject());
		synthesizeBtn.setOnAction(e -> synthesize());
		tbBtn.setOnAction(e -> generateVerilogTestFixture());
		simulateBtn.setOnAction(e -> simulate());
		generatePrgFileBtn.setOnAction(e -> generatePrgFile());
		filterBtn.setOnAction(e -> filter());

		layout.getChildren().addAll(createProjectBtn, openProjectBtn, synthesizeBtn, tbBtn, simulateBtn,
				generatePrgFileBtn, filterBtn);

		return layout;
	}

	/**
	 * Sets up functionalities (especially for the buttons).
	 */
	protected abstract void initialize();

	/**
	 * Creates new project
	 */
	protected abstract void createProject();

	/**
	 * Opens existing project
	 */
	protected abstract void openProject();

	/**
	 * Synthesize currently opened project
	 */
	protected abstract void synthesize();
	
	/**
	 * Generates Verilog test fixtures
	 */
	protected abstract void generateVerilogTestFixture();

	/**
	 * Simulate currently opened project
	 */
	protected abstract void simulate();

	/**
	 * Generate Programming File
	 */
	protected abstract void generatePrgFile();

	/**
	 * Filters message. Used for synthesis report.
	 */
	protected abstract void filter();

}
