package org.jboss.reddeer.swt.handler;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;
import org.jboss.reddeer.junit.logging.Logger;
import org.jboss.reddeer.swt.api.ExpandBarItem;
import org.jboss.reddeer.swt.util.Display;
import org.jboss.reddeer.swt.util.ResultRunnable;
import org.jboss.reddeer.swt.wait.AbstractWait;
import org.jboss.reddeer.swt.wait.TimePeriod;

/**
 * Contains methods that handle UI operations on {@link ExpandItem} widgets.  
 *  
 * @author Vlado Pakan
 *
 */
public class ExpandBarItemHandler {

	private static final Logger logger = Logger.getLogger(ExpandBarItemHandler.class);

	private static ExpandBarItemHandler instance;

	private ExpandBarItemHandler() {

	}

	/**
	 * Creates and returns instance of ComboHandler class
	 * 
	 * @return
	 */
	public static ExpandBarItemHandler getInstance() {
		if (instance == null) {
			instance = new ExpandBarItemHandler();
		}
		return instance;
	}

	/**
	 * See {@link ExpandBarItem}
	 * @param timePeriod
	 * @param expandBarItem
	 */
	public static void expand(TimePeriod timePeriod , final ExpandBarItem expandBarItem) {
		logger.debug("Expanding Expand Bar Item " + expandBarItem.getText());
		if (!expandBarItem.isExpanded()) {
			ExpandBarItemHandler.notifyExpandBar(
					ExpandBarItemHandler.createEventForExpandBar(SWT.Expand,expandBarItem),expandBarItem);
			Display.syncExec(new Runnable() {
				@Override
				public void run() {
					expandBarItem.getSWTWidget().setExpanded(true);
				}
			});
			AbstractWait.sleep(timePeriod.getSeconds()*1000);
			logger.info("Expand Bar Item " + expandBarItem.getText()
					+ " has been expanded");
		} else {
			logger.debug("Expand Bar Item " + expandBarItem.getText()
					+ " is already expanded. No action performed");
		}
	}
	/**
	 * See {@link ExpandBarItem}
	 * @param expandBarItem
	 */
	public static void collapse(final ExpandBarItem expandBarItem) {
		logger.debug("Collapsing Expand Bar Item " + expandBarItem.getText());
		if (expandBarItem.isExpanded()) {
			Display.syncExec(new Runnable() {
				@Override
				public void run() {
					expandBarItem.getSWTWidget().setExpanded(false);
				}
			});
			ExpandBarItemHandler.notifyExpandBar(
					ExpandBarItemHandler.createEventForExpandBar(SWT.Collapse,expandBarItem),expandBarItem);
			logger.info("Expand Bar Item " + expandBarItem.getText()
					+ " has been collapsed");
		} else {
			logger.debug("Expand Bar Item " + expandBarItem.getText()
					+ " is already collapsed. No action performed");
		}
	}

	/**
	 * Notifies Expand Bar listeners about event event.type field has too be properly
	 * set
	 * 
	 * @param event
	 * @param expandBarItem
	 */
	private static void notifyExpandBar(final Event event , final ExpandBarItem expandBarItem) {
		Display.syncExec(new Runnable() {
			public void run() {
				expandBarItem.getSWTParent().notifyListeners(event.type, event);
			}
		});
	}
	/**
	 * Creates event for Expand Bar with specified type and empty detail
	 * 
	 * @param type
	 * @param expandBarItem
	 * @return
	 */
	private static Event createEventForExpandBar(int type , ExpandBarItem expandBarItem) {
		return createEventForExpandBar(type, SWT.NONE , expandBarItem);
	}

	/**
	 * Creates event for Expand Bar with specified type and detail
	 * 
	 * @param type
	 * @param detail
	 * @param expandBarItem
	 * @return
	 */
	private static Event createEventForExpandBar(int type, int detail , ExpandBarItem expandBarItem) {
		Event event = new Event();
		event.type = type;
		event.display = Display.getDisplay();
		event.time = (int) System.currentTimeMillis();
		event.item = expandBarItem.getSWTWidget();
		event.widget = expandBarItem.getSWTParent();
		event.detail = detail;
		return event;
	}

	/**
	 * See {@link ExpandBarItem}
	 * @param timePeriod
	 * @param expandBarItem
	 */
	public void expand(final ExpandItem expandItem, final ExpandBar expandBar) {
		notifyExpandBar(createEventForExpandBar(SWT.Expand,expandItem, expandBar),expandBar);
		Display.syncExec(new Runnable() {
			@Override
			public void run() {
				expandItem.setExpanded(true);
			}
		});
	}
	/**
	 * See {@link ExpandBarItem}
	 * @param expandBarItem
	 */
	public void collapse(final ExpandItem expandItem, final ExpandBar expandBar) {
		Display.syncExec(new Runnable() {
			@Override
			public void run() {
				expandItem.setExpanded(false);
			}
		});
		notifyExpandBar(createEventForExpandBar(SWT.Collapse,expandItem, expandBar),expandBar);
	}

	/**
	 * Notifies Expand Bar listeners about event event.type field has too be properly
	 * set
	 * 
	 * @param event
	 * @param expandBarItem
	 */
	private void notifyExpandBar(final Event event, final ExpandBar expandBar) {
		Display.syncExec(new Runnable() {
			public void run() {
				expandBar.notifyListeners(event.type, event);
			}
		});
	}
	/**
	 * Creates event for Expand Bar with specified type and empty detail
	 * 
	 * @param type
	 * @param expandBarItem
	 * @return
	 */
	private Event createEventForExpandBar(int type , ExpandItem expandItem, ExpandBar expandBar) {
		return createEventForExpandBar(type, SWT.NONE , expandItem, expandBar);
	}

	/**
	 * Creates event for Expand Bar with specified type and detail
	 * 
	 * @param type
	 * @param detail
	 * @param expandBarItem
	 * @return
	 */
	private Event createEventForExpandBar(int type, int detail , ExpandItem expandItem, ExpandBar expandBar) {
		Event event = new Event();
		event.type = type;
		event.display = Display.getDisplay();
		event.time = (int) System.currentTimeMillis();
		event.item = expandItem;
		event.widget = expandBar;
		event.detail = detail;
		return event;
	}

	/**
	 * Gets tooltip if supported widget type
	 * 
	 * @param widget
	 * @return widget text
	 */
	public String getToolTipText(final ExpandItem item) {
		String text = Display.syncExec(new ResultRunnable<String>() {
			@Override
			public String run() {
				return item.getParent().getToolTipText();
			}
		});
		return text;
	}

	/**
	 * Checks if supported widget is expanded
	 * 
	 * @param item	given widget
	 * @return	true if widget is expanded
	 */
	public boolean isExpanded(final ExpandItem item) {
		return Display.syncExec(new ResultRunnable<Boolean>() {

			@Override
			public Boolean run() {
				return item.getExpanded();
			}
		});
	}

	/**
	 * Returns parent for supported widget
	 * 
	 * @param item	given widget
	 * @return	parent widget
	 */
	public ExpandBar getParent(final ExpandItem item) {
		return Display.syncExec(new ResultRunnable<ExpandBar>() {
			@Override
			public ExpandBar run() {
				return item.getParent();
			}
		});
	}
}
