package com.xilinxlite.gui.functions;

import java.io.File;
import java.util.logging.Logger;

import com.xilinxlite.communication.CommunicationMgr;
import com.xilinxlite.gui.LocalOrRemoteDesign;

/**
 * Function implementation for LocalOrRemoteDesign GUI.
 * 
 * @author Ong Hock Leng
 *
 */
public class LocalOrRemoteMgr extends LocalOrRemoteDesign {

	private static final Logger logger = Logger.getLogger(LocalOrRemoteMgr.class.getName());

	private CommunicationMgr mgr = null;
	private File workingDirectoryPath;
	private File settingsFolderPath;
	private LayoutController masterLayout;
	
	public LocalOrRemoteMgr() {
		
	}

	/**
	 * Sets up instance of LocalOrRemoteMgr. Remember to call getLayout() to get the
	 * GUI.
	 * 
	 * @param mgr
	 *            Pass reference to CommunicatonMgr
	 * @param settingsFolderPath
	 *            Location for Settings folder
	 * @param workingDirectoryPath
	 *            Location for desired working directory
	 * @param masterLayout
	 *            Parent layout
	 */
	public LocalOrRemoteMgr(CommunicationMgr mgr, File settingsFolderPath, File workingDirectoryPath,
			LayoutController masterLayout) {
		this.mgr = mgr;
		this.settingsFolderPath = settingsFolderPath;
		this.workingDirectoryPath = workingDirectoryPath;
		this.masterLayout = masterLayout;
	}

	/**
	 * Launches LocalSettingMgr GUI.
	 */
	@Override
	protected void setLocal() {
		new LocalSettingMgr(mgr, settingsFolderPath, workingDirectoryPath).launch();
		if (mgr.isLocal()) {
			masterLayout.updateLayout(new ProjectViewMgr()); // to update with next layout
		}
	}

	/**
	 * Launches RemoteSettingMgr GUI.
	 */
	@Override
	protected void setRemote() {
		logger.warning("setRemote() not implemented.");
	}

}
