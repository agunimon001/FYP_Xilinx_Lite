package com.xilinxlite.gui;

import javafx.scene.layout.Pane;

/**
 * Implemented classes are to produce a layout Pane to be used in a Scene.
 * Get the Pane through calling getLayout().
 * 
 * @author Ong Hock Leng
 *
 */
public interface DesignManager {
	
	/**
	 * Returns a layout Pane as designed by the class.
	 * 
	 * @return layout in Pane (can be VBox, HBox, StackPane, etc.)
	 */
	public Pane getLayout();

}
