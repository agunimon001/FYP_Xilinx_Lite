package com.xilinxlite.gui;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * GUI design template for project view.
 * 
 * @author Ong Hock Leng
 *
 */
public abstract class ProjectViewDesign implements DesignManager {

	@Override
	public Pane getLayout() {
		VBox layout = new VBox(20);

		HBox layout1 = new HBox(20);

		VBox layout1a = new VBox(20);

		layout1a.getChildren().addAll(setButtonSet(), setMessageView());

		layout1.getChildren().addAll(setDirectoryView(), layout1a);

		layout.getChildren().addAll(setSummaryTable(), layout1);

		return layout;
	}

	protected abstract Pane setSummaryTable();

	protected abstract Pane setDirectoryView();

	protected abstract Pane setButtonSet();

	protected abstract Pane setMessageView();

}
