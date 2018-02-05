package com.xilinxlite.gui.functions;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

class PropertiesController {

	private static Logger logger = Logger.getLogger(PropertiesController.class.getName());

	private static PropertiesController instance;

	private Properties props = new Properties();
	private File config = null;

	static final String KEY_XTCLSH = "local.xtclsh";
	static final String KEY_WD = "local.wd";

	enum KEY {
		KEY_XTCLSH("local.xtclsh"), KEY_WD("local.wd");

		private String value;

		KEY(String value) {
			this.value = value;
		}

		@Override
		public String toString() {
			return this.value;
		}
	}

	/**
	 * Constructor. Not exposed to outside class.
	 * 
	 * @param property_file
	 */
	private PropertiesController(File property_file) {
		this.config = property_file;
		try (FileReader reader = new FileReader(this.config)) {
			props.load(reader);
		} catch (IOException e) {
			logger.log(Level.WARNING, "Problem reading properties file. Will trigger if first time.", e);
		}
	}

	/**
	 * Singleton. Gets instance of PropertiesController.
	 * 
	 * @param file To contain filepath to the properties file (existing or new)
	 * @return Instance of PropertiesController
	 */
	static PropertiesController load(File file) {
		instance = new PropertiesController(file);

		return instance;
	}

	/**
	 * Singleton. Gets instance of pre-loaded of PropertiesController.
	 * 
	 * @return Existing instance of PropertiesController; null if otherwise
	 */
	static PropertiesController load() {
		return instance;
	}

	/**
	 * Puts property with new value. Remember to call save().
	 * 
	 * @param key
	 *            From enum KEY
	 * @param value
	 *            New value for key
	 */
	void put(KEY key, String value) {
		props.put(key.toString(), value);
	}

	/**
	 * Gets value for Key key
	 * 
	 * @param key
	 *            From enum KEY
	 * @return Value of key; null if missing
	 */
	String get(KEY key) {
		return props.getProperty(key.toString());
	}

	/**
	 * Saves running properties.
	 * 
	 * @return True if saved properly; false if otherwise.
	 */
	boolean save() {
		try (FileWriter writer = new FileWriter(config)) {
			props.store(writer, "Storing for local connection");
			return true;
		} catch (IOException e) {
			logger.log(Level.WARNING, "Cannot write to config file: " + config.getAbsolutePath(), e);
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Alert");
			alert.setHeaderText("Unable to save properties");
			alert.setContentText("Don't worry, you can still use this program.");
			return false;
		}
	}

}
