package org.jboss.reddeer.swt.handler;

import org.eclipse.swt.widgets.Spinner;
import org.jboss.reddeer.swt.util.Display;
import org.jboss.reddeer.swt.util.ResultRunnable;

/**
 * Contains methods that handle UI operations on {@link Spinner} widgets. 
 * 
 * @author Lucia Jelinkova
 *
 */
public class SpinnerHandler {

	private static SpinnerHandler instance;

	private SpinnerHandler() {

	}

	/**
	 * Creates and returns instance of ComboHandler class
	 * 
	 * @return
	 */
	public static SpinnerHandler getInstance() {
		if (instance == null) {
			instance = new SpinnerHandler();
		}
		return instance;
	}

	/**
	 * Get value of supported widget
	 * 
	 * @param spinner widget
	 * @return value of the widget
	 */
	public int getValue(final Spinner spinner) {
		return Display.syncExec(new ResultRunnable<Integer>() {

			@Override
			public Integer run() {
				return spinner.getSelection();
			}
		});
	}

	/**
	 * Set value of supported widget
	 * 
	 * @param spinner widget
	 * @param value value of the widget
	 */
	public void setValue(final Spinner spinner, final int value) {
		Display.syncExec(new Runnable() {

			@Override
			public void run() {
				spinner.setSelection(value);
			}
		});
	}
}
