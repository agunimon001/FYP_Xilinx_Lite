package com.xilinxlite.communication;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Implemented class of Commands. Uses local copy of Xilinx ISE. Development
 * based on Xilinx ISE 14.7. Project needs to be opened before using other
 * methods.
 * 
 * @author Ong Hock Leng
 *
 */
class XtclshCommands implements Commands {

	private static final Logger logger = Logger.getLogger(XtclshCommands.class.getName());

	private XtclshWrapper xtclsh = null;
	private String projectName = "";
	private BufferedReader r;
	private String line;

	/**
	 * Constructor.
	 * 
	 * @param xtclshPath
	 * @param tclScriptPath
	 * @param workingDirectory
	 * @throws FileNotFoundException
	 */
	public XtclshCommands(String xtclshPath, String tclScriptPath, String workingDirectory)
			throws FileNotFoundException {
		this.xtclsh = XtclshWrapper.getInstance(xtclshPath, tclScriptPath, workingDirectory);
	}

	/**
	 * Test connection. True if connection is successful; false otherwise (can be no
	 * connection has been setup).
	 * 
	 * @return Test success
	 */
	public boolean testConnection() {
		// Check if connection is set up
		if (xtclsh != null) {
			try {
				// Switch off logger to prevent false alarm
				Level level = logger.getLevel();
				logger.setLevel(Level.OFF);

				// Test connection with insufficient argument
				run("open_project");

				// Revert logger status
				logger.setLevel(level);

				// Read for line; return true if found
				r = xtclsh.getInputReader();
				while ((line = r.readLine()) != null) {
					if (line.contains("Error: arguments insufficient")) {
						return true;
					}
				}
			} catch (IOException e) {
			}
		}

		return false;
	}

	/**
	 * Used to run commands with xtclsh.exe within this class.
	 * 
	 * @param commands
	 * @throws IOException
	 */
	private void run(String... commands) throws IOException {
		// Check if projectName is available.
		if (this.projectName.isEmpty()) {
			logger.info("Project not opened.");
		}

		// Build String array to be used.
		String[] arr = new String[commands.length + 1];
		arr[0] = projectName;
		for (int i = 0; i < commands.length; i++) {
			arr[i + 1] = commands[i];
		}

		// Run command
		xtclsh.run(arr);
	}

	/**
	 * Reads output from xtclsh.exe for project attributes. This method is designed
	 * with the accompanied TCL script. Returns as Map<String, String>. May return
	 * empty Map if no attribute is found.
	 * 
	 * @return Attributes stored in a Map using XilinxAttribute
	 */
	private Map<String, String> readAttributes() {
		Map<String, String> attributes = new HashMap<String, String>();
		Pattern pattern = Pattern.compile("^\\s*\\b(?<key>.+)\\b\\s*:\\s*(?<value>.+)\\s*$");
		Matcher matcher;

		try {
			// Reads output and puts into Map if found valid line
			r = xtclsh.getInputReader();
			while ((line = r.readLine()) != null) {
				matcher = pattern.matcher(line);
				if (matcher.matches()) {
					attributes.put(matcher.group("key"), matcher.group("value"));
				}
			}
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Reader error", e);
		}

		return attributes;
	}

	@Override
	public String getProjectName() {
		return this.projectName;
	}

	@Override
	public boolean openProject(String projectName) {
		this.projectName = projectName;

		try {
			run("open_project");

			r = xtclsh.getInputReader();
			while ((line = r.readLine()) != null) {
				if (line.contains("Error")) {
					this.projectName = "";
					return false;
				}
			}

		} catch (IOException e) {
			logger.log(Level.SEVERE, "Reader error", e);
		}

		return true;
	}

	@Override
	public void closeProject() {
		this.projectName = "";
	}

	@Override
	public boolean newProject(String projectName) {
		this.projectName = projectName;

		try {
			run("new_project");

			r = xtclsh.getInputReader();
			while ((line = r.readLine()) != null) {
				if (line.contains("Error")) {
					this.projectName = "";
					return false;
				}
			}
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Reader error", e);
		}

		return true;
	}

	@Override
	public boolean setWorkingDirectory(String newWorkingDirectoryPath) {
		if (xtclsh != null) {
			try {
				xtclsh = XtclshWrapper.getInstance(xtclsh.getXtclshPath(), xtclsh.getTclScriptPath(),
						newWorkingDirectoryPath);
				return true;
			} catch (FileNotFoundException e) {
				logger.log(Level.WARNING, "Cannot update working directory", e);
			}
		}

		return false;
	}

