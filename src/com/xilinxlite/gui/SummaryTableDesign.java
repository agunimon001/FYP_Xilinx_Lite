package com.xilinxlite.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * GUI design for summary table.
 * 
 * @author Ong Hock Leng
 *
 */
public abstract class SummaryTableDesign implements DesignManager {

	private Label titleLabel = new Label("Summary Table");
	private Label projectTitleLabel = new Label("Project Title: ");
	private Label connectionStatusLabel = new Label("Connection: ");
	private Label workingDirectoryLabel = new Label("Working Directory: ");
	private Label statusLabel = new Label("Status: ");

	protected Label projectTitle = new Label();
	protected Label connectionStatus = new Label();
	protected Label workingDirectory = new Label();
	protected Label status = new Label();

	@Override
	public Pane getLayout() {

		VBox layout = new VBox();
		layout.setFillWidth(true);
		layout.setStyle("-fx-border-color: gray");
		layout.setPadding(new Insets(10));
		layout.setAlignment(Pos.CENTER);
		layout.setMinWidth(450);

		layout.getChildren().add(titleLabel);
		titleLabel.setAlignment(Pos.CENTER);

		GridPane layout1 = new GridPane();
		layout.getChildren().add(layout1);
		layout1.setPadding(new Insets(10));
		ColumnConstraints col1 = new ColumnConstraints();
		col1.setPercentWidth(75);
		layout1.getColumnConstraints().addAll(col1);

		GridPane layout1a = new GridPane();
		layout1.add(layout1a, 0, 0);
		layout1a.setPadding(new Insets(10));
		layout1a.setAlignment(Pos.TOP_LEFT);
		layout1a.add(projectTitleLabel, 0, 0);
		layout1a.add(projectTitle, 1, 0);

		GridPane layout1b = new GridPane();
		layout1.add(layout1b, 1, 0);
		layout1b.setPadding(new Insets(10));
		layout1b.setAlignment(Pos.TOP_LEFT);
		layout1b.add(connectionStatusLabel, 0, 0);
		layout1b.add(connectionStatus, 1, 0);

		GridPane layout1c = new GridPane();
		layout1.add(layout1c, 0, 1);
		layout1c.setPadding(new Insets(10));
		layout1c.setAlignment(Pos.TOP_LEFT);
		layout1c.add(workingDirectoryLabel, 0, 0);
		layout1c.add(workingDirectory, 1, 0);

		GridPane layout1d = new GridPane();
		layout1.add(layout1d, 1, 1);
		layout1d.setPadding(new Insets(10));
		layout1d.setAlignment(Pos.TOP_LEFT);
		layout1d.add(statusLabel, 0, 0);
		layout1d.add(status, 1, 0);

		return layout;
	}

	/**
	 * Updates Project Title in SummaryTable
	 */
	protected abstract void updateProjectTitle();

	/**
	 * Updates Connection Status in SummaryTable
	 */
	protected abstract void updateConnectionStatus();
	
	/**
	 * Updates working directory in SummaryTable
	 */
	protected abstract void updateWorkingDirectory();
	
	/**
	 * Sets status message
	 * @param message new message
	 */
	protected abstract void setStatus(String message);

}
