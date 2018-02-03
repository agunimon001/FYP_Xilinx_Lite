package com.xilinxlite.gui.functions;

import com.xilinxlite.communication.CommunicationMgr;
import com.xilinxlite.gui.SummaryTableDesign;

public class SummaryTableMgr extends SummaryTableDesign implements Updateable {

	CommunicationMgr cmdMgr;

	public SummaryTableMgr(CommunicationMgr cmdMgr) {
		this.cmdMgr = cmdMgr;
	}

	@Override
	protected void updateProjectTitle() {
		projectTitle.setText(cmdMgr.getProjectName());
	}

	@Override
	protected void updateConnectionStatus() {
		connectionStatus.setText(cmdMgr.getConnectiontype().toString());
	}

	@Override
	public void update() {
		updateProjectTitle();
		updateConnectionStatus();
	}

}
