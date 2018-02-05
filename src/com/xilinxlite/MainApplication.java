package com.xilinxlite;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import com.xilinxlite.communication.CommunicationMgr;
import com.xilinxlite.gui.DesignManager;
import com.xilinxlite.gui.functions.FunctionController;
import com.xilinxlite.gui.functions.LayoutController;
import com.xilinxlite.gui.functions.LocalOrRemoteMgr;
import com.xilinxlite.gui.functions.MenuBarMgr;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainApplication extends Application implements LayoutController {

	private static final Logger logger = Logger.getLogger(MainApplication.class.getName());

	private final String DEFAULT_TITLE = "Xilinx_Lite";

	private CommunicationMgr cmdMgr = new CommunicationMgr();
	private FunctionController fnControl = new FunctionController();

	private final File SETTINGS_FOLDER = new File("Xilinx_Lite/.settings");

	private BorderPane mainLayout;

	public static void main(String[] args) {
		launch(); // launches JavaFX
		logger.info("Application exited.");
	}

	/**
	 * Required by Application abstract class. Controls the overall layout of the
	 * main window.
	 */
	@Override
	public void start(Stage window) {

		// Enable logging to file
		setLogger();

		// Build SETTINGS_FOLDER
		SETTINGS_FOLDER.mkdirs();

		// Build window
		mainLayout = new BorderPane();
		mainLayout.setTop(new MenuBarMgr(cmdMgr, fnControl).getInstance());

		DesignManager dm = new LocalOrRemoteMgr(cmdMgr, SETTINGS_FOLDER, this);
		updateLayout(dm);

		window.setScene(new Scene(mainLayout, 800, 600));
		window.setTitle(DEFAULT_TITLE);
		window.show();

	}

	@Override
	public void updateLayout(DesignManager dm) {
		mainLayout.setCenter(dm.getLayout());
		fnControl.setUpdateTarget(dm);
	}

	/**
	 * Sets up logging to file
	 */
	private void setLogger() {
		// Get root logger
		Logger logger = Logger.getLogger("");
		FileHandler fh;
		try {
			fh = new FileHandler(SETTINGS_FOLDER + File.separator + "XilinxLite_logfile.log");
			logger.addHandler(fh);
			fh.setFormatter(new SimpleFormatter());
		} catch (IOException e) {
			logger.finer(e.getMessage());
		}

		// logger.setLevel(Level.FINE);
	}
}