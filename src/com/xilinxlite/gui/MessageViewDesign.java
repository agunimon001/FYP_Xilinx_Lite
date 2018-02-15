package com.xilinxlite.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public abstract class MessageViewDesign implements DesignManager {

	@Override
	public Pane getLayout() {

		StackPane layout = new StackPane();
		layout.setPadding(new Insets(10));
		layout.setAlignment(Pos.CENTER);
		layout.setStyle("-fx-border-color: gray");

		// TabPane tp = new TabPane();
		// layout.getChildren().add(tp);
		//
		// Tab tab1 = new Tab("First");
		// tp.getTabs().add(tab1);
		// tab1.setClosable(false);
		// TextArea ta1 = new TextArea();
		// tab1.setContent(value);
		// ta1.setText("Hello World");
		//
		// Tab tab2 = new Tab("Second");
		// TextFlow textFlow = new TextFlow();
		// textFlow.

		return layout;
	}

}
