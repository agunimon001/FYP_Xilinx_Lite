package com.xilinxlite.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * GUI design for viewing project directory/content.
 * 
 * @author Ong Hock Leng
 *
 */
public abstract class DirectoryViewDesign implements DesignManager {

	protected TreeView<Object> treeView = new TreeView<>();
	protected TreeItem<Object> root = new TreeItem<>("root");

	protected Button addFile = new Button("Add");
	protected Button removeFile = new Button("Remove Selected");
	protected Button refreshBtn = new Button("Refresh");
	protected Button topModuleBtn = new Button("Set Top Module");

	private Label heading = new Label("Project Explorer");

	@Override
	public Pane getLayout() {
		// Initialize design with required functionalities
		initialize();

		// Layout container
		BorderPane layout = new BorderPane();
		layout.setPadding(new Insets(10));
		layout.setStyle("-fx-border-color: gray");
		layout.setPrefWidth(250);

		// Heading
		VBox layout1 = new VBox(10);
		layout.setTop(layout1);
		layout1.getChildren().addAll(heading, new Separator());

		// Set TreeView with empty TreeItem root
		layout.setCenter(treeView);
		treeView.setRoot(root);
		treeView.setShowRoot(false);

		// Set buttons group
		VBox layout2 = new VBox(10);
		layout.setBottom(layout2);

		HBox layout2a = new HBox(10);
		layout2.getChildren().add(layout2a);
		layout2a.getChildren().addAll(addFile, removeFile);
		layout2a.setAlignment(Pos.CENTER);
		layout2a.setPadding(new Insets(10));

		addFile.setOnAction(e -> addFile());
		removeFile.setOnAction(e -> removeFile());

		HBox layout2b = new HBox(10);
		layout2.getChildren().add(layout2b);
		layout2b.getChildren().addAll(refreshBtn, topModuleBtn);
		layout2b.setAlignment(Pos.CENTER);
		layout2b.setPadding(new Insets(10));

		refreshBtn.setOnAction(e -> refresh());
		topModuleBtn.setOnAction(e -> setTopModule());

		return layout;
	}

	/**
	 * Initial settings to layout
	 */
	protected abstract void initialize();

	/**
	 * Added file to project
	 */
	protected abstract void addFile();

	/**
	 * Remove selected file
	 */
	protected abstract void removeFile();

	/**
	 * Refreshes tree
	 */
	protected abstract void refresh();

	/**
	 * Sets top module
	 */
	protected abstract void setTopModule();
}
