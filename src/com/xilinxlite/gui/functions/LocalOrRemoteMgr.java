package com.xilinxlite.gui.functions;

import java.util.logging.Logger;

import com.xilinxlite.communication.CommunicationMgr;
import com.xilinxlite.gui.DesignManager;
import com.xilinxlite.gui.LocalOrRemoteDesign;

/**
 * Function implementation for LocalOrRemoteDesign GUI.
 * 
 * @author Ong Hock Leng
 *
 */
public class LocalOrRemoteMgr extends LocalOrRemoteDesign implements DesignManager {

	private static final Logger logger = Logger.getLogger(LocalOrRemoteMgr.class.getName());

	private CommunicationMgr mgr = null;
	private String settingsFolderPath;
	private LayoutController masterLayout;

	/**
	 * Sets up instance of LocalOrRemoteMgr. Remember to call getLayout() to get the GUI.
	 * 
	 * @param mgr Pass reference to CommunicatonMgr
	 * @param settingsFolderPath Location for Settings folder
	 */
	public LocalOrRemoteMgr(CommunicationMgr mgr, String settingsFolderPath, LayoutController masterLayout) {
		this.mgr = mgr;
		this.settingsFolderPath = settingsFolderPath;
		this.masterLayout = masterLayout;
	}

	/**
	 * Launches LocalSettingMgr GUI.
	 */
	@Override
	protected void setLocal() {
		new LocalSettingMgr(mgr, settingsFolderPath).launch();
		if (mgr.isLocal()) {
			masterLayout.updateLayout(new ProjectViewMgr(mgr));		// to update with next layout
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
