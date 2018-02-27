package com.xilinxlite.gui.functions;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.xilinxlite.gui.ButtonSetDesign;

/**
 * Function implementation for ButtonSetDesign.
 * 
 * @author Ong Hock Leng
 *
 */
public class ButtonSetMgr extends ButtonSetDesign implements Updateable {

	private static Logger logger = Logger.getLogger(ButtonSetMgr.class.getName());

	private FunctionPack fnPack;

	@Override
	public void update() {
		synthesizeBtn.setDisable(fnPack.isProjectClosed());
	}

	@Override
	protected void initialize() {
		// Load FunctionPack
		fnPack = FunctionPack.getInstance();
		if (fnPack == null) {
			logger.log(Level.SEVERE, "No FunctionPack detected.", new Exception());
		}
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

}
