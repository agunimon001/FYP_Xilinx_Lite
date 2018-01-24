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

	public void setAttributes(Map<String, String> attributes);

	public Map<String, String> getAttributes();

	public void addFile();

	public List<String> getFiles();

	public void synthesize();

	public List<String> getFamilyList();

	// Device, Package, Speed
	public Map<String, Map<String, String[]>> getArchitectData(String architect);

}
