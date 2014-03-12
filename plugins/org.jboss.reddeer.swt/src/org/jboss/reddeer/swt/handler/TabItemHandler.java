package org.jboss.reddeer.swt.handler;

import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.jboss.reddeer.swt.util.Display;
import org.jboss.reddeer.swt.util.ResultRunnable;
/**
 * Contains methods that handle UI operations on {@link TabItem} widgets. 
 * @author Vlado Pakan
 *
 */
public class TabItemHandler {

	private static TabItemHandler instance;

	private TabItemHandler() {

	}

	/**
	 * Creates and returns instance of TabItemHandler class
	 * 
	 * @return
	 */
	public static TabItemHandler getInstance() {
		if (instance == null) {
			instance = new TabItemHandler();
		}
		return instance;
	}
	/**
	 * Return TabFolder containing swtTabItem
	 * @param swtTabItem
	 * @return
	 */
	public TabFolder getTabFolder(final TabItem swtTabItem) {
		return Display
				.syncExec(new ResultRunnable<org.eclipse.swt.widgets.TabFolder>() {
					@Override
					public org.eclipse.swt.widgets.TabFolder run() {
						return swtTabItem.getParent();
					}
				});
	}
	/**
	 * Notifies TabFolder listeners about event event.type field has to be
	 * properly set
	 * 
	 * @param event
	 */
	public void notifyTabFolder(final Event event , final TabFolder swtTabFolder) {
		Display.syncExec(new Runnable() {
			public void run() {
				swtTabFolder.notifyListeners(event.type, event);
			}
		});
	}
	/**
	 * Creates event for TabItem with specified type
	 * 
	 * @param swtTabItem
	 * @param type
	 * @return
	 */
	public Event createEventForTabItem(final TabItem swtTabItem, int type) {
		Event event = new Event();
		event.type = type;
		event.display = Display.getDisplay();
		event.time = (int) System.currentTimeMillis();
		event.item = swtTabItem;
		event.widget = getTabFolder(swtTabItem);
		return event;
	}
	/**
	 * Selects swtTabItem
	 * 
	 * @param swtTabItem
	 */
	public void select (final TabItem swtTabItem){
		Display.syncExec(new Runnable() {
			public void run() {
				swtTabItem.getParent().setSelection(swtTabItem);
			}
		});
	}

	public void setFocus(final TabItem tabItem) {
		Display.syncExec(new Runnable() {
			@Override
			public void run() {
				tabItem.getParent().forceFocus();
			}
		});
	}
}
