package org.jboss.reddeer.swt.handler;

import org.jboss.reddeer.swt.api.Scale;
import org.jboss.reddeer.swt.lookup.WidgetLookup;
import org.jboss.reddeer.swt.util.Display;
import org.jboss.reddeer.swt.util.ResultRunnable;

/**
 * Implements Scale UI methods
 * @author Vlado Pakan
 *
 */
public class ScaleHandler {
	/**
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
	 * See {@link Scale}
	 */
	public static void setSelection(final Scale scale, final int value) {
		Display.syncExec(new Runnable() {
			@Override
			public void run() {
				scale.getSWTWidget().setSelection(value);
			}
		});
		WidgetLookup.getInstance().sendClickNotifications(scale.getSWTWidget());
	}
	
}
