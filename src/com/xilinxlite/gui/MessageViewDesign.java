package com.xilinxlite.gui;

import java.io.BufferedReader;
import java.io.File;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public abstract class MessageViewDesign implements DesignManager {

	protected TabPane tp;

	protected Tab logTab;
	protected TextArea logField;

	protected Tab fileTab;
	protected TextArea fileView;

	@Override
	public Pane getLayout() {

		initialize();

		StackPane layout = new StackPane();
		layout.setPadding(new Insets(10));
		layout.setAlignment(Pos.CENTER);
		layout.setStyle("-fx-border-color: gray");

		tp = new TabPane();
		layout.getChildren().add(tp);
		tp.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

		logTab = new Tab("Log");
		tp.getTabs().add(logTab);
		logTab.setClosable(false);

		logField = new TextArea();
		logTab.setContent(logField);
		logField.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		logField.setStyle("-fx-text-fill: black;");

		fileTab = new Tab("File");
		tp.getTabs().add(fileTab);
		fileTab.setClosable(false);

		fileView = new TextArea();
		fileTab.setContent(fileView);
		fileView.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		fileView.setStyle("-fx-text-fill: black;");
		
		
		return layout;
	}

	/*
	 * Initialize settings
	 */
	protected abstract void initialize();

	/**
	 * Appends output from BufferedReader r to the log field.
	 * 
	 * @param r
	 *            BufferedReader to read from
	 */
	protected abstract void appendLog(BufferedReader r);

	/**
	 * Clears the log
	 */
	protected abstract void clearLog();

	/**
	 * Displays file content in the file view.
	 * 
	 * @param file
	 *            File to be read
	 */
	protected abstract void viewFile(File file);

}
