package com.xilinxlite.gui.functions;

import com.xilinxlite.communication.CommunicationMgr;
import com.xilinxlite.gui.LocalOrRemoteDesign;

public class LocalOrRemoteMgr extends LocalOrRemoteDesign {
	
	private CommunicationMgr mgr = null;
	
	public LocalOrRemoteMgr(CommunicationMgr mgr) {
		this.mgr = mgr;
	}

	@Override
	protected void setLocal() {
		System.err.println("setLocal() not implemented.");
	}

	@Override
	protected void setRemote() {
		System.err.println("setRemote() not implemented."); 
	}

}
