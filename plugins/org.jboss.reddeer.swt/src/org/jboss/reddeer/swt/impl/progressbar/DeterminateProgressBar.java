package org.jboss.reddeer.swt.impl.progressbar;

import org.jboss.reddeer.swt.util.Display;
import org.jboss.reddeer.swt.util.ResultRunnable;

/**
 * This class represents determinate progress bars (both horizontal and vertical) with possibility to read status. 
 * 
 * @author rhopp
 *
 */

public abstract class DeterminateProgressBar extends AbstractProgressBar {
	
	protected DeterminateProgressBar(int index, int style) {
		super(index, style);
	}
	
	/**
	 * 
	 * @return maximum value of this progressbar
	 */
	
	public int getMaximum(){
		return Display.syncExec(new ResultRunnable<Integer>() {
			@Override
			public Integer run() {
				return widget.getMaximum();
			}
		});
	}
	
	/**
	 * 
	 * @return minimum value of this progressbar
	 */
	
	public int getMinimum(){
		return Display.syncExec(new ResultRunnable<Integer>() {
			@Override
			public Integer run() {
				return widget.getMinimum();
			}
		});
	}
	
	/**
	 * 
	 * @return current value of this progressbar
	 */
	
	public int getSelection(){
		return Display.syncExec(new ResultRunnable<Integer>() {
			@Override
			public Integer run() {
				return widget.getSelection();
			}
		});
	}

}
