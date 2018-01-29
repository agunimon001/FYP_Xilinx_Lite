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

	public boolean newProject(String projectName);

	public boolean openProject(String projectName);

	public String getProjectName();

	public void closeProject();

	public Map<String, String> setAttributes(Map<String, String> attributes);

	public Map<String, String> getAttributes();

	// Attribute: Family
	public Map<String, String> setFamily(String family);

	public Map<String, String> getFamily();

	// Attribute: Device
	public Map<String, String> setDevice(String device);

	public Map<String, String> getDevice();

	// Attribute: Package
	public Map<String, String> setPackage(String _package);

	public Map<String, String> getPackage();

	// Attribute: Speed Grade
	public Map<String, String> setSpeed(String speed);

	public Map<String, String> getSpeed();

	// Attribute: Top-Level Source Type
	public Map<String, String> setTopSourceType(String type);

	public Map<String, String> getTopSourceType();

	// Attribute: Synthesis Tool
	public Map<String, String> setSynthesis(String synthesis);

	public Map<String, String> getSynthesis();

	// Attribute: Simulator
	public Map<String, String> setSimulator(String simulator);

	public Map<String, String> getSimulator();

	// Attribute: Preferred Language
	public Map<String, String> setPreferredLanguage(String language);

	public Map<String, String> getPreferredLanguage();

	// Attribute: Enable Message Filtering
	public Map<String, String> setMessageFilter(boolean filter);

	public Map<String, String> getMessageFilter();

	// Other methods
	public void addFile(String filename);

	public List<String> getFiles();

	public void synthesize();

	public List<String> getFamilyList();

	// Device, Package, Speed
	public Map<String, Map<String, String[]>> getArchitectData(String architect);

}
