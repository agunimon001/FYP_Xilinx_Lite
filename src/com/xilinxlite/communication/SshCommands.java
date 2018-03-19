package com.xilinxlite.communication;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class SshCommands implements Commands {

	private static final Logger logger = Logger.getLogger(SshCommands.class.getName());

	public SshCommands() {

	}

	@Override
	public boolean newProject(String projectName) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean openProject(String projectName) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getProjectName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void closeProject() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean setWorkingDirectory(String newWorkingDirectoryPath) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getWorkingDirectory() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> setAttributes(Map<String, String> attributes) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> getAttributes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setFamily(String family) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getFamily() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setDevice(String device) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDevice() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setPackage(String _package) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPackage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setSpeed(String speed) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getSpeed() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setTopSourceType(String type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getTopSourceType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setSynthesis(String synthesis) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getSynthesis() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setSimulator(String simulator) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getSimulator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setPreferredLanguage(String language) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPreferredLanguage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean setMessageFilter(boolean filter) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean getMessageFilter() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addFile(String filename) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeFile(String filename) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<String> getFiles() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void synthesize() {
		// TODO Auto-generated method stub

	}

	@Override
	public List<String> getArchitectList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Map<String, String[]>> getArchitectData(String architect) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void simulate(String moduleName) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getTopModule() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getTopModules() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean setTopModule(String topModule) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getXtclshPath() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void generatePrgFile() {
		// TODO Auto-generated method stub
		
	}

}
