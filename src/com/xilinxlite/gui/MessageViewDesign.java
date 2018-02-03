package com.xilinxlite.gui;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public abstract class MessageViewDesign implements DesignManager {

	@Override
	public Pane getLayout() {

		HBox layout = new HBox();

		return layout;
	}

}
