package com.xilinxlite.communication;

import java.util.ArrayList;
import java.util.List;

public class CommandChainer {

	private List<String> commands_prefix;
	private List<String> commands_suffix;
	private List<String> commands;

	public CommandChainer() {
		commands_prefix = new ArrayList<>();
		commands_suffix = new ArrayList<>();
		commands = new ArrayList<>();
	}

	public void clear() {
		commands_prefix.clear();
		commands_suffix.clear();
	}

	public String[] getCommands() {
		commands.clear();
		commands.addAll(commands_prefix);
		commands.addAll(commands_suffix);
		return commands.toArray(new String[0]);
	}

	public CommandChainer openProject(String projectName) {
		commands_prefix.add("/open_project");
		commands_prefix.add(projectName);
		return this;
	}

	public CommandChainer synthesize() {
		commands_suffix.add("/synthesize");
		return this;
	}

	public CommandChainer newProject(String projectName) {
		commands_prefix.add("/new_project");
		commands_prefix.add(projectName);
		return this;
	}

	public CommandChainer getAttributes() {
		if (!commands_suffix.contains("/get_attributes")) {
			commands_suffix.add("/get_attributes");
		}
		return this;
	}

	public CommandChainer getFamilyList() {
		commands_suffix.add("/get_family_list");
		return this;
	}

	public CommandChainer getArchData(String arch) {
		commands_suffix.add("/get_arch_data");
		commands_suffix.add(arch);
		return this;
	}

	public CommandChainer addFile(String file) {
		commands_prefix.add("/add_file");
		commands_prefix.add(file);
		return this;
	}

	public CommandChainer addFiles(List<String> files) {
		commands_prefix.add("/add_files");
		for (String file : files) {
			commands_prefix.add(file);
		}
		return this;
	}

	public CommandChainer removeFile(String file) {
		commands_prefix.add("/remove_file");
		commands_prefix.add(file);
		return this;
	}

	public CommandChainer viewFiles() {
		if (!commands_suffix.contains("/view_files")) {
			commands_suffix.add("/view_files");
		}
		return this;
	}

	public CommandChainer setFamily(String family) {
		commands_prefix.add("/set_family");
		commands_prefix.add(family);
		return this;
	}

	public CommandChainer getFamily() {
		commands_suffix.add("/get_family");
		return this;
	}

	public CommandChainer setDevice(String device) {
		commands_prefix.add("/set_device");
		commands_prefix.add(device);
		return this;
	}

	public CommandChainer getDevice() {
		commands_suffix.add("/get_device");
		return this;
	}

	public CommandChainer setPackage(String _package) {
		commands_prefix.add("/set_package");
		commands_prefix.add(_package);
		return this;
	}

	public CommandChainer getPackage() {
		commands_suffix.add("/get_package");
		return this;
	}

	public CommandChainer setSpeed(String speed) {
		commands_prefix.add("/set_speed");
		commands_prefix.add(speed);
		return this;
	}

	public CommandChainer getSpeed() {
		commands_suffix.add("/get_speed");
		return this;
	}

	public CommandChainer setTopType(String topType) {
		commands_prefix.add("/set_top_level_source_type");
		commands_prefix.add(topType);
		return this;
	}

	public CommandChainer getTopType() {
		commands_suffix.add("/get_top_level_source_type");
		return this;
	}

	public CommandChainer setSynthesis(String synthesis) {
		commands_prefix.add("/set_synthesis");
		commands_prefix.add(synthesis);
		return this;
	}

	public CommandChainer getSynthesis() {
		commands_suffix.add("/get_synthesis");
		return this;
	}

	public CommandChainer setSimulator(String simulator) {
		commands_prefix.add("/set_simulator");
		commands_prefix.add(simulator);
		return this;
	}

	public CommandChainer getSimulator() {
		commands_suffix.add("/get_simulator");
		return this;
	}

	public CommandChainer setLanguage(String language) {
		commands_prefix.add("/set_language");
		commands_prefix.add(language);
		return this;
	}

	public CommandChainer getLanguage() {
		commands_suffix.add("/get_language");
		return this;
	}

	public CommandChainer setMessageFilter(String filter) {
		commands_prefix.add("/set_message_filter");
		commands_prefix.add(filter);
		return this;
	}

	public CommandChainer getMessageFilter() {
		commands_suffix.add("/get_message_filter");
		return this;
	}

	public CommandChainer setTopModule(String module) {
		commands_prefix.add("/set_top_module");
		commands_prefix.add(module);
		return this;
	}

	public CommandChainer getTopModule() {
		commands_suffix.add("/get_top_module");
		return this;
	}

	public CommandChainer getModules() {
		if (!commands_suffix.contains("/get_top_modules")) {
			commands_suffix.add("/get_top_modules");
		}
		return this;
	}

	public CommandChainer simulate(String module, String glbl) {
		if (!commands_suffix.contains("/simulate")) {
			commands_suffix.add("/simulate");
			commands_suffix.add(module);
			commands_suffix.add(glbl);
		}
		return this;
	}

	public CommandChainer generateProgrammingFile() {
		if (!commands_suffix.contains("/generate_programming_file")) {
			commands_suffix.add("/generate_programming_file");
		}
		return this;
	}
	
	public CommandChainer addBuffer() {
		commands_prefix.add("NIL");
		return this;
	}

}
