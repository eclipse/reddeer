package org.jboss.reddeer.swt.handler;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.jboss.reddeer.swt.util.Display;
import org.jboss.reddeer.swt.util.ResultRunnable;


/**
 * Contains methods for handling UI operations on {@link ToolItem} widgets. 
 * 
 * @author Lucia Jelinkova
 *
 */
public class ToolItemHandler {

	private static ToolItemHandler instance;

	private ToolItemHandler() {

	}

	/**
	 * Gets instance of ToolItemHandler.
	 * 
	 * @return instance of ToolItemHandler
	 */
	public static ToolItemHandler getInstance() {
		if (instance == null) {
			instance = new ToolItemHandler();
		}
		return instance;
	}

	/**
	 * Click specified {@link ToolItem}.
	 * 
	 * @param toolItem tool item to handle
	 */
	public void click(final ToolItem toolItem) {
		Display.syncExec(new Runnable() {
			@Override
			public void run() {
				if (((toolItem.getStyle() & SWT.TOGGLE) != 0)
						|| ((toolItem.getStyle() & SWT.CHECK) != 0)
						|| ((toolItem.getStyle() & SWT.RADIO) != 0)) {
					toolItem.setSelection(!toolItem.getSelection());
				}
			}
		});

		WidgetHandler.getInstance().sendClickNotifications(toolItem);
	}
	
	/**
	 * Finds out whether specified {@link ToolItem} is selected or not.

	 * @param toolItem tool item to handle
	 * @return true if specified tool item is selected, false otherwise
	 */
	public boolean isSelected(final ToolItem toolItem) {
		boolean selectionState = Display.syncExec(new ResultRunnable<Boolean>() {
			@Override
			public Boolean run() {
					return toolItem.getSelection(); 
			}
		});
		return selectionState;
	}
	
	/**
	 * Returns ToolBar to which this ToolItem belongs to.
	 * 
	 * @param item
	 * @return
	 */
	
	public ToolBar getParent(final ToolItem item){
		return Display.syncExec(new ResultRunnable<ToolBar>() {
			@Override
			public ToolBar run() {
				return item.getParent();
			}
		});
	}
}
