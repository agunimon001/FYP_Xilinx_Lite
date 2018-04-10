package com.xilinxlite.gui.functions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.xilinxlite.communication.CommunicationMgr;
import com.xilinxlite.communication.XilinxAttribute;
import com.xilinxlite.gui.ProjectSettingsDesign;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * Function implementation for PRojectSettingsDesign.
 * 
 * @author Ong Hock Leng
 *
 */
public class ProjectSettingsMgr extends ProjectSettingsDesign {

	private static Logger logger = Logger.getLogger(ProjectSettingsMgr.class.getName());

	private FunctionPack fnPack = FunctionPack.getInstance();

	private boolean createProject = false;

	private Map<String, Map<String, String[]>> architectData;
	private Map<String, String> attributes;

	/**
	 * Default constructor. Flag for creating new project is set to false.
	 */
	public ProjectSettingsMgr() {

	}

	/**
	 * Constructor. Sets flag for creating new project.
	 * 
	 * @param createProject
	 *            Set true to enable flag
	 */
	public ProjectSettingsMgr(boolean createProject) {
		this.createProject = createProject;
	}

	@Override
	protected void initialize() {

		projectNameField.setDisable(!createProject);
		workingDirectoryField.setDisable(!createProject);
		saveBtn.setDisable(!createProject && fnPack.isProjectClosed());
		directoryChooserBtn.setDisable(!createProject);

		List<String> architectList = fnPack.getArchitectList();
		familyField.getItems().addAll(architectList);

		topLevelSourceTypeField.getItems().add("HDL");

		synthesisToolField.getItems().add("XST (VHDL/Verilog)");

		simulatorField.getItems().add("ISim (VHDL/Verilog)");

		preferredLanguageField.getItems().add("Verilog");

		workingDirectoryField.setText(fnPack.getWorkingDirectory());

		if (fnPack.getProjectName().isEmpty()) {
			setDefault();
		} else {
			// Use XilinxAttribute as key to Map<> from attributes.get()
			attributes = fnPack.getAttributes();
			projectNameField.setText(fnPack.getProjectName());
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
		architectData = fnPack.getArchitectData(familyField.getValue());
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
			logger.log(Level.FINE, "Error loading for device " + deviceField.getValue(), e);
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
			logger.log(Level.FINE, "Error loading for package " + packageField.getValue(), e);
		}
	}

	@Override
	protected void loadSpeed() {
		// do nothing; nothing else to load along with speed
	}

	@Override
	protected void loadTopLevelSourceType() {
		// do nothing; current default only one setting
	}

	@Override
	protected void loadSynthesisTool() {
		// do nothing; current default only one setting
	}

	@Override
	protected void loadSimulator() {
		// do nothing; current default only one setting
	}

	@Override
	protected void loadPreferredLanguage() {
		// do nothing; current default only one setting
	}

	@Override
	protected void setDefault() {

		// Load for each new value; to ensure Xilinx ISE contains those values
		familyField.setValue("spartan6");
		loadFamily();
		deviceField.setValue("xc6slx4");
		loadDevice();
		packageField.setValue("csg225");
		loadPackage();
		speedField.setValue("-3");
		loadSpeed();

		// Default values
		topLevelSourceTypeField.setValue("HDL");

		synthesisToolField.setValue("XST (VHDL/Verilog)");

		simulatorField.setValue("ISim (VHDL/Verilog)");

		preferredLanguageField.setValue("Verilog");
	}

	@Override
	protected void save() {

		// Change working directory if new working directory is not equal to
		// CommunicationMgr's
		if (!fnPack.getWorkingDirectory().equals(workingDirectoryField.getText())) {
			fnPack.setWorkingDirectory(workingDirectoryField.getText());
		}

		// If project creation is successful, obtain attributes of created project
		if (createProject && fnPack.isProjectClosed() && !projectNameField.getText().isEmpty()) {
			if (fnPack.newProject(projectNameField.getText())) {
				attributes = fnPack.getAttributes();
			} else {
				// Alert if a project with the same name is found
				Alert alert = new Alert(AlertType.ERROR);
				alert.setContentText("A project of the same name is found in the working directory!!!");
				alert.showAndWait();
				return;
			}
		}

		// Build attribute map to use for updating project with; compares with current
		// attribute settings to determine which to update with
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

		fnPack.setAttributes(attr);

		fnPack.update();

		close();
	}

	@Override
	protected void close() {
		window.close();
	}

}
