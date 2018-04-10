package com.xilinxlite.gui.functions;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.xilinxlite.gui.ButtonSetDesign;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Tooltip;

/**
 * Function implementation for ButtonSetDesign.
 * 
 * @author Ong Hock Leng
 *
 */
public class ButtonSetMgr extends ButtonSetDesign implements Updateable {

	private static Logger logger = Logger.getLogger(ButtonSetMgr.class.getName());

	private FunctionPack fnPack;
	
	private DirectoryViewMgr dvm;
	private MessageViewMgr mvm;
	
	public void setDirectoryViewMgr(DirectoryViewMgr dvm) {
		this.dvm = dvm;
	}
	
	public void setMessageViewMgr(MessageViewMgr mvm) {
		this.mvm = mvm;
	}

	@Override
	public void update() {
		boolean projectClosed = fnPack.isProjectClosed();
		synthesizeBtn.setDisable(projectClosed);
		simulateBtn.setDisable(projectClosed);
		generatePrgFileBtn.setDisable(projectClosed);
		filterBtn.setDisable(projectClosed);
	}

	@Override
	protected void initialize() {
		// Load FunctionPack
		fnPack = FunctionPack.getInstance();
		if (fnPack == null) {
			logger.log(Level.SEVERE, "No FunctionPack detected.", new Exception());
		}
		
		createProjectBtn.setTooltip(new Tooltip("Creates new project"));
		openProjectBtn.setTooltip(new Tooltip("Opens existing project"));
		synthesizeBtn.setTooltip(new Tooltip("Synthesizes top module"));
		simulateBtn.setTooltip(new Tooltip("Simulates for selected Modules item in Project Explorer."));
		generatePrgFileBtn.setTooltip(new Tooltip("Generates programming file for top module"));
		
		update();
	}

	@Override
	protected void createProject() {
		fnPack.newProject();
	}

	@Override
	protected void openProject() {
		fnPack.openProject();
	}

	@Override
	protected void synthesize() {
		fnPack.synthesize();
	}

	@Override
	protected void simulate() {
		String module = dvm.getSelectedModuleName();
		if (module == null) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setContentText("Please select correct module");
			alert.showAndWait();
		} else {
			fnPack.simulate(module);
		}
	}

	@Override
	protected void generatePrgFile() {
		fnPack.generatePrgFile();
	}

	@Override
	protected void filter() {
		mvm.filterSynthesisReport();
	}

	@Override
	protected void generateVerilogTestFixture() {
		// TODO
		File source_file = dvm.getselectedVerilogName();
		String dest_name = source_file.toString().replace(".v", "_test");
		
		TextInputDialog dialog = new TextInputDialog(dest_name);
		dialog.setHeaderText("Creating Verilog Test Fixture...");
		dialog.setContentText("Enter Verilog test fixture name:");
		
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()) {
			File generatedFile;
			if ((generatedFile = VerilogTestFixtureGenerator.parse(source_file, result.get())) != null) {
				try {
					Desktop.getDesktop().open(generatedFile);
				} catch (IOException e) {
					logger.log(Level.WARNING, "Error opening generated file.", e);
				}
				fnPack.addFile(generatedFile.getAbsolutePath());
				fnPack.update();
			} else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setContentText("Error generating Verilog test fixture.");
				alert.show();
			}
		}
	}

}
