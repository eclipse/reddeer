package org.jboss.reddeer.core.handler;

import org.eclipse.swt.widgets.Spinner;
import org.jboss.reddeer.core.util.Display;
import org.jboss.reddeer.core.util.ResultRunnable;

/**
 * Contains methods for handling UI operations on {@link Spinner} widgets.
 * 
 * @author Lucia Jelinkova
 *
 */
public class SpinnerHandler {

	private static SpinnerHandler instance;

	private SpinnerHandler() {

	}

	/**
	 * Gets instance of SpinnerHandler.
	 * 
	 * @return instance of SpinnerHandler
	 */
	public static SpinnerHandler getInstance() {
		if (instance == null) {
			instance = new SpinnerHandler();
		}
		return instance;
	}

	/**
	 * Gets current value of specified {@link Spinner}.
	 * 
	 * @param spinner spinner to handle
	 * @return current value of specified spinner
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
	 * Sets value of specified {@link Spinner} to specified value.
	 * 
	 * @param spinner spinner to handle
	 * @param value value to set
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
