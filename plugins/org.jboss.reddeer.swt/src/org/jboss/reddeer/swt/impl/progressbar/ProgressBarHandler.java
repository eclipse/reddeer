package org.jboss.reddeer.swt.impl.progressbar;

import org.eclipse.swt.widgets.ProgressBar;
import org.jboss.reddeer.core.util.Display;
import org.jboss.reddeer.core.util.ResultRunnable;

/**
 *	Contains methods that handle UI operations on {@link ProgressBar} widgets. 
 * 
 * @author rhopp
 *
 */


public class ProgressBarHandler {
	
	private static ProgressBarHandler instance;
	
	private ProgressBarHandler() { }
	
	/**
	 * Creates and returns instance of ProgressBarHandler class
	 * 
	 * @return
	 */
	public static ProgressBarHandler getInstance(){
		if (instance == null){
			instance = new ProgressBarHandler();
		}
		return instance;
	}
	
	
	/**
	 * Returns state of {@link ProgressBar}
	 * 
	 * @param widget
	 * @return
	 */
	
	public int getState(final ProgressBar widget){
		return Display.syncExec(new ResultRunnable<Integer>() {
			@Override
			public Integer run() {
				return widget.getState();
			}
		});
	}
	
	/**
	 * Returns maximum of {@link ProgressBar}
	 * 
	 * @param widget
	 * @return
	 */
	
	public int getMaximum(final ProgressBar widget){
		return Display.syncExec(new ResultRunnable<Integer>() {
			@Override
			public Integer run() {
				return widget.getMaximum();
			}
		});
	}
	
	/**
	 * Returns minimum of {@link ProgressBar}
	 * 
	 * @param widget
	 * @return
	 */
	
	public int getMinimum(final ProgressBar widget){
		return Display.syncExec(new ResultRunnable<Integer>() {
			@Override
			public Integer run() {
				return widget.getMinimum();
			}
		});
	}
	
	/**
	 * Returns selection of {@link ProgressBar}
	 * 
	 * @param widget
	 * @return
	 */
	
	public int getSelection(final ProgressBar widget){
		return Display.syncExec(new ResultRunnable<Integer>() {
			@Override
			public Integer run() {
				return widget.getSelection();
			}
		});
	}

}
