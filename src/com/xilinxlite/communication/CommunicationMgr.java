package com.xilinxlite.communication;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Manages communications between program and local/remote copy of Xilinx ISE.
 * Uses Facade and Proxy Pattern.
 * 
 * @author Ong Hock Leng
 *
 */
public class CommunicationMgr implements Commands {

	private static final Logger logger = Logger.getLogger(CommunicationMgr.class.getName());

	private Commands cmd = null;

	/**
	 * Enumeration used by CommunicationMgr.
	 * 
	 * @author Ong Hock Leng
	 *
	 */
	public enum ConnectionType {
		NONE, LOCAL, REMOTE
	}

	/**
	 * Sets up connection to local Xilinx ISE.
	 * 
	 * @param xtclshPath
	 *            File path of xtclsh.exe
	 * @param tclScriptPath
	 *            File path of TCL script
	 * @param workingDirectory
	 *            File path of working directory
	 * @return True if connection is live; false otherwise.
	 */
	public boolean localConnection(String xtclshPath, String tclScriptPath, String workingDirectory) {
		// Test for existing connection; return false if true
		if (cmd != null) {
			logger.info("Connection is live. Close connection first.");
			return false;
		}

		// Try to establish local connection
		try {
			logger.info("Establishing local connection...");
			cmd = new XtclshCommands(xtclshPath, tclScriptPath, workingDirectory);
			boolean flag = ((XtclshCommands) cmd).testConnection();
			logger.log(Level.CONFIG, "Connection status: " + flag);
			return flag;
		} catch (FileNotFoundException e) {
			logger.log(Level.WARNING, "Error(s) in provided parameters.", e);
			return false;
		}
	}

	/**
	 * Incomplete...
	 * 
	 * @return True if connection is live; false if otherwise.
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
	 * Gets connection type (Enum {LOCAL, REMOTE, NONE}).
	 * 
	 * @return ConnectionType (Enum)
	 */
	public ConnectionType getConnectiontype() {
		if (cmd instanceof XtclshCommands) {
			return ConnectionType.LOCAL;
		} else if (cmd instanceof SshCommands) {
			return ConnectionType.REMOTE;
		} else {
			return ConnectionType.NONE;
		}
	}

	/**
	 * Check if connection is local.
	 * 
	 * @return True if local connection; false if otherwise.
	 */
	public boolean isLocal() {
		return cmd instanceof XtclshCommands;
	}

	/**
	 * Check if connection is remote.
	 * 
	 * @return True if remote connection; false if otherwise.
	 */
	public boolean isRemote() {
		return cmd instanceof SshCommands;
	}

