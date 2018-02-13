package com.xilinxlite.gui.functions;

/**
 * Allows storing a target Updateable object and notifies it to update.
 * 
 * @author Ong Hock Leng
 *
 */
public class UpdateController implements Updateable {

	private Updateable updateTarget = () -> {
	};

	/**
	 * Attempts to set new update target. Input o is checked to be an instance of
	 * interface Updateable.
	 * 
	 * @param o
	 *            Expects Updateable
	 * @return True if new target is set; false if otherwise
	 */
	public boolean setUpdateTarget(Object o) {
		if (o instanceof Updateable) {
			updateTarget = (Updateable) o;
			return true;
		} else {
			clearUpdateTarget();
			return false;
		}
	}

	/**
	 * Removes update target
	 */
	public void clearUpdateTarget() {
		updateTarget = () -> {
		};
	}

	/**
	 * Notify preset Updateable to update.
	 */
	@Override
	public void update() {
		updateTarget.update();
	}
}
