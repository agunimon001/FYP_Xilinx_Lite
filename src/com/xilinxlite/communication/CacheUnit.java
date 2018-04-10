package com.xilinxlite.communication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CacheUnit {

	private Map<String, String> attributes;
	private List<String> files;
	private String topModule;
	private List<String> modules;
	private List<String> architectList;
	private Map<String, Map<String, String[]>> architectData;
	private SimulationData simulationData;

	public CacheUnit() {
		attributes = new HashMap<>();
		files = new ArrayList<>();
		modules = new ArrayList<>();
		architectList = new ArrayList<>();
		architectData = new HashMap<>();
		simulationData = new SimulationData();
		flush();
	}

	public void flush() {
		attributes.clear();
		for (XilinxAttribute attr : XilinxAttribute.values()) {
			attributes.put(attr.toString(), "");
		}
		files.clear();;
		topModule = "";
		modules.clear();
		architectList.clear();
		architectData.clear();
	}

	public void setAttributes(Map<String, String> attributes) {
		this.attributes = attributes;
	}

	public Map<String, String> getAttributes() {
		return this.attributes;
	}

	public void setAttribute(XilinxAttribute attr, String value) {
		this.attributes.put(attr.toString(), value);
	}

	public String getAttribute(XilinxAttribute attr) {
		return this.attributes.get(attr.toString());
	}

	public void setFiles(List<String> files) {
		this.files = files;
	}

	public void setFile(String file) {
		this.files.add(file);
	}

	public List<String> getFiles() {
		return this.files;
	}

	public void setTopModule(String topModule) {
		this.topModule = topModule;
	}

	public String getTopModule() {
		return this.topModule;
	}

	public void setModules(List<String> modules) {
		this.modules = modules;
	}
	
	public void setModule(String module) {
		this.modules.add(module);
	}

	public List<String> getModules() {
		return this.modules;
	}

	public void setArchitectList(List<String> architectList) {
		this.architectList = architectList;
	}

	public void setArchitectListItem(String architectListItem) {
		this.architectList.add(architectListItem);
	}

	public List<String> getArchitectList() {
		return this.architectList;
	}

	public void setArchitectData(Map<String, Map<String, String[]>> architectData) {
		this.architectData = architectData;
	}

	public Map<String, Map<String, String[]>> getArchitectData() {
		return this.architectData;
	}
}
