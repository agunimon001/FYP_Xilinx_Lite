package com.xilinxlite.communication;

import java.io.FileNotFoundException;
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

}