	/**
	 * Check if there is no connection.
	 * 
	 * @return True if no conection; false if otherwise.
	 */
	public boolean isNone() {
		return cmd == null;
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

	@Override
	public boolean newProject(String projectName) {
		logger.fine("Creating project " + projectName);
		return cmd.newProject(projectName);
	}

	@Override
	public boolean openProject(String projectName) {
		logger.fine("Opening project " + projectName);
		return cmd.openProject(projectName);
	}

	@Override
	public String getProjectName() {
		return cmd.getProjectName();
	}

	@Override
	public void closeProject() {
		logger.fine("Closing project");
		cmd.closeProject();
	}

	@Override
	public boolean setWorkingDirectory(String newWorkingDirectoryPath) {
		logger.fine("Updating working directory");
		return cmd.setWorkingDirectory(newWorkingDirectoryPath);
	}

	@Override
	public String getWorkingDirectory() {
		return cmd.getWorkingDirectory();
	}

	@Override
	public Map<String, String> setAttributes(Map<String, String> attributes) {
		logger.fine("Setting multiple attributes");
		return cmd.setAttributes(attributes);
	}

	@Override
	public Map<String, String> getAttributes() {
		logger.fine("Getting attributes");
		return cmd.getAttributes();
	}

	@Override
	public String setFamily(String family) {
		logger.fine("Setting family attribute: " + family);
		return cmd.setFamily(family);
	}

	@Override
	public String getFamily() {
		return cmd.getFamily();
	}

	@Override
	public String setDevice(String device) {
		logger.fine("Setting device attribute: " + device);
		return cmd.setDevice(device);
	}

	@Override
	public String getDevice() {
		return cmd.getDevice();
	}

	@Override
	public String setPackage(String _package) {
		logger.fine("Setting package attribute: " + _package);
		return cmd.setPackage(_package);
	}

	@Override
	public String getPackage() {
		return cmd.getPackage();
	}

	@Override
	public String setSpeed(String speed) {
		logger.fine("Setting speed attribute: " + speed);
		return cmd.setSpeed(speed);
	}

	@Override
	public String getSpeed() {
		return cmd.getSpeed();
	}

	@Override
	public String setTopSourceType(String type) {
		logger.fine("Setting top source type attribute: " + type);
		return cmd.setTopSourceType(type);
	}

	@Override
	public String getTopSourceType() {
		return cmd.getTopSourceType();
	}

	@Override
	public String setSynthesis(String synthesis) {
		logger.fine("Setting synthesis attribute: " + synthesis);
		return cmd.setSynthesis(synthesis);
	}

	@Override
	public String getSynthesis() {
		return cmd.getSynthesis();
	}

	@Override
	public String setSimulator(String simulator) {
		logger.fine("Setting simulator attribute: " + simulator);
		return cmd.setSimulator(simulator);
	}

	@Override
	public String getSimulator() {
		return cmd.getSimulator();
	}

	@Override
	public String setPreferredLanguage(String language) {
		logger.fine("Setting preferred language attribute: " + language);
		return cmd.setPreferredLanguage(language);
	}

	@Override
	public String getPreferredLanguage() {
		return cmd.getPreferredLanguage();
	}

	@Override
	public boolean setMessageFilter(boolean filter) {
		logger.fine("Setting message filter attribute: " + filter);
		return cmd.setMessageFilter(filter);
	}

	@Override
	public boolean getMessageFilter() {
		return cmd.getMessageFilter();
	}

	@Override
	public boolean addFile(String filename) {
		logger.fine("Adding file: " + filename);
		return cmd.addFile(filename);
	}

	@Override
	public boolean removeFile(String filename) {
		logger.fine("Removing file: " + filename);
		return cmd.removeFile(filename);
	}

	@Override
	public List<String> getFiles() {
		return cmd.getFiles();
	}

	@Override
	public void synthesize() {
		logger.fine("Running synthesis");
		cmd.synthesize();
	}

	@Override
	public List<String> getArchitectList() {
		logger.fine("Getting architect list");
		return cmd.getArchitectList();
	}

	@Override
	public Map<String, Map<String, String[]>> getArchitectData(String architect) {
		logger.fine("Getting data for architect: " + architect);
		return cmd.getArchitectData(architect);
	}

	@Override
	public void simulate(String moduleName) {
		int index = moduleName.indexOf('/');
		if (index != -1) {
			moduleName = moduleName.substring(0, index);
		}
		logger.fine("Simulating for " + moduleName);
		cmd.simulate(moduleName);
	}

	@Override
	public String getTopModule() {
		logger.fine("Getting top module");
		return cmd.getTopModule();
	}

	@Override
	public List<String> getTopModules() {
		logger.fine("Getting list of modules for setting as top module");
		return cmd.getTopModules();
	}

	@Override
	public boolean setTopModule(String topModule) {
		logger.fine("Sets top module " + topModule);
		return cmd.setTopModule(topModule);
	}

	@Override
	public String getXtclshPath() {
		logger.fine("Getting xtclsh.exe path");
		return cmd.getXtclshPath();
	}

	@Override
	public void generatePrgFile() {
		logger.fine("Generating programming file");
		cmd.generatePrgFile();
	}

}
