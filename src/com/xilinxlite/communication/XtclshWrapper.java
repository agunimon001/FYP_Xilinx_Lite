package com.xilinxlite.communication;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Wrapper for xtclsh.exe of local copy of Xilinx ISE xtclsh.exe [script]
 * [commands..]
 * 
 * @author Ong Hock Leng
 *
 */
class XtclshWrapper {

	private String xtclshPath;
	private String tclScriptPath;
	private String workingDirectory;

	/**
	 * Constructor. Throws FileNotFoundException if the parameters are incorrect.
	 * Message is built for dialog box.
	 * 
	 * @param xtclsh
	 * @param tclScript
	 * @throws FileNotFoundException
	 */
	XtclshWrapper(String xtclsh, String tclScript) throws FileNotFoundException {
		this(xtclsh, tclScript, null);
	}

	XtclshWrapper(String xtclsh, String tclScript, String workingDirectory) throws FileNotFoundException {
		xtclshPath = xtclsh;
		tclScriptPath = tclScript;
		String errorMsg = "";

		// Check xtclsh.exe
		if (!xtclshPath.endsWith("xtclsh.exe"))
			errorMsg += xtclshPath + " is not the correct program.";
		else if (!new File(xtclshPath).exists())
			errorMsg += (errorMsg.isEmpty() ? "" : "\n") + xtclshPath + " is not found.";

		// Check TCL script
		if (!tclScriptPath.endsWith(".tcl"))
			errorMsg += tclScriptPath + " is not a TCL script.";
		else if (!new File(tclScriptPath).exists())
			errorMsg += (errorMsg.isEmpty() ? "" : "\n") + tclScriptPath + " is not found.";

		if (workingDirectory == null) {
			// Sets default working directory
			this.workingDirectory = System.getProperty("user.home") + "\\Xilinx_Lite";
			File file = new File(this.workingDirectory);
			if (!file.exists())
				file.mkdirs();
		} else {
			// Checks for valid working directory
			this.workingDirectory = workingDirectory;
			if (!new File(this.workingDirectory).exists())
				errorMsg += (errorMsg.isEmpty() ? "" : "\n") + workingDirectory + " is not found.";
		}

		if (!errorMsg.isEmpty())
			throw new FileNotFoundException(errorMsg);
	}

	/**
	 * Parameter 'commands' is the argument array used in conjunction with the TCL
	 * shell. The TCL shell doesn't have an input stream, therefore this method
	 * returns only the input stream.
	 * 
	 * @param commands
	 * @return
	 * @throws IOException
	 */
	BufferedReader run(String... commands) throws IOException {
		String[] commandArray = new String[commands.length + 2];
		commandArray[0] = xtclshPath;
		commandArray[1] = tclScriptPath;
		for (int i = 0; i < commands.length; i++) {
			commandArray[2 + i] = commands[i];
		}

		ProcessBuilder pb = new ProcessBuilder(commandArray);
		pb.directory(new File(this.workingDirectory));
		Process p = pb.start();
		return new BufferedReader(new InputStreamReader(p.getInputStream()));
	}

}
