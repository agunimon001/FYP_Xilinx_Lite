package com.xilinxlite.gui.functions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xilinxlite.communication.CommunicationMgr;
import com.xilinxlite.communication.XilinxAttribute;
import com.xilinxlite.gui.ProjectSettingsDesign;

public class ProjectSettingsMgr extends ProjectSettingsDesign {

	private FunctionPack fnPack = FunctionPack.getInstance();
	private CommunicationMgr cmd;

	private boolean createProject = false;

	private Map<String, Map<String, String[]>> architectData;
	private Map<String, String> attributes;

	public ProjectSettingsMgr() {

	}

	public ProjectSettingsMgr(boolean createProject) {
		this.createProject = createProject;
	}

	@Override
	protected void initialize() {
		cmd = fnPack.getCommunicationMgr();

		projectNameField.setDisable(!createProject);
		workingDirectoryField.setDisable(!createProject);
		saveBtn.setDisable(!createProject && cmd.getProjectName().isEmpty());
		directoryChooserBtn.setDisable(saveBtn.isDisabled());

		List<String> architectList = cmd.getArchitectList();
		familyField.getItems().addAll(architectList);

		topLevelSourceTypeField.getItems().add("HDL");

		synthesisToolField.getItems().add("XST (VHDL/Verilog)");

		simulatorField.getItems().add("ISim (VHDL/Verilog)");

		preferredLanguageField.getItems().add("Verilog");

		if (cmd.getProjectName().isEmpty()) {
			setDefault();
		} else {
			attributes = cmd.getAttributes();
			projectNameField.setText(cmd.getProjectName());
			workingDirectoryField.setText(cmd.getWorkingDirectory());
			familyField.setValue(attributes.get(XilinxAttribute.FAMILY.toString()));
			deviceField.setValue(attributes.get(XilinxAttribute.DEVICE.toString()));
			packageField.setValue(attributes.get(XilinxAttribute.PACKAGE.toString()));
			speedField.setValue(attributes.get(XilinxAttribute.SPEED.toString()));
			topLevelSourceTypeField.setValue(attributes.get(XilinxAttribute.TOP_SOURCE_TYPE.toString()));
			synthesisToolField.setValue(attributes.get(XilinxAttribute.SYNTHESIS.toString()));
			simulatorField.setValue(attributes.get(XilinxAttribute.SIMULATOR.toString()));
			preferredLanguageField.setValue(attributes.get(XilinxAttribute.LANGUAGE.toString()));

		}
	}

	@Override
	protected void loadFamily() {
		architectData = cmd.getArchitectData(familyField.getValue());
		deviceField.getItems().clear();
		deviceField.getItems().addAll(architectData.keySet());
		deviceField.setValue(deviceField.getItems().get(0));
	}

	@Override
	protected void loadDevice() {
		try {
			Map<String, String[]> devices = architectData.get(deviceField.getValue());
			packageField.getItems().clear();
			packageField.getItems().addAll(devices.keySet());
			packageField.setValue(packageField.getItems().get(0));
		} catch (NullPointerException e) {

		}
	}

	@Override
	protected void loadPackage() {
		try {
			String[] speeds = architectData.get(deviceField.getValue()).get(packageField.getValue());
			speedField.getItems().clear();
			speedField.getItems().addAll(speeds);
			speedField.setValue(speedField.getItems().get(0));
		} catch (NullPointerException e) {

		}
	}

	@Override
	protected void loadSpeed() {

	}

	@Override
	protected void loadTopLevelSourceType() {

	}

	@Override
	protected void loadSynthesisTool() {

	}

	@Override
	protected void loadSimulator() {

	}

	@Override
	protected void loadPreferredLanguage() {

	}

	@Override
	protected void setDefault() {

		familyField.setValue("spartan6");
		loadFamily();
		deviceField.setValue("xc6slx4");
		loadDevice();
		packageField.setValue("csg225");
		loadPackage();
		speedField.setValue("-3");
		loadSpeed();

		topLevelSourceTypeField.setValue("HDL");

		synthesisToolField.setValue("XST (VHDL/Verilog)");

		simulatorField.setValue("ISim (VHDL/Verilog)");

		preferredLanguageField.setValue("Verilog");
	}

	@Override
	protected void save() {

		if (!cmd.getWorkingDirectory().equals(workingDirectoryField.getText())) {
			cmd.setWorkingDirectory(workingDirectoryField.getText());
		}

		if (cmd.getProjectName().isEmpty() && !projectNameField.getText().isEmpty()) {
			cmd.newProject(projectNameField.getText());
		}

		Map<String, String> attr = new HashMap<String, String>();
		if (!familyField.getValue().equals(attributes.get(XilinxAttribute.FAMILY.toString())))
			attr.put(XilinxAttribute.FAMILY.toString(), familyField.getValue());
		if (!deviceField.getValue().equals(attributes.get(XilinxAttribute.DEVICE.toString())))
			attr.put(XilinxAttribute.DEVICE.toString(), deviceField.getValue());
		if (!packageField.getValue().equals(attributes.get(XilinxAttribute.PACKAGE.toString())))
			attr.put(XilinxAttribute.PACKAGE.toString(), packageField.getValue());
		if (!speedField.getValue().equals(attributes.get(XilinxAttribute.SPEED.toString())))
			attr.put(XilinxAttribute.SPEED.toString(), speedField.getValue());
		if (!topLevelSourceTypeField.getValue().equals(attributes.get(XilinxAttribute.TOP_SOURCE_TYPE.toString())))
			attr.put(XilinxAttribute.TOP_SOURCE_TYPE.toString(), topLevelSourceTypeField.getValue());
		if (!synthesisToolField.getValue().equals(attributes.get(XilinxAttribute.SYNTHESIS.toString())))
			attr.put(XilinxAttribute.SYNTHESIS.toString(), synthesisToolField.getValue());
		if (!simulatorField.getValue().equals(attributes.get(XilinxAttribute.SIMULATOR.toString())))
			attr.put(XilinxAttribute.SIMULATOR.toString(), simulatorField.getValue());
		if (!preferredLanguageField.getValue().equals(attributes.get(XilinxAttribute.LANGUAGE.toString())))
			attr.put(XilinxAttribute.LANGUAGE.toString(), preferredLanguageField.getValue());

		cmd.setAttributes(attr);

		close();
	}

	@Override
	protected void close() {
		window.close();
	}

}
