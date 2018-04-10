package com.xilinxlite.communication;

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

public class XtclshCommandsChainImpl extends XtclshCommands {

	private static Logger logger = Logger.getLogger(XtclshCommandsChainImpl.class.getName());

	private CacheUnit cache;
	private CommandChainer chainer;
	
	private int counter;

	public XtclshCommandsChainImpl(String xtclshPath, String tclScriptPath, String workingDirectory)
			throws FileNotFoundException {
		super(xtclshPath, tclScriptPath, workingDirectory);
		cache = new CacheUnit();
		chainer = new CommandChainer();
		counter = 0;
	}

	public void run() {
		System.out.println("== chainer commands ==");
		for (String str : chainer.getCommands()) {
			System.out.println(str);
		}
		System.out.println("== end of commands ==");

		try {
			
			super.run(chainer.getCommands());
			parse();
			chainer.clear();
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Error running chained command.", e);
		}

	}

	public void clear() {
		chainer.clear();
		cache.flush();
	}
	
	private void auto_flush() {
		counter++;
		counter %= 15;
		if (counter == 0) {
			cache.flush();
			fullGUIUpdate();
		}
	}

	private void parse() {
		StringBuilder strBuilder = new StringBuilder();
		boolean not_building = true;
		String command = "";
		List<String> commandList = new ArrayList<>();

		System.out.println("== parsing ==");

		r = xtclsh.getInputReader();
		try {
			while ((line = r.readLine()) != null) {
				// identify command output
				// build string output for command output
				// pass built string to respective parse method
				// repeat till finish

				System.out.println(line);

				if (not_building) {
					System.out.println("---");
					if (line.trim().contains("xilinx_cmd_start: ")) {
						command = line.split(" ")[1];
						if (!commandList.contains(command)) {
							commandList.add(command);
							not_building = false;
						}
					}
				} else {
					if (line.trim().contains("xilinx_cmd_end: ")) {
						not_building = true;
						if (command.equals("view_files")) {
							parseViewFiles(strBuilder.toString());
						} else if (command.equals("get_top_modules")) {
							parseModules(strBuilder.toString().trim());
						} else if (command.equals("get_top_module")) {
							parseTopModule(strBuilder.toString().trim());
						} else if (command.equals("get_attributes")) {
							parseAttributes(strBuilder.toString().trim());
						} else if (command.equals("simulate")) {
							parseSimulate(strBuilder.toString().trim());
						} else if (command.equals("get_family_list")) {
							parseArchitectList(strBuilder.toString().trim());
						} else if (command.equals("get_arch_data")) {
							parseArchitectData(strBuilder.toString().trim());
						}
						strBuilder.delete(0, strBuilder.length());
					} else {
//						if (line.matches("(INFO|WARNING| ).+")) {
//							continue;
//						}
						strBuilder.append(line + "\n");
					}
				}
			}
		} catch (IOException e) {
			logger.log(Level.WARNING, "Error parsing output from chained command execution", e);
		}

		System.out.println("== parsing finished ==");
	}

	private void parseViewFiles(String content) {
		cache.getFiles().clear();
		for (String str : content.split("\n")) {
			if (str.contains("Error")) {
				break;
			}
			cache.setFile(str);
		}
	}

	private void parseModules(String content) {
		cache.getModules().clear();
		for (String str : content.split("\n")) {
			if (str.contains("Error")) {
				break;
			}
			cache.setModule(str.trim());
		}
	}

	private void parseTopModule(String content) {
		cache.setTopModule(content.trim());
	}

	private void parseAttributes(String content) {
		Pattern pattern = Pattern.compile("^\\s*\\b(?<key>.+)\\b\\s*:\\s*(?<value>.+)\\s*$");
		Matcher matcher;

		for (String str : content.split("\n")) {
			matcher = pattern.matcher(str);
			if (matcher.matches()) {
				cache.setAttribute(XilinxAttribute.valueOf(matcher.group("key").toUpperCase()), matcher.group("value"));
			}
		}
	}

	private void parseSimulate(String content) {
		// TODO
	}

	private void parseArchitectList(String content) {
		System.err.println("== parsing architect list ==");
		System.err.println(content);
		for (String str : content.split("\n")) {
//			System.err.println(str);
			if (str.matches("^\\s+\\w+")) {
//				System.err.println(">> " + str.trim());
				cache.setArchitectListItem(str.trim());
			}
		}
		System.err.println("== end of parsing architect list ==");
	}

	private void parseArchitectData(String content) {
		Map<String, Map<String, String[]>> map = cache.getArchitectData();
		Map<String, String[]> _default = new HashMap<String, String[]>();

		String[] strArr = null;

		for (String str : content.split("\n")) {
			boolean flagDevice = str.matches("\\w+\\s+SPEEDS:(\\s+\\S+)+");
			boolean flagPackage = str.matches("\\s+\\w+(\\s+\\S+)*");

			if (!flagDevice && !flagPackage) {
				strArr = null;
			} else if (flagDevice) {
				strArr = str.trim().split("\\s+(SPEEDS:\\s+)?");

				List<String> _values = new ArrayList<String>();
				for (int i = 1; i < strArr.length; i++) {
					_values.add(strArr[i]);
				}
				_default.put(strArr[0], _values.toArray(new String[0]));

				map.put(strArr[0], new HashMap<String, String[]>());
			} else if (flagPackage && strArr != null) {
				String[] packageArr = str.trim().split("\\s+");
				map.get(strArr[0]).put(packageArr[0],
						packageArr.length > 1 ? Arrays.copyOfRange(packageArr, 1, packageArr.length)
								: _default.get(strArr[0]));
			}
		}
	}

