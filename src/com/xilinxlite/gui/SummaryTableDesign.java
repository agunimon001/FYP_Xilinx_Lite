package com.xilinxlite.gui;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * GUI design for summary table.
 * 
 * @author Ong Hock Leng
 *
 */
public abstract class SummaryTableDesign implements DesignManager {

	private Label titleLabel = new Label("Summary Table");
	private Label projectTitleLabel = new Label("Project Title:");
	private Label connectionStatusLabel = new Label("Connection:");

	protected Label projectTitle = new Label();
	protected Label connectionStatus = new Label();

	@Override
	public Pane getLayout() {

		VBox layout = new VBox(20);
		layout.setAlignment(Pos.TOP_CENTER);
		layout.setStyle("-fx-border-color: grey");

//		titleLabel.setPadding(new Insets(30));

		FlowPane layout1 = new FlowPane(Orientation.HORIZONTAL, 30, 30);

		GridPane layout1a = new GridPane();
		layout1a.setMinWidth(250);

		layout1a.add(projectTitleLabel, 0, 0);
		layout1a.add(projectTitle, 1, 0);

		GridPane layout1b = new GridPane();
		layout1b.setMinWidth(150);

		layout1b.add(connectionStatusLabel, 0, 0);
		layout1b.add(connectionStatus, 1, 0);

		layout1.getChildren().addAll(layout1a, layout1b);

		layout.getChildren().addAll(titleLabel, layout1);

		return layout;
	}

	protected abstract void updateProjectTitle();

	protected abstract void updateConnectionStatus();

}
