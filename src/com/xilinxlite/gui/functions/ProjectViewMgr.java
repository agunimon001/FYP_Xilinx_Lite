package com.xilinxlite.gui.functions;

import java.util.ArrayList;
import java.util.List;

import com.xilinxlite.communication.CommunicationMgr;
import com.xilinxlite.gui.ProjectViewDesign;

import javafx.scene.layout.Pane;

/**
 * Implementation of ProjectViewDesign's functions. Layout container for project
 * view.
 * 
 * @author Ong Hock Leng
 *
 */
public class ProjectViewMgr extends ProjectViewDesign implements Updateable {

	private CommunicationMgr cmdMgr = null;
	List<Updateable> updateList = new ArrayList<Updateable>();

	/**
	 * Constructor.
	 * 
	 * @param cmdMgr
	 *            CommunicationMgr instance
	 */
	public ProjectViewMgr(CommunicationMgr cmdMgr) {
		this.cmdMgr = cmdMgr;
	}

	@Override
	protected Pane setSummaryTable() {
		SummaryTableMgr pane = new SummaryTableMgr(cmdMgr);
		updateList.add(pane);
		return pane.getLayout();
	}

	@Override
	protected Pane setDirectoryView() {
		DirectoryViewMgr pane = new DirectoryViewMgr(cmdMgr);
		updateList.add(pane);
		return pane.getLayout();
	}

	@Override
	protected Pane setButtonSet() {
		ButtonSetMgr pane = new ButtonSetMgr();
		updateList.add(pane);
		return pane.getLayout();
	}

	@Override
	protected Pane setMessageView() {
		MessageViewMgr pane = new MessageViewMgr();
		updateList.add(pane);
		return pane.getLayout();
	}

	@Override
	public void update() {
		for (Updateable u : updateList) {
			u.update();
		}
	}

}
