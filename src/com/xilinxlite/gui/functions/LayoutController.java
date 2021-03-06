package com.xilinxlite.gui.functions;

import com.xilinxlite.gui.DesignManager;

/**
 * Interface for updating target layout. Implement this on the parent layout class.
 * 
 * @author Ong Hock Leng
 *
 */
public interface LayoutController {

	/**
	 * Updates with targeted layout.
	 * 
	 * @param dm layout
	 */
	public void updateLayout(DesignManager dm);

}
