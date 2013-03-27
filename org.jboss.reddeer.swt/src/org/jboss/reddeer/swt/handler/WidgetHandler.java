package org.jboss.reddeer.swt.handler;

import org.eclipse.swt.widgets.Text;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.swt.util.Display;
import org.jboss.reddeer.swt.util.ResultRunnable;

/**
 * Handler operating basic widgets
 * Create instance via getInstance() method
 * Currently supported:
 * <ul>
 * <li>Text</li>
 * </ul>
 * @author Jiri Peterka
 *
 */
public class WidgetHandler {

	private static WidgetHandler instance;
	private WidgetHandler() {
		
	}
	/**
	 * Creates and returns widget of WidgetHandler class
	 * @return
	 */
	public static WidgetHandler getInstance() {
		if (instance == null) {
			instance = new WidgetHandler();
		}
		return instance;
	}
	
	/**
	 * Set text for supported widget type
	 * @param w given widgets
	 * @param text text to be set
	 */
	public <T> void setText(final T w, final String text) {
		Display.syncExec(new Runnable() {

			@Override
			public void run() {
				if (w instanceof Text) ((Text)w).setText(text);
				else throw new SWTLayerException("Unuspported type");
				
			}
		});
	}
	/**
	 * Gets text for supported widget type
	 * @param w given widget
	 * @return returns widget text
	 */
	public <T> String getText(final T w) {
		String text = Display.syncExec(new ResultRunnable<String>() {

			@Override
			public String run() {
				if (w instanceof Text)
					return ((Text)w).getText();
				else throw new SWTLayerException("Unuspported type");
			}
		});
		return text;
	}

	/**
	 * Gets tooltip if supported widget type
	 * @param widget
	 * @return widget text
	 */
	public <T> String getToolTipText(final T w) {
		String text = Display.asynExec(new ResultRunnable<String>() {

			@Override
			public String run() {
				if (w instanceof Text)
					return ((Text)w).getToolTipText();
				else throw new SWTLayerException("Unuspported type");
			}
		});
		return text;
	}
	
}