	/**
	 * Gets current working directory; returns null otherwise.
	 */
	@Override
	public String getWorkingDirectory() {
		if (xtclsh != null) {
			return xtclsh.getWorkingDirectory();
		}
		return null;
	}

	/**
	 * 
	 */
	@Override
	public Map<String, String> setAttributes(Map<String, String> attributes) {
		// Run through all Attribute set methods sequentially

		// Family
		if (attributes.containsKey(XilinxAttribute.FAMILY.toString()))
			setFamily(attributes.get(XilinxAttribute.FAMILY.toString()));

		// Device
		if (attributes.containsKey(XilinxAttribute.DEVICE.toString()))
			setDevice(attributes.get(XilinxAttribute.DEVICE.toString()));

		// Package
		if (attributes.containsKey(XilinxAttribute.PACKAGE.toString()))
			setPackage(attributes.get(XilinxAttribute.PACKAGE.toString()));

		// Speed Grade
		if (attributes.containsKey(XilinxAttribute.SPEED.toString()))
			setSpeed(attributes.get(XilinxAttribute.SPEED.toString()));

		// Top-Level Source Type
		if (attributes.containsKey(XilinxAttribute.TOP_SOURCE_TYPE.toString()))
			setTopSourceType(attributes.get(XilinxAttribute.TOP_SOURCE_TYPE.toString()));

		// Synthesis Tool
		if (attributes.containsKey(XilinxAttribute.SYNTHESIS.toString()))
			setSynthesis(attributes.get(XilinxAttribute.SYNTHESIS.toString()));

		// Simulator
		if (attributes.containsKey(XilinxAttribute.SIMULATOR.toString()))
			setSimulator(attributes.get(XilinxAttribute.SIMULATOR.toString()));

		// Preferred Language
		if (attributes.containsKey(XilinxAttribute.LANGUAGE.toString()))
			setPreferredLanguage(attributes.get(XilinxAttribute.LANGUAGE.toString()));

		// Enable Message Filtering
		if (attributes.containsKey(XilinxAttribute.MESSAGE.toString())) {
			switch (attributes.get(XilinxAttribute.MESSAGE.toString()).toLowerCase()) {
			case "true":
				setMessageFilter(true);
				break;
			case "false":
				setMessageFilter(false);
				break;
			default: // do nothing
			}
		}

		// return new read
		return getAttributes();
	}

	@Override
	public Map<String, String> getAttributes() {
		try {
			run("get_attributes");
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Reader error", e);
		}

		return readAttributes();
	}

	@Override
	public Map<String, String> setFamily(String family) {
		try {
			run("set_family", family);
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Reader error", e);
		}

		return readAttributes();
	}

	@Override
	public Map<String, String> getFamily() {
		try {
			run("get_family");
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Reader error", e);
		}

		return readAttributes();
	}

	@Override
	public Map<String, String> setDevice(String device) {
		try {
			run("set_device", device);
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Reader error", e);
		}

		return readAttributes();
	}

	@Override
	public Map<String, String> getDevice() {
		try {
			run("get_device");
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Reader error", e);
		}

		return readAttributes();
	}

	@Override
	public Map<String, String> setPackage(String _package) {
		try {
			run("set_package", _package);
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Reader error", e);
		}
		return readAttributes();
	}

	@Override
	public Map<String, String> getPackage() {
		try {
			run("get_package");
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Reader error", e);
		}
		return readAttributes();
	}

	@Override
	public Map<String, String> setSpeed(String speed) {
		try {
			run("set_speed", speed);
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Reader error", e);
		}

		return readAttributes();
	}

	@Override
	public Map<String, String> getSpeed() {
		try {
			run("get_speed");
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Reader error", e);
		}
		return readAttributes();
	}

	@Override
	public Map<String, String> setTopSourceType(String type) {
		try {
			run("set_top_level_source_type", type);
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Reader error", e);
		}

		return readAttributes();
	}

	@Override
	public Map<String, String> getTopSourceType() {
		try {
			run("get_top_level_source_type");
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Reader error", e);
		}

		return readAttributes();
	}

	@Override
	public Map<String, String> setSynthesis(String synthesis) {
		try {
			run("set_synthesis", synthesis);
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Reader error", e);
		}

		return readAttributes();
	}

	@Override
	public Map<String, String> getSynthesis() {
		try {
			run("get_synthesis");
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Reader error", e);
		}

		return readAttributes();
	}

