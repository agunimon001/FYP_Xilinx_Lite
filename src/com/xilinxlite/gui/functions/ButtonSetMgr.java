package com.xilinxlite.gui.functions;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.xilinxlite.communication.CommunicationMgr;
import com.xilinxlite.gui.ButtonSetDesign;

public class ButtonSetMgr extends ButtonSetDesign implements Updateable {

	private static Logger logger = Logger.getLogger(ButtonSetMgr.class.getName());

	private FunctionPack fnPack;
	private CommunicationMgr cmd;

	@Override
	public void update() {

	}

	@Override
	protected void initialize() {
		// Load FunctionPack
		fnPack = FunctionPack.getInstance();

		try {
			cmd = fnPack.getCommunicationMgr();
		} catch (NullPointerException e) {
			logger.log(Level.SEVERE, "FunctionPack cannot be loaded");
		}

	}

	@Override
	protected void createProject() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void openProject() {
		fnPack.openProject();
	}

	@Override
	protected void synthesize() {
		// TODO Auto-generated method stub

	}

}
