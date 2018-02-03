package com.xilinxlite.gui;

import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;

/**
 * GUI design for button group.
 * 
 * @author Ong Hock Leng
 *
 */
public abstract class ButtonSetDesign implements DesignManager {

	// TODO add buttons

	@Override
	public Pane getLayout() {

		TilePane layout = new TilePane();

		return layout;
	}

}
