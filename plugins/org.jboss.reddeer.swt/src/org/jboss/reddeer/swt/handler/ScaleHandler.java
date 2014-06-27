package org.jboss.reddeer.swt.handler;

import org.jboss.reddeer.swt.api.Scale;
import org.jboss.reddeer.swt.util.Display;
import org.jboss.reddeer.swt.util.ResultRunnable;

/**
 * Contains methods for handling UI operations on
 * {@link org.eclipse.swt.widgets.Scale} widgets.
 * 
 * @author Vlado Pakan
 *
 */
public class ScaleHandler {

	private static ScaleHandler instance;

	private ScaleHandler() {

	}

	/**
	 * Gets instance of ScaleHandler.
	 * 
	 * @return instance of ScaleHandler
	 */
	public static ScaleHandler getInstance() {
		if (instance == null) {
			instance = new ScaleHandler();
		}
		return instance;
	}

	/**
	 * @deprecated Use non static method instead. Will be removed in version 0.6.
	 * @param scale to get minimum of
	 * @return minimum value
	 */
	public static int getMinimum(final Scale scale) {
		return Display.syncExec(new ResultRunnable<Integer>() {
			@Override
			public Integer run() {
				return scale.getSWTWidget().getMinimum();
			}
		});
	}

	/**
	 * @deprecated Use non static method instead. Will be removed in version 0.6.
	 * @param scale to get maximum of
	 * @return maximum value
	 */
	public static int getMaximum(final Scale scale) {
		return Display.syncExec(new ResultRunnable<Integer>() {
			@Override
			public Integer run() {
				return scale.getSWTWidget().getMaximum();
			}
		});
	}

	/**
	 * @deprecated Use non static method instead. Will be removed in version 0.6.
	 */
	public static int getSelection(final Scale scale) {
		return Display.syncExec(new ResultRunnable<Integer>() {
			@Override
			public Integer run() {
				return scale.getSWTWidget().getSelection();
			}
		});
	}

	/**
	 * @deprecated Use non static method instead. Will be removed in version 0.6.
	 */
	public static void setSelection(final Scale scale, final int value) {
		Display.syncExec(new Runnable() {
			@Override
			public void run() {
				scale.getSWTWidget().setSelection(value);
			}
		});
		WidgetHandler.getInstance()
				.sendClickNotifications(scale.getSWTWidget());
	}

	/**
	 * Gets minimum value of specified {@link org.eclipse.swt.widgets.Scale}.
	 * 
	 * @param scale scale to handle
	 * @return minimum value of specified scale
	 */
	public int getMinimum(final org.eclipse.swt.widgets.Scale scale) {
		return Display.syncExec(new ResultRunnable<Integer>() {
			@Override
			public Integer run() {
				return scale.getMinimum();
			}
		});
	}

	/**
	 * Gets maximum value of specified {@link org.eclipse.swt.widgets.Scale}.
	 * 
	 * @param scale scale to handle
	 * @return maximum value of specified scale
	 */
	public int getMaximum(final org.eclipse.swt.widgets.Scale scale) {
		return Display.syncExec(new ResultRunnable<Integer>() {
			@Override
			public Integer run() {
				return scale.getMaximum();
			}
		});
	}

	/**
	 * Gets current value of specified {@link org.eclipse.swt.widgets.Scale}.
	 * 
	 * @param scale scale to handle
	 * @return current value of specified scale
	 */
	public int getSelection(final org.eclipse.swt.widgets.Scale scale) {
		return Display.syncExec(new ResultRunnable<Integer>() {
			@Override
			public Integer run() {
				return scale.getSelection();
			}
		});
	}

	/**
	 * Sets specified {@link org.eclipse.swt.widgets.Scale} to specified value.
	 * 
	 * @param scale scale to handle
	 * @param value value to set
	 */
	public void setSelection(final org.eclipse.swt.widgets.Scale scale,
			final int value) {
		Display.syncExec(new Runnable() {
			@Override
			public void run() {
				scale.setSelection(value);
			}
		});
		WidgetHandler.getInstance().sendClickNotifications(scale);
	}
}