	@Override
	public Map<String, String> setSimulator(String simulator) {
		try {
			run("set_simulator", simulator);
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Reader error", e);
		}

		return readAttributes();
	}

	@Override
	public Map<String, String> getSimulator() {
		try {
			run("get_simulator");
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Reader error", e);
		}

		return readAttributes();
	}

	@Override
	public Map<String, String> setPreferredLanguage(String language) {
		try {
			run("set_language", language);
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Reader error", e);
		}

		return readAttributes();
	}

	@Override
	public Map<String, String> getPreferredLanguage() {
		try {
			run("get_language");
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Reader error", e);
		}

		return readAttributes();
	}

	@Override
	public Map<String, String> setMessageFilter(boolean filter) {
		try {
			run("set_message_filter", (filter ? "true" : "false"));
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Reader error", e);
		}

		return readAttributes();
	}

	@Override
	public Map<String, String> getMessageFilter() {
		try {
			run("get_message_filter");
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Reader error", e);
		}

		return readAttributes();
	}

	@Override
	public boolean addFile(String filename) {
		try {
			// adjust filename to suit xtclsh usage
			filename = filename.replaceAll("\\\\", "/");
			while (filename.contains("//")) {
				filename.replaceAll("//", "/");
			}

			run("add_file", filename);

			// Check for error caused by file being present
			r = xtclsh.getErrorReader();
			while ((line = r.readLine()) != null) {
				if (line.contains("is already present")) {
					return false;
				}
			}
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Reader error", e);
		}

		return true;
	}

	@Override
	public boolean removeFile(String filename) {
		logger.log(Level.FINE, "Removing file " + filename);
		try {
			filename = filename.replaceAll("\\\\", "/");
			while (filename.contains("//")) {
				filename.replaceAll("//", "/");
			}
			
			run("remove_file", filename);

			BufferedReader r = xtclsh.getInputReader();
			while ((line = r.readLine()) != null) {
				if (line.contains(filename)) {
					return false;
				}
			}
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Reader error", e);
		}
		return true;
	}

	@Override
	public List<String> getFiles() {
		List<String> output = new ArrayList<String>();

		try {
			run("view_files");

			r = xtclsh.getInputReader();
			while ((line = r.readLine()) != null) {
				output.add(line);
			}
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Reader error", e);
		}

		return output;
	}

	@Override
	public void synthesize() {
		try {
			run("synthesize");
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Reader error", e);
		}
	}

	@Override
	public List<String> getArchitectList() {
		List<String> list = new ArrayList<String>();

		try {
			run("get_family_list");

			r = xtclsh.getInputReader();
			while ((line = r.readLine()) != null) {
				if (line.contains("Valid architectures are")) {
					break;
				}
			}

			while ((line = r.readLine()) != null) {
				if (line.matches("^\\s+\\w+")) {
					list.add(line.trim());
				} else {
					break;
				}
			}
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Reader error", e);
		}

		return list;
	}

	@Override
	public Map<String, Map<String, String[]>> getArchitectData(String architect) {
		Map<String, Map<String, String[]>> map = new HashMap<String, Map<String, String[]>>();
		Map<String, String[]> _default = new HashMap<String, String[]>();

		try {
			run("get_arch_data", architect);

			r = xtclsh.getInputReader();
			String[] strArr = null;
			while ((line = r.readLine()) != null) {
				boolean flagDevice = line.matches("\\w+\\s+SPEEDS:(\\s+\\S+)+");
				boolean flagPackage = line.matches("\\s+\\w+(\\s+\\S+)*");

				if (!flagDevice && !flagPackage) {
					strArr = null;
				} else if (flagDevice) {
					strArr = line.trim().split("\\s+(SPEEDS:\\s+)?");

					List<String> _values = new ArrayList<String>();
					for (int i = 1; i < strArr.length; i++) {
						_values.add(strArr[i]);
					}
					_default.put(strArr[0], _values.toArray(new String[0]));

					map.put(strArr[0], new HashMap<String, String[]>());
				} else if (flagPackage && strArr != null) {
					String[] packageArr = line.trim().split("\\s+");
					map.get(strArr[0]).put(packageArr[0],
							packageArr.length > 1 ? Arrays.copyOfRange(packageArr, 1, packageArr.length)
									: _default.get(strArr[0]));
				}
			}
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Reader error", e);
		}

		return map;
	}

}
