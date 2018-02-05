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
	 * @return True if successfully changed working directory; false if otherwise.
	 */
	public boolean setWorkingDirectory(String newWorkingDirectoryPath);
	
	/**
	 * Gets current working directory.
	 * 
	 * @return 
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
	 * @return A Map containing entry if successful; empty Map if otherwise.
	 */
	public Map<String, String> setFamily(String family);

	/**
	 * Getter for Device Family attribute.
	 * 
	 * @return A Map containing Family attribute.
	 */
	public Map<String, String> getFamily();

	// Attribute: Device
	/**
	 * Setter for Device attribute.
	 * 
	 * @param device
	 * @return A Map containing entry if successful; empty Map if otherwise.
	 */
	public Map<String, String> setDevice(String device);

	/**
	 * Getter for Device attribute.
	 * 
	 * @return a Map containing Device attribute.
	 */
	public Map<String, String> getDevice();

	// Attribute: Package
	/**
	 * Setter for Package attribute.
	 * 
	 * @param _package
	 * @return A Map containing entry if successful; empty Map if otherwise.
	 */
	public Map<String, String> setPackage(String _package);

	/**
	 * Getter for Package attribute.
	 * 
	 * @return A Map containing Package attribute.
	 */
	public Map<String, String> getPackage();

	// Attribute: Speed Grade
	/**
	 * Setter for Speed Grade attribute.
	 * 
	 * @param speed
	 * @return A Map containing entry if successful; empty Map if otherwise.
	 */
	public Map<String, String> setSpeed(String speed);

	/**
	 * Getter for Speed Grade attribute.
	 * 
	 * @return A Map containing Speed Grade attribute.
	 */
	public Map<String, String> getSpeed();

	// Attribute: Top-Level Source Type
	/**
	 * Setter for Top-Level Source Type attribute.
	 * 
	 * @param type
	 * @return A Map containing entry if successful; empty Map if otherwise.
	 */
	public Map<String, String> setTopSourceType(String type);

	/**
	 * Getter for Top-Level Source Type attribute.
	 * 
	 * @return A Map containing Top-Level Source Type attribute.
	 */
	public Map<String, String> getTopSourceType();

	// Attribute: Synthesis Tool
	/**
	 * Setter for Synthesis Tool attribute.
	 * 
	 * @param synthesis
	 * @return A Map containing entry if successful; empty Map if otherwise.
	 */
	public Map<String, String> setSynthesis(String synthesis);

	/**
	 * Getter for Synthesis Tool attribute.
	 * 
	 * @return A Map containing Synthesis Tool attribute.
	 */
	public Map<String, String> getSynthesis();

	// Attribute: Simulator
	/**
	 * Setter for Simulator attribute.
	 * 
	 * @param simulator
	 * @return A Map containing entry if successful; empty Map if otherwise.
	 */
	public Map<String, String> setSimulator(String simulator);

	/**
	 * Getter for Simulator attribute.
	 * 
	 * @return A Map containing Simulator attribute.
	 */
	public Map<String, String> getSimulator();

	// Attribute: Preferred Language
	/**
	 * Setter for Preferred Language attribute.
	 * 
	 * @param language
	 * @return A Map containing entry if successful; empty Map if otherwise.
	 */
	public Map<String, String> setPreferredLanguage(String language);

	/**
	 * Getter for Preferred Language attribute.
	 * 
	 * @return A Map containing Preferred Language attribute.
	 */
	public Map<String, String> getPreferredLanguage();

	// Attribute: Enable Message Filtering
	/**
	 * Setter for Enable Message Filtering attribute.
	 * 
	 * @param filter
	 * @return A Map containing entry if successful; empty Map if otherwise.
	 */
	public Map<String, String> setMessageFilter(boolean filter);

	/**
	 * Getter for Enable Message Filtering attribute.
	 * 
	 * @return A Map containing Enable Message Filtering attribute.
	 */
	public Map<String, String> getMessageFilter();

	// Other methods
	/**
	 * Adds file to a project.
	 * 
	 * @param filename
	 *            Provide absolute path of file to add.
	 */
	public boolean addFile(String filename);

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
	 * @return
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

}
