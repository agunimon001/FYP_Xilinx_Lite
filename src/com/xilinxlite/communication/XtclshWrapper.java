package com.xilinxlite.communication;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Logger;

/**
 * Wrapper for xtclsh.exe of local copy of Xilinx ISE. The executable file runs
 * with the following format: "xtclsh.exe [script] [commands..]". This wrapper
 * is built around the three parameters given on construction. After each run(),
 * both input and error readers must be called again for reading as each run()
 * builds a new process. Different information has to be read from different
 * reader.
 * 
 * @author Ong Hock Leng
 *
 */
class XtclshWrapper {

	private static final Logger logger = Logger.getLogger(XtclshWrapper.class.getName());

	private String xtclshPath;
	private String tclScriptPath;
	private String workingDirectory;
	private Process p;

	/**
	 * Constructor hidden from outside use. Validation required before construction.
	 * 
	 * @param xtclshPath
	 * @param tclScriptPath
	 * @param workingDirectory
	 */
	private XtclshWrapper(String xtclshPath, String tclScriptPath, String workingDirectory) {
		this.xtclshPath = xtclshPath;
		this.tclScriptPath = tclScriptPath;
		this.workingDirectory = workingDirectory;
	}

	/**
	 * Validates all three parameters before construction. Throws exception if error
	 * building.
	 * 
	 * @param xtclshPath
	 * @param tclScriptPath
	 * @param workingDirectory
	 * @return XtclshWrapper
	 * @throws FileNotFoundException
	 */
	static XtclshWrapper getInstance(String xtclshPath, String tclScriptPath, String workingDirectory)
			throws FileNotFoundException {
		String errorMsg = "";

		// Validate xtclshPath
		if (!xtclshPath.endsWith("xtclsh.exe")) {
			errorMsg += "Program file is not xtclsh.exe: " + xtclshPath;
		} else if (!new File(xtclshPath).exists()) {
			errorMsg += xtclshPath + " not found.";
		}

		// Validate tclScriptPath
		if (!tclScriptPath.endsWith(".tcl")) {
			errorMsg += (errorMsg.isEmpty() ? "" : "\n") + "Script file not TCL file: " + tclScriptPath;
		} else if (!new File(tclScriptPath).exists()) {
			errorMsg += (errorMsg.isEmpty() ? "" : "\n") + tclScriptPath + " not found.";
		}

		// Validate workingDirectory
		File wd = new File(workingDirectory);
		if (!wd.exists()) {
			if (wd.mkdirs()) {
				logger.info("Working Directory \"" + workingDirectory + "\" created: " + workingDirectory);
			} else {
				errorMsg += (errorMsg.isEmpty() ? "" : "\n") + "Error creating working directory \"" + workingDirectory
						+ "\".";
			}
		}

		// Return instance if valid; else throw exception
		if (errorMsg.isEmpty()) {
			logger.config("Instantiating new XtclshWrapper");
			return new XtclshWrapper(xtclshPath, tclScriptPath, workingDirectory);
		} else {
			throw new FileNotFoundException(errorMsg);
		}
	}

	/**
	 * Accepts String array commands to be run. IOException is thrown if (1)
	 * xtclsh.exe is not given a valid path, or (2) TCL script path is not valid.
	 * 
	 * @param commands
	 * @throws IOException
	 */
	public void run(String... commands) throws IOException {
		// Build String array for ProcessBuilder use
		String[] arr = new String[commands.length + 2];
		arr[0] = xtclshPath;
		arr[1] = tclScriptPath;
		for (int i = 0; i < commands.length; i++) {
			arr[2 + i] = commands[i];
		}

		ProcessBuilder pb = new ProcessBuilder(arr);
		pb.directory(new File(workingDirectory));
		try {
			p = pb.start();
		} catch (IOException e) {
			logger.warning("XtclshWrapper: run error");
			throw e;
		}
	}

	/**
	 * Gets input reader for current process. Reads outputs as designed in TCL
	 * script.
	 * 
	 * @return
	 */
	public BufferedReader getInputReader() {
		return new BufferedReader(new InputStreamReader(p.getInputStream()));
	}

	/**
	 * Gets error reader for current process. Reads outputs as designed by Xilinx
	 * ISE.
	 * 
	 * @return
	 */
	public BufferedReader getErrorReader() {
		return new BufferedReader(new InputStreamReader(p.getErrorStream()));
	}

	public String getXtclshPath() {
		return this.xtclshPath;
	}

	public String getTclScriptPath() {
		return this.tclScriptPath;
	}

	public String getWorkingDirectory() {
		return this.workingDirectory;
	}

}
