package com.xilinxlite;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import com.xilinxlite.communication.CommunicationMgr;
import com.xilinxlite.gui.DesignManager;
import com.xilinxlite.gui.functions.FunctionPack;
import com.xilinxlite.gui.functions.LayoutController;
import com.xilinxlite.gui.functions.LocalOrRemoteMgr;
import com.xilinxlite.gui.functions.MenuBarMgr;
import com.xilinxlite.gui.functions.UpdateController;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Main class of the project.
 * 
 * @author Ong Hock Leng
 *
 */
public class MainApplication extends Application implements LayoutController {

	private static final Logger logger = Logger.getLogger(MainApplication.class.getName());

	private final String DEFAULT_TITLE = "Xilinx_Lite";

	private final File SETTINGS_FOLDER = new File("Xilinx_Lite/.settings");
	private final File ROOT_FOLDER = SETTINGS_FOLDER.getParentFile();

	private CommunicationMgr cmdMgr = new CommunicationMgr();
	private UpdateController updater = new UpdateController();
	@SuppressWarnings("unused")
	private FunctionPack fnPack = FunctionPack.getInstance(cmdMgr, updater, ROOT_FOLDER);

	private BorderPane mainLayout;

	/**
	 * Launches program.
	 * 
	 * @param args
	 *            Extra parameters
	 */
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

		// Build window with menu bar
		mainLayout = new BorderPane();
		MenuBarMgr mbMgr = new MenuBarMgr();
		mainLayout.setTop(mbMgr.getMenubar());
		updater.setMenuBar(mbMgr);
		updater.update();

		// Instantiate LocalOrRemoteMgr and update to main window
		DesignManager dm = new LocalOrRemoteMgr(cmdMgr, SETTINGS_FOLDER, this);
		updateLayout(dm);

		// Set scene and show window
		window.setScene(new Scene(mainLayout, 800, 600));
		window.setTitle(DEFAULT_TITLE);
		window.show();
		window.setMinHeight(window.getHeight());
		window.setMinWidth(window.getWidth());

	}

	@Override
	public void updateLayout(DesignManager dm) {
		mainLayout.setCenter(dm.getLayout());
		updater.setUpdateTarget(dm);
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

		// Set logger level (Default: INFO)
		// logger.setLevel(Level.CONFIG);
	}
}