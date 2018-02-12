package com.xilinxlite.gui;

import java.io.File;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * GUI design for viewing project directory/content.
 * 
 * @author Ong Hock Leng
 *
 */
public abstract class DirectoryViewDesign implements DesignManager {
	
	TreeView<File> tv = new TreeView<>();

	@Override
	public Pane getLayout() {
		VBox layout = new VBox(10);

		return layout;
	}
	
	public abstract void setView(TreeItem<File> item);

}
