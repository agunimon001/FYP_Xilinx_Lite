package com.xilinxlite.gui.functions;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.xilinxlite.communication.CommunicationMgr;
import com.xilinxlite.gui.LocalSettingDesign;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

/**
 * Function implementation for LocalSettingDesign GUI.
 * 
 * @author Ong Hock Leng
 *
 */
public class LocalSettingMgr extends LocalSettingDesign {

	private static final Logger logger = Logger.getLogger(LocalSettingMgr.class.getName());

	private CommunicationMgr cmdMgr = null;

	private String settingsFolderPath;
	private String scriptPath = "";
	private File config;
	private Properties props = null;
	private final String KEY_XTCLSH = "local.xtclsh";
	private final String KEY_WD = "local.wd";

	/**
	 * Sets up LocalSettingMgr instance. Remember to call launch().
	 * 
	 * @param cmdMgr
	 *            Reference to CommunicationMgr
	 * @param settingsFolderPath
	 *            Path for Settings folder
	 */
	public LocalSettingMgr(CommunicationMgr cmdMgr, String settingsFolderPath) {
		this.cmdMgr = cmdMgr;
		this.settingsFolderPath = settingsFolderPath;
		this.config = new File(this.settingsFolderPath + "/XilinxLite_config.properties");
	}

	/**
	 * Function that is first called in launch(). Gets saved properties from last
	 * use.
	 */
	@Override
	protected void initialize() {
		logger.entering("LocalSettingMgr", "initialize");

		File script = new File(settingsFolderPath + "/script.tcl");
		scriptPath = script.getAbsolutePath();

		if (!script.exists()) {
			URL internalScriptURL = this.getClass().getResource("/com/xilinxlite/script.tcl");
			try (InputStream stream = internalScriptURL.openStream()) {
				Files.copy(stream, script.toPath());
				stream.close();
			} catch (IOException e) {
				logger.log(Level.SEVERE, "Script not created", e);
			}
		}

		if (props == null)
			props = new Properties();
		try (FileReader reader = new FileReader(config)) {
			props.load(reader);

			xtclshPathField.setText(props.getProperty(KEY_XTCLSH, ""));

			workingDirectoryField.setText(props.getProperty(KEY_WD, System.getProperty("user.home") + "/Xilinx_Lite"));
		} catch (IOException e) {
			logger.warning("config file cannot be read");
		}
	}

	/**
	 * Tests local connection. Saves settings if pass and proceed to close window.
	 * Main window is to proceed thereafter.
	 */
	@Override
	protected void save(Stage window) {
		if (cmdMgr.localConnection(xtclshPathField.getText(), scriptPath, workingDirectoryField.getText())) {
			if (props == null) {
				props = new Properties();
			}

			props.setProperty(KEY_XTCLSH, xtclshPathField.getText());
			props.setProperty(KEY_WD, workingDirectoryField.getText());

			try (FileWriter writer = new FileWriter(config)) {
				props.store(writer, "Storing for local connection");
			} catch (IOException e) {
				logger.log(Level.WARNING, "Cannot write to config file: " + config.getAbsolutePath(), e);
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Alert");
				alert.setHeaderText("Unable to save properties");
				alert.setContentText("Don't worry, you can still use this program.");
			}

			window.close();
		} else {
			logger.warning("connection not established");
		}
	}

	/**
	 * Closes pop-up window and do nothing else.
	 */
	@Override
	protected void close(Stage window) {
		window.close();
	}

}
