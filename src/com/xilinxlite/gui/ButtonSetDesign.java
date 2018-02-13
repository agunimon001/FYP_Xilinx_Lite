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

	@Override
	public Pane getLayout() {

		FlowPane layout = new FlowPane(20, 20);
		layout.setAlignment(Pos.CENTER_LEFT);
		layout.setPadding(new Insets(20));

		createProjectBtn.setOnAction(e -> createProject());
		openProjectBtn.setOnAction(e -> openProject());
		synthesizeBtn.setOnAction(e -> synthesize());

		layout.getChildren().addAll(createProjectBtn, openProjectBtn, synthesizeBtn);

		return layout;
	}

	/**
	 * Sets up functionalities (especially for the buttons).
	 */
	protected abstract void initialize();

	protected abstract void createProject();

	protected abstract void openProject();

	protected abstract void synthesize();

}
