package com.xilinxlite.gui;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

/**
 * GUI design template for project view.
 * 
 * @author Ong Hock Leng
 *
 */
public abstract class ProjectViewDesign implements DesignManager {

	@Override
	public Pane getLayout() {
		
		BorderPane layout = new BorderPane();
		
		layout.setTop(setSummaryTable());
		
		layout.setLeft(setDirectoryView());
		
		BorderPane layout1 = new BorderPane();
		
		layout.setRight(layout1);
		
		layout1.setTop(setButtonSet());
		
		layout1.setBottom(setMessageView());

		return layout;
	}

	protected abstract Pane setSummaryTable();

	protected abstract Pane setDirectoryView();

	protected abstract Pane setButtonSet();

	protected abstract Pane setMessageView();

}
