package org.jboss.reddeer.swt.impl.progressbar;


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
		return ProgressBarHandler.getInstance().getMaximum(widget);
	}
	
	/**
	 * 
	 * @return minimum value of this progressbar
	 */
	
	public int getMinimum(){
		return ProgressBarHandler.getInstance().getMinimum(widget);
	}
	
	/**
	 * 
	 * @return current value of this progressbar
	 */
	
	public int getSelection(){
		return ProgressBarHandler.getInstance().getSelection(widget);
	}

}
