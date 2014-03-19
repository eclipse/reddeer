package org.jboss.reddeer.swt.handler;

import org.jboss.reddeer.swt.api.Scale;
import org.jboss.reddeer.swt.util.Display;
import org.jboss.reddeer.swt.util.ResultRunnable;

/**
 * Contains methods that handle UI operations on {@link org.eclipse.swt.widgets.Scale} widgets. 
 * @author Vlado Pakan
 *
 */
public class ScaleHandler {
	
	private static ScaleHandler instance;

	private ScaleHandler() {

	}

	/**
	 * Creates and returns instance of TreeHandler class
	 * 
	 * @return
	 */
	public static ScaleHandler getInstance() {
		if (instance == null) {
			instance = new ScaleHandler();
		}
		return instance;
	}
	
	/**
	 * @deprecated The methods in handler should not be static and should use SWT widgets as arguments. Use ScaleHandler.getInstance and appropriate non static method
	 * See {@link Scale}
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
	 * See {@link Scale}
	 * @deprecated The methods in handler should not be static and should use SWT widgets as arguments. Use ScaleHandler.getInstance and appropriate non static method
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
	 * @deprecated The methods in handler should not be static and should use SWT widgets as arguments. Use ScaleHandler.getInstance and appropriate non static method
	 * See {@link Scale}
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
	 * @deprecated The methods in handler should not be static and should use SWT widgets as arguments. Use ScaleHandler.getInstance and appropriate non static method
	 * See {@link Scale}
	 */
	public static void setSelection(final Scale scale, final int value) {
		Display.syncExec(new Runnable() {
			@Override
			public void run() {
				scale.getSWTWidget().setSelection(value);
			}
		});
		WidgetHandler.getInstance().sendClickNotifications(scale.getSWTWidget());
	}
	
	/**
	 * See {@link Scale}
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
	 * See {@link Scale}
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
	 * See {@link Scale}
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
	 * See {@link Scale}
	 */
	public void setSelection(final org.eclipse.swt.widgets.Scale scale, final int value) {
		Display.syncExec(new Runnable() {
			@Override
			public void run() {
				scale.setSelection(value);
			}
		});
		WidgetHandler.getInstance().sendClickNotifications(scale);
	}
}
