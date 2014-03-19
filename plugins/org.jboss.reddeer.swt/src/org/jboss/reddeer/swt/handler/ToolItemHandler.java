package org.jboss.reddeer.swt.handler;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.ToolItem;
import org.jboss.reddeer.swt.util.Display;
import org.jboss.reddeer.swt.util.ResultRunnable;


/**
 * Contains methods that handle UI operations on {@link ToolItem} widgets. 
 * 
 * @author Lucia Jelinkova
 *
 */
public class ToolItemHandler {

	private static ToolItemHandler instance;

	private ToolItemHandler() {

	}

	/**
	 * Creates and returns instance of ToolItemHandler class
	 * 
	 * @return
	 */
	public static ToolItemHandler getInstance() {
		if (instance == null) {
			instance = new ToolItemHandler();
		}
		return instance;
	}

	/**
	 * Click the {@link ToolItem}
	 * 
	 * @param w given widgets
	 */
	public void click(final ToolItem toolItem) {
		Display.syncExec(new Runnable() {
			@Override
			public void run() {
				if (((toolItem.getStyle() & SWT.TOGGLE) != 0) ||
						((toolItem.getStyle() & SWT.CHECK) != 0) ||
						((toolItem.getStyle() & SWT.RADIO) != 0)) {
					toolItem.setSelection(!toolItem.getSelection());
				}
			}
		});

		WidgetHandler.getInstance().sendClickNotifications(toolItem);
	}
	
	/**
	 * Checks if toolitem is selected

	 * @param w	given widget
	 * @return	returns widget label text
	 */
	public boolean isSelected(final ToolItem w) {
		boolean selectionState = Display.syncExec(new ResultRunnable<Boolean>() {
			@Override
			public Boolean run() {
					return w.getSelection(); 
			}
		});
		return selectionState;
	}
}
