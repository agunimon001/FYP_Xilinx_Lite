package com.xilinxlite.gui.functions;

import com.xilinxlite.gui.SummaryTableDesign;

/**
 * Function implementation for SummaryTableDesign.
 * 
 * @author Ong Hock Leng
 *
 */
public class SummaryTableMgr extends SummaryTableDesign implements Updateable {

	private FunctionPack fnPack = null;

	/**
	 * Constructor.
	 * 
	 * @param cmdMgr
	 */
	public SummaryTableMgr() {
		fnPack = FunctionPack.getInstance();
		updateConnectionStatus();
	}

	@Override
	protected void updateProjectTitle() {
		projectTitle.setText(fnPack.getProjectName());
	}

	@Override
	protected void updateConnectionStatus() {
		connectionStatus.setText(fnPack.getConnectionStatus());
	}

	@Override
	public void update() {
		updateProjectTitle();
		updateConnectionStatus();
		updateWorkingDirectory();
	}

	@Override
	protected void setStatus(String message) {
		status.setText(message);
	}

	@Override
	protected void updateWorkingDirectory() {
		workingDirectory.setText(fnPack.getWorkingDirectory());
	}

}
