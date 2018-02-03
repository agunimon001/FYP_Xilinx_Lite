package com.xilinxlite.gui;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

/**
 * GUI design for viewing project directory/content.
 * 
 * @author Ong Hock Leng
 *
 */
public abstract class DirectoryViewDesign implements DesignManager {

	@Override
	public Pane getLayout() {
		HBox layout = new HBox();

		return layout;
	}

}
