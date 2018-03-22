package com.xilinxlite.gui.functions;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.xilinxlite.gui.ButtonSetDesign;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

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
	
	public void setDirectoryViewMgr(DirectoryViewMgr dvm) {
		this.dvm = dvm;
	}

	@Override
	public void update() {
		boolean projectClosed = fnPack.isProjectClosed();
		synthesizeBtn.setDisable(projectClosed);
		simulateBtn.setDisable(projectClosed);
		generatePrgFileBtn.setDisable(projectClosed);
	}

	@Override
	protected void initialize() {
		// Load FunctionPack
		fnPack = FunctionPack.getInstance();
		if (fnPack == null) {
			logger.log(Level.SEVERE, "No FunctionPack detected.", new Exception());
		}
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

}
