package com.xilinxlite.communication;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Manages communications between program and local/remote copy of Xilinx ISE.
 * 
 * @author Ong Hock Leng
 *
 */
public class CommunicationMgr {

	private static final Logger logger = Logger.getLogger(CommunicationMgr.class.getName());

	private Commands cmd = null;

	/**
	 * Sets up connection to local Xilinx ISE.
	 * 
	 * @param xtclshPath
	 * @param tclScriptPath
	 * @param workingDirectory
	 * @return True if connection is live; false otherwise.
	 */
	public boolean localConnection(String xtclshPath, String tclScriptPath, String workingDirectory) {
		if (cmd != null) {
			logger.info("Connection is live. Close connection first.");
			return false;
		}

		try {
			cmd = new XtclshCommands(xtclshPath, tclScriptPath, workingDirectory);
			return ((XtclshCommands) cmd).testConnection();
		} catch (FileNotFoundException e) {
			logger.log(Level.WARNING, "Error(s) in provided parameters.", e);
			return false;
		}
	}

	/**
	 * Incomplete...
	 * 
	 * @return
	 */
	public boolean remoteConnection() {
		if (cmd != null) {
			logger.info("Connection is live. Close connection first.");
			return false;
		}

		// Pseudo command
		cmd = new SshCommands();
		return false;
	}

	/**
	 * Close existing connection.
	 * 
	 * @return True if connection is closed successfully; false if otherwise.
	 */
	public boolean closeConnection() {
		if (cmd instanceof XtclshCommands) {
			cmd = null;
		} else if (cmd instanceof SshCommands) {
			// close possible ssh connection
		}

		return cmd == null;
	}

	public boolean newProject(String projectName) {
		return cmd.newProject(projectName);
	}

	public boolean openProject(String projectName) {
		return cmd.openProject(projectName);
	}

	public String getProjectName() {
		return cmd.getProjectName();
	}

	public void closeProject() {
		cmd.closeProject();
	}

	public Map<String, String> setAttributes(Map<String, String> attributes) {
		return cmd.setAttributes(attributes);
	}

	public Map<String, String> getAttributes() {
		return cmd.getAttributes();
	}

	public Map<String, String> setFamily(String family) {
		return cmd.setFamily(family);
	}

	public Map<String, String> getFamily() {
		return cmd.getFamily();
	}

	public Map<String, String> setDevice(String device) {
		return cmd.setDevice(device);
	}

	public Map<String, String> getDevice() {
		return cmd.getDevice();
	}

	public Map<String, String> setPackage(String _package) {
		return cmd.setPackage(_package);
	}

	public Map<String, String> getPackage() {
		return cmd.getPackage();
	}

	public Map<String, String> setSpeed(String speed) {
		return cmd.setSpeed(speed);
	}

	public Map<String, String> getSpeed() {
		return cmd.getSpeed();
	}

	public Map<String, String> setTopSourceType(String type) {
		return cmd.setTopSourceType(type);
	}

	public Map<String, String> getTopSourceType() {
		return cmd.getTopSourceType();
	}

	public Map<String, String> setSynthesis(String synthesis) {
		return cmd.setSynthesis(synthesis);
	}

	public Map<String, String> getSynthesis() {
		return cmd.getSynthesis();
	}

	public Map<String, String> setSimulator(String simulator) {
		return cmd.setSimulator(simulator);
	}

	public Map<String, String> getSimulator() {
		return cmd.getSimulator();
	}

	public Map<String, String> setPreferredLanguage(String language) {
		return cmd.setPreferredLanguage(language);
	}

	public Map<String, String> getPreferredLanguage() {
		return cmd.getPreferredLanguage();
	}

	public Map<String, String> setMessageFilter(boolean filter) {
		return cmd.setMessageFilter(filter);
	}

	public Map<String, String> getMessageFilter() {
		return cmd.getMessageFilter();
	}

	public boolean addFile(String filename) {
		return cmd.addFile(filename);
	}

	public List<String> getFiles() {
		return cmd.getFiles();
	}

	public void synthesize() {
		cmd.synthesize();
	}

	public List<String> getArchitectList() {
		return cmd.getArchitectList();
	}

	public Map<String, Map<String, String[]>> getArchitectData(String architect) {
		return cmd.getArchitectData(architect);
	}

}
