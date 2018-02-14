package com.xilinxlite.gui;

import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;

/**
 * GUI Design for selecting Local or Remote connection setting. Extend this
 * class to include functions required for the GUI.
 * 
 * @author Ong Hock Leng
 *
 */
public abstract class LocalOrRemoteDesign implements DesignManager {

	/**
	 * To be called to obtain a Pane for the GUI.
	 * 
	 * @return Pane class
	 */
	@Override
	public Pane getLayout() {
		Label prompt = new Label("Choose local or remote Xilinx:");
		Button btnLocal = new Button("Local");
		Button btnRemote = new Button("Remote");
		btnRemote.setDisable(true);	// remove when remote is implemented

		btnLocal.setOnAction(e -> setLocal());

		btnRemote.setOnAction(e -> setRemote());

		VBox main = new VBox(20);
		main.setAlignment(Pos.CENTER);
		TilePane buttons = new TilePane(Orientation.HORIZONTAL, 10, 10);
		main.getChildren().addAll(prompt, buttons);
		buttons.setAlignment(Pos.CENTER);
		buttons.getChildren().addAll(btnLocal, btnRemote);

		return main;
	}

	protected abstract void setLocal();

	protected abstract void setRemote();
}
