package com.xilinxlite.gui.functions;

import java.util.ArrayList;
import java.util.List;

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

	List<Updateable> updateList = new ArrayList<Updateable>();

	private DirectoryViewMgr dvMgr = null;
	private MessageViewMgr mvMgr = null;

	@Override
	protected Pane setSummaryTable() {
		SummaryTableMgr pane = new SummaryTableMgr();
		updateList.add(pane);
		return pane.getLayout();
	}

	@Override
	protected Pane setDirectoryView() {
		DirectoryViewMgr pane = new DirectoryViewMgr();
		updateList.add(pane);
		dvMgr = pane;
		if (mvMgr != null) {
			dvMgr.setMessageViewMgr(mvMgr);
		}
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
		mvMgr = pane;
		if (dvMgr != null) {
			dvMgr.setMessageViewMgr(mvMgr);
		}
		return pane.getLayout();
	}

	@Override
	public void update() {
		for (Updateable u : updateList) {
			u.update();
		}
	}

}
