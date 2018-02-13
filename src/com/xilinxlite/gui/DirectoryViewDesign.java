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
		HBox layout2 = new HBox(10);
		layout.setBottom(layout2);
		layout2.getChildren().addAll(addFile, removeFile);
		layout2.setAlignment(Pos.CENTER);
		layout2.setPadding(new Insets(10));

		return layout;
	}

	/**
	 * Initial settings to layout
	 */
	protected abstract void initialize();

}
