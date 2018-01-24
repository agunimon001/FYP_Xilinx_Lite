package com.xilinxlite.communication;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class XtclshCommands implements Commands {

	private static final Logger logger = Logger.getLogger(XtclshCommands.class.getName());

	private XtclshWrapper xtclsh;
	private String projectName = "";
	private BufferedReader r;
	private String line;

	public XtclshCommands(String xtclshPath, String tclScriptPath) throws FileNotFoundException {
		xtclsh = XtclshWrapper.getInstance(xtclshPath, tclScriptPath);
	}

	public XtclshCommands(String xtclshPath, String tclScriptPath, String workingDirectory)
			throws FileNotFoundException {
		xtclsh = XtclshWrapper.getInstance(xtclshPath, tclScriptPath, workingDirectory);
	}

	private void run(String... commands) throws IOException {
		if (this.projectName.isEmpty()) {
			logger.info("Project not opened.");
		}
		
		String[] arr = new String[commands.length + 1];
		arr[0] = projectName;
		for (int i = 0; i < commands.length; i++) {
			arr[i + 1] = commands[i];
		}

		xtclsh.run(arr);
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
				System.out.println(line);
				if (line.contains("Error")) {
					this.projectName = null;
					return false;
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}

		return true;
	}

	@Override
	public void closeProject() {
		this.projectName = null;
	}

	@Override
	public boolean newProject(String projectName) {
		this.projectName = projectName;
		
		try {
			run("new_project");
			
			r = xtclsh.getInputReader();
			while ((line = r.readLine()) != null) {
				if (line.contains("Error")) {
					this.projectName = null;
					return false;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		
		return true;
	}

	@Override
	public void setAttributes(Map<String, String> attributes) {
		// TODO Auto-generated method stub

	}

	@Override
	public Map<String, String> getAttributes() {
		Map<String, String> attributes = new HashMap<String, String>();
		Pattern pattern = Pattern.compile("^\\s*\\b(?<key>.*)\\s*:\\s*(?<value>.*)\\s*$");
		Matcher matcher;
		
		try {
			run("get_attributes");
			
			r = xtclsh.getInputReader();
			while ((line = r.readLine()) != null) {
				matcher = pattern.matcher(line);
				if (matcher.matches()) {
					attributes.put(matcher.group("key"), matcher.group("value"));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		
		return attributes;
	}

	@Override
	public void addFile() {
		// TODO Auto-generated method stub

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
	public List<String> getFamilyList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Map<String, String[]>> getArchitectData(String architect) {
		// TODO Auto-generated method stub
		return null;
	}

}