	private CommandChainer fullGUIUpdate() {
		return chainer.viewFiles().getModules().getTopModule();
	}

	@Override
	public boolean openProject(String projectName) {
		if (super.openProject(projectName)) {
			fullGUIUpdate();
			run();
			return true;
		}

		return false;
	}

	@Override
	public void closeProject() {
		super.closeProject();
		clear();
	}

	@Override
	public boolean newProject(String projectName) {
		if (super.newProject(projectName)) {
			fullGUIUpdate();
			run();
			return true;
		}
		return false;
	}

	@Override
	public Map<String, String> setAttributes(Map<String, String> attributes) {
		// Copied from XtclshCommands
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

		run();

		// return new read
		return getAttributes();
	}

	@Override
	public Map<String, String> getAttributes() {
		auto_flush();
		return cache.getAttributes();
	}

	@Override
	public String setFamily(String family) {
		chainer.setFamily(family);
		return "";
	}

	@Override
	public String getFamily() {
		return cache.getAttribute(XilinxAttribute.FAMILY);
	}

	@Override
	public String setDevice(String device) {
		chainer.setDevice(device);
		return "";
	}

	@Override
	public String getDevice() {
		return cache.getAttribute(XilinxAttribute.DEVICE);
	}

	@Override
	public String setPackage(String _package) {
		chainer.setPackage(_package);
		return "";
	}

	@Override
	public String getPackage() {
		return cache.getAttribute(XilinxAttribute.PACKAGE);
	}

	@Override
	public String setSpeed(String speed) {
		chainer.setSpeed(speed);
		return "";
	}

	@Override
	public String getSpeed() {
		return cache.getAttribute(XilinxAttribute.SPEED);
	}

	@Override
	public String setTopSourceType(String type) {
		chainer.setTopType(type);
		return "";
	}

	@Override
	public String getTopSourceType() {
		return cache.getAttribute(XilinxAttribute.TOP_SOURCE_TYPE);
	}

	@Override
	public String setSynthesis(String synthesis) {
		chainer.setSynthesis(synthesis);
		return "";
	}

	@Override
	public String getSynthesis() {
		return cache.getAttribute(XilinxAttribute.SYNTHESIS);
	}

	@Override
	public String setSimulator(String simulator) {
		chainer.setSimulator(simulator);
		return "";
	}

	@Override
	public String getSimulator() {
		return cache.getAttribute(XilinxAttribute.SIMULATOR);
	}

	@Override
	public String setPreferredLanguage(String language) {
		chainer.setLanguage(language);
		return "";
	}

	@Override
	public String getPreferredLanguage() {
		return cache.getAttribute(XilinxAttribute.LANGUAGE);
	}

	@Override
	public boolean setMessageFilter(boolean filter) {
		chainer.setMessageFilter(filter ? "true" : "false");
		return false;
	}

	@Override
	public boolean getMessageFilter() {
		String val = cache.getAttribute(XilinxAttribute.MESSAGE);
		return val.toLowerCase().equals("true");
	}

	@Override
	public boolean addFile(String filename) {
		chainer.addFile(filename.replaceAll("\\\\", "/"));
		fullGUIUpdate();
		run();
		return true;
	}

	@Override
	public boolean addFiles(String... filenames) {
		for (String filename : filenames) {
			chainer.addFile(filename.replaceAll("\\\\", "/"));
		}
		fullGUIUpdate();
		run();
		return true;
	}

	@Override
	public boolean removeFile(String filename) {
		chainer.removeFile(filename);
		fullGUIUpdate();
		run();
		return true;
	}

	@Override
	public List<String> getFiles() {
		auto_flush();
		if (cache.getFiles().isEmpty()) {
			fullGUIUpdate();
			run();
		}
		return cache.getFiles();
	}

	@Override
	public List<String> getArchitectList() {
		if (cache.getArchitectList().isEmpty()) {
			if (projectName.isEmpty()) {
				chainer.addBuffer();
			}
			chainer.getFamilyList();
			run();
		}

		System.err.println("== architect list ==");
		for (String str : cache.getArchitectList()) {
			System.err.println(str);
		}
		
		return cache.getArchitectList();
	}

	@Override
	public Map<String, Map<String, String[]>> getArchitectData(String architect) {
		Map<String, Map<String, String[]>> map = cache.getArchitectData();
		if (projectName.isEmpty()) {
			chainer.addBuffer();
		}
		chainer.getArchData(architect);
		run();
		return map;
	}

	@Override
	public String getTopModule() {
		return cache.getTopModule();
	}

	@Override
	public List<String> getTopModules() {
		auto_flush();
		return cache.getModules();
	}

	@Override
	public boolean setTopModule(String topModule) {
		chainer.setTopModule(topModule).getTopModule().getModules();
		run();
		return true;
	}

}
