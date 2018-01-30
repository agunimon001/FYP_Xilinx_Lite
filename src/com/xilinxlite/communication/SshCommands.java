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
	public Map<String, String> setFamily(String family) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> getFamily() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> setDevice(String device) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> getDevice() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> setPackage(String _package) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> getPackage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> setSpeed(String speed) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> getSpeed() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> setTopSourceType(String type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> getTopSourceType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> setSynthesis(String synthesis) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> getSynthesis() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> setSimulator(String simulator) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> getSimulator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> setPreferredLanguage(String language) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> getPreferredLanguage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> setMessageFilter(boolean filter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> getMessageFilter() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean addFile(String filename) {
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

}
