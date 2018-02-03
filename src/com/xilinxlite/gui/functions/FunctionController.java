package com.xilinxlite.gui.functions;

public class FunctionController {

	private Updateable updateTarget = () -> {};

	public boolean setUpdateTarget(Object o) {
		if (o instanceof Updateable) {
			updateTarget = (Updateable) o;
			return true;
		} else {
			clearUpdateTarget();
			return false;
		}
	}
	
	public void clearUpdateTarget() {
		updateTarget = () -> {};
	}
	
	public void update() {
		updateTarget.update();
	}
}
