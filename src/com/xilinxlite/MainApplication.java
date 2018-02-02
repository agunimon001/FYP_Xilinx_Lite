package com.xilinxlite;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import com.xilinxlite.bean.BeanInstantiationError;
import com.xilinxlite.communication.CommunicationMgr;
import com.xilinxlite.gui.functions.LocalOrRemoteMgr;
import com.xilinxlite.gui.functions.MenuBarMgr;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainApplication extends Application {

	private static final Logger logger = Logger.getLogger(MainApplication.class.getName());

	private final String DEFAULT_TITLE = "Xilinx_Lite";

	private CommunicationMgr cmdMgr = new CommunicationMgr();

	public static void main(String[] args) {
		launch(); // launches JavaFX
		logger.info("Application exited.");
	}

	/**
	 * Required by Application abstract class. Controls the overall layout of the
	 * main window.
	 */
	@Override
	public void start(Stage window) throws BeanInstantiationError {

		// Enable logging to file
		setLogger();

		// Build window
		BorderPane mainLayout = new BorderPane();
		mainLayout.setTop(new MenuBarMgr().getInstance());

		mainLayout.setCenter(new LocalOrRemoteMgr(cmdMgr).getLayout());

		window.setScene(new Scene(mainLayout, 800, 600));
		window.setTitle(DEFAULT_TITLE);
		window.show();

	}

	/**
	 * Sets up logging to file
	 */
	private void setLogger() {
		String workingDirectory = System.getProperty("user.dir") + System.getProperty("file.separator");
		// Get root logger
		Logger logger = Logger.getLogger("");
		FileHandler fh;
		try {
			fh = new FileHandler(workingDirectory + "logfile.log");
			logger.addHandler(fh);
			fh.setFormatter(new SimpleFormatter());
		} catch (IOException e) {
			e.printStackTrace();
		}

		// logger.setLevel(Level.FINE);
	}
}