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

		layout.setCenter(layout1);

		layout1.setTop(setButtonSet());

		layout1.setCenter(setMessageView());

		return layout;
	}

	/**
	 * Returns a Pane for SummaryTable. Used in getLayout().
	 * 
	 * @return Pane for SummaryTable
	 */
	protected abstract Pane setSummaryTable();

	/**
	 * Returns a Pane for DirectoryView. Used in getLayout().
	 * 
	 * @return Pane for DirectoryView
	 */
	protected abstract Pane setDirectoryView();

	/**
	 * Returns a Pane for ButtonSet. Used in getLayout().
	 * 
	 * @return Pane for ButtonSet
	 */
	protected abstract Pane setButtonSet();

	/**
	 * Returns a Pane for MessageView. Used in getLayout().
	 * 
	 * @return Pane for MessageView
	 */
	protected abstract Pane setMessageView();

}
