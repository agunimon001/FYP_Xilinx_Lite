package com.xilinxlite.communication;

import java.util.List;
import java.util.Map;

/**
 * Interface for commands usable by program for calling Xilinx ISE functions.
 * Implements with local/remote.
 * 
 * @author Ong Hock Leng
 *
 */
public interface Commands {

	/**
	 * Creates a new project with given projectName. Project is considered opened if
	 * successful.
	 * 
	 * @param projectName
	 *            Name of project
	 * @return True if project is created successfully; false otherwise.
	 */
	public boolean newProject(String projectName);

	/**
	 * Opens project with given projectName.
	 * 
	 * @param projectName
	 *            Name of project
	 * @return True if project is opened successfully; false otherwise.
	 */
	public boolean openProject(String projectName);

	/**
	 * Get opened project's name.
	 * 
	 * @return Project's name; empty String if unavailable.
	 */
	public String getProjectName();

	/**
	 * Closes project.
	 */
	public void closeProject();

	/**
	 * Change working directory.
	 * 
	 * @param newWorkingDirectoryPath
	 *            New working directory
	 * 
	 * @return True if successfully changed working directory; false if otherwise.
	 */
	public boolean setWorkingDirectory(String newWorkingDirectoryPath);

	/**
	 * Gets current working directory.
	 * 
	 * @return Path of current working directory
	 */
	public String getWorkingDirectory();

	/**
	 * Sets project attributes. Attributes can be found in XilinxAttribute Enum
	 * class.
	 * 
	 * @param attributes
	 *            Use XilinxAttribute for the keys.
	 * @return A Map containing all successful setting.
	 */
	public Map<String, String> setAttributes(Map<String, String> attributes);

	/**
	 * Gets current project attributes.
	 * 
	 * @return A Map containing project settings.
	 */
	public Map<String, String> getAttributes();

	// Attribute: Device Family
	/**
	 * Setter for Device Family attribute.
	 * 
	 * @param family
	 *            New family name
	 * @return Current family
	 */
	public String setFamily(String family);

	/**
	 * Getter for Device Family attribute.
	 * 
	 * @return Current family
	 */
	public String getFamily();

	// Attribute: Device
	/**
	 * Setter for Device attribute.
	 * 
	 * @param device
	 *            New device name
	 * @return Current device
	 */
	public String setDevice(String device);

	/**
	 * Getter for Device attribute.
	 * 
	 * @return Current device
	 */
	public String getDevice();

	// Attribute: Package
	/**
	 * Setter for Package attribute.
	 * 
	 * @param _package
	 *            New package name
	 * @return Current package
	 */
	public String setPackage(String _package);

	/**
	 * Getter for Package attribute.
	 * 
	 * @return Current package
	 */
	public String getPackage();

	// Attribute: Speed Grade
	/**
	 * Setter for Speed Grade attribute.
	 * 
	 * @param speed
	 *            New speed rating
	 * @return Current speed
	 */
	public String setSpeed(String speed);

	/**
	 * Getter for Speed Grade attribute.
	 * 
	 * @return Current speed
	 */
	public String getSpeed();

	// Attribute: Top-Level Source Type
	/**
	 * Setter for Top-Level Source Type attribute.
	 * 
	 * @param type
	 *            New top source type name
	 * @return Current top-level source type
	 */
	public String setTopSourceType(String type);

	/**
	 * Getter for Top-Level Source Type attribute.
	 * 
	 * @return Current top-level source type
	 */
	public String getTopSourceType();

	// Attribute: Synthesis Tool
	/**
	 * Setter for Synthesis Tool attribute.
	 * 
	 * @param synthesis
	 *            New synthesis name
	 * @return Current synthesis tool
	 */
	public String setSynthesis(String synthesis);

	/**
	 * Getter for Synthesis Tool attribute.
	 * 
	 * @return Current synthesis tool
	 */
	public String getSynthesis();

	// Attribute: Simulator
	/**
	 * Setter for Simulator attribute.
	 * 
	 * @param simulator
	 *            New simulator name
	 * @return Current simulator
	 */
	public String setSimulator(String simulator);

	/**
	 * Getter for Simulator attribute.
	 * 
	 * @return Current simulator
	 */
	public String getSimulator();

	// Attribute: Preferred Language
	/**
	 * Setter for Preferred Language attribute.
	 * 
	 * @param language
	 *            New preferred language
	 * @return Current preferred language
	 */
	public String setPreferredLanguage(String language);

	/**
	 * Getter for Preferred Language attribute.
	 * 
	 * @return Current preferred language
	 */
	public String getPreferredLanguage();

	// Attribute: Enable Message Filtering
	/**
	 * Setter for Enable Message Filtering attribute.
	 * 
	 * @param filter
	 *            New message filter setting
	 * @return Current message filter setting
	 */
	public boolean setMessageFilter(boolean filter);

	/**
	 * Getter for Enable Message Filtering attribute.
	 * 
	 * @return Current message filter setting
	 */
	public boolean getMessageFilter();

	// Other methods
	/**
	 * Adds file to a project.
	 * 
	 * @param filename
	 *            Provide absolute path of file to add.
	 * 
	 * @return True if adding file is successful; false if otherwise.
	 */
	public boolean addFile(String filename);

	/**
	 * Removes file from a project.
	 * 
	 * @param filename
	 *            Filename of file to be removed
	 * @return True if removal is successful; false if otherwise;
	 */
	public boolean removeFile(String filename);

	/**
	 * Gets list of .v files.
	 * 
	 * @return List of .v files.
	 */
	public List<String> getFiles();

	/**
	 * Synthesize project.
	 */
	public void synthesize();

	/**
	 * Gets List of Architecture available for use.
	 * 
	 * @return List of available architectures
	 */
	public List<String> getArchitectList();

	// Device, Package, Speed
	/**
	 * Gets tree for provided architect in the following form: Device, Package,
	 * Speed. Uses double Map like a tree to contain the information. Use
	 * map.get(_device).get(_package) to get array of speed available for that
	 * particular device and package.
	 * 
	 * @param architect
	 *            One of the available architects provided by getArchitectList().
	 * @return Tree format of information for provided architect.
	 */
	public Map<String, Map<String, String[]>> getArchitectData(String architect);

	/**
	 * Simulates with Top Module of the project. Module name must be provided.
	 * 
	 * @param moduleName
	 *            Module to simulate
	 */
	public void simulate(String moduleName);

	/**
	 * Gets current project's top module.
	 * 
	 * @return top module
	 */
	public String getTopModule();

	/**
	 * Gets list of available modules in current project.
	 * 
	 * @return List of modules available
	 */
	public List<String> getTopModules();

	/**
	 * Sets new module as top module. Return the success state of the method.
	 * 
	 * @param topModule
	 *            String of the module with preceding '/' to set as top module
	 * @return true if successfully set top module; false if otherwise
	 */
	public boolean setTopModule(String topModule);

	/**
	 * Gets path to xtclsh.exe
	 * 
	 * @return path to xtclsh.exe
	 */
	public String getXtclshPath();

	/**
	 * Generate programming file for the project's top module.
	 */
	public void generatePrgFile();

}
