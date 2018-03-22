package com.xilinxlite.gui.functions;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.xilinxlite.communication.CommunicationMgr;
import com.xilinxlite.gui.LocalSettingDesign;
import com.xilinxlite.gui.functions.PropertiesController.KEY;

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

	private File workingDirectoryPath;
	private File settingsFolderPath;
	private String scriptPath = "";
	private File config;

	private PropertiesController props;

	/**
	 * Sets up LocalSettingMgr instance. Remember to call launch().
	 * 
	 * @param cmdMgr
	 *            Reference to CommunicationMgr
	 * @param settingsFolderPath
	 *            Path for Settings folder
	 * @param workingDirectoryPath
	 *            Path for desired working directory
	 */
	public LocalSettingMgr(CommunicationMgr cmdMgr, File settingsFolderPath, File workingDirectoryPath) {
		this.cmdMgr = cmdMgr;
		this.settingsFolderPath = settingsFolderPath;
		this.workingDirectoryPath = workingDirectoryPath;
		this.config = new File(this.settingsFolderPath + File.separator + "XilinxLite_config.properties");
		this.props = PropertiesController.load(this.config);
	}

	/**
	 * Function that is first called in launch(). Gets saved properties from last
	 * use.
	 */
	@Override
	protected void initialize() {
		logger.log(Level.INFO, "Initializing LocalSetting");

		File script = new File(settingsFolderPath.getAbsolutePath() + File.separator + "script.tcl");
		scriptPath = script.getAbsolutePath();

		if (!script.exists()) {
			logger.log(Level.INFO, "Adding script...");
			URL internalScriptURL = this.getClass().getResource("/com/xilinxlite/script.tcl");
			try (InputStream stream = internalScriptURL.openStream()) {
				Files.copy(stream, script.toPath());
				stream.close();
				logger.log(Level.INFO, "Script added.");
			} catch (IOException e) {
				logger.log(Level.SEVERE, "Script not created", e);
			}
		}
		
		script = new File(settingsFolderPath.getAbsolutePath() + File.separator + "isim_script.tcl");
		
		if (!script.exists()) {
			logger.log(Level.INFO, "Adding script...");
			URL internalScriptURL = this.getClass().getResource("/com/xilinxlite/isim_script.tcl");
			try (InputStream stream = internalScriptURL.openStream()) {
				Files.copy(stream, script.toPath());
				stream.close();
				logger.log(Level.INFO, "Script added.");
			} catch (IOException e) {
				logger.log(Level.SEVERE, "Script not created", e);
			}
		}

		String value = props.get(KEY.KEY_XTCLSH);
		xtclshPathField.setText(value != null ? value : "");

		value = props.get(KEY.KEY_WD);
		workingDirectoryField.setText(value != null ? value : workingDirectoryPath.getAbsolutePath());

		logger.log(Level.INFO, "Initializing complete.");
	}

	/**
	 * Tests local connection. Saves settings if pass and proceed to close window.
	 * Main window is to proceed thereafter.
	 */
	@Override
	protected void save(Stage window) {
		logger.log(Level.INFO, "Saving LocalSetting settings");
		if (cmdMgr.localConnection(xtclshPathField.getText(), scriptPath, workingDirectoryField.getText())) {

			logger.log(Level.INFO, "Connection established. Now saving properties...");

			props.put(KEY.KEY_XTCLSH, xtclshPathField.getText());
			props.put(KEY.KEY_WD, workingDirectoryField.getText());

			props.save();

			window.close();

			logger.log(Level.INFO, "Properties saved.");
		} else {
			logger.log(Level.WARNING, "Connection not established. xtclshPath: " + xtclshPathField.getText()
					+ ", workingDirectory: " + workingDirectoryField.getText());
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
