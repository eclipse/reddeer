package org.jboss.reddeer.swt.handler;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.swt.api.ExpandBarItem;
import org.jboss.reddeer.swt.util.Display;
import org.jboss.reddeer.swt.util.ResultRunnable;
import org.jboss.reddeer.swt.wait.AbstractWait;
import org.jboss.reddeer.swt.wait.TimePeriod;

/**
 * Contains methods for handling UI operations on {@link ExpandBarItem} widgets.
 * 
 * @author Vlado Pakan
 *
 */
public class ExpandBarItemHandler {

	private static final Logger logger = Logger
			.getLogger(ExpandBarItemHandler.class);

	private static ExpandBarItemHandler instance;

	private ExpandBarItemHandler() {

	}

	/**
	 * Gets instance of ExpandBarItemHandler.
	 * 
	 * @return instance of ExpandBarItemHandler
	 */
	public static ExpandBarItemHandler getInstance() {
		if (instance == null) {
			instance = new ExpandBarItemHandler();
		}
		return instance;
	}

	/**
	 * Expands specified {@link ExpandBarItem} and wait for specified time period.
	 * 
	 * @param timePeriod time period to wait for after expanding
	 * @param expandBarItem expand bar item to handle
	 */
	public static void expand(TimePeriod timePeriod,
			final ExpandBarItem expandBarItem) {
		logger.debug("Expanding Expand Bar Item " + expandBarItem.getText());
		if (!expandBarItem.isExpanded()) {
			ExpandBarItemHandler.notifyExpandBar(ExpandBarItemHandler
					.createEventForExpandBar(SWT.Expand, expandBarItem),
					expandBarItem);
			Display.syncExec(new Runnable() {
				@Override
				public void run() {
					expandBarItem.getSWTWidget().setExpanded(true);
				}
			});
			AbstractWait.sleep(timePeriod);
			logger.info("Expand Bar Item " + expandBarItem.getText()
					+ " has been expanded");
		} else {
			logger.debug("Expand Bar Item " + expandBarItem.getText()
					+ " is already expanded. No action performed");
		}
	}

	/**
	 * Collapses specified {@link ExpandBarItem}.
	 * 
	 * @param expandBarItem expand bar item to handle
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
			ExpandBarItemHandler.notifyExpandBar(ExpandBarItemHandler
					.createEventForExpandBar(SWT.Collapse, expandBarItem),
					expandBarItem);
			logger.info("Expand Bar Item " + expandBarItem.getText()
					+ " has been collapsed");
		} else {
			logger.debug("Expand Bar Item " + expandBarItem.getText()
					+ " is already collapsed. No action performed");
		}
	}

	/**
	 * Notifies listeners of specified {@link ExpandBarItem} about specified event.
	 * Field for event type in specified event has to be set properly.
	 * 
	 * @param event event to notify about
	 * @param expandBarItem expand bar item to handle
	 */
	private static void notifyExpandBar(final Event event,
			final ExpandBarItem expandBarItem) {
		Display.syncExec(new Runnable() {
			public void run() {
				expandBarItem.getSWTParent().notifyListeners(event.type, event);
			}
		});
	}

	/**
	 * Creates the event for specified {@link ExpandBarItem} with specified type
	 * and empty details.
	 * 
	 * @param type type of the event
	 * @param expandBarItem expand bar item to handle
	 * @return event for specified expand bar
	 */
	private static Event createEventForExpandBar(int type,
			ExpandBarItem expandBarItem) {
		return createEventForExpandBar(type, SWT.NONE, expandBarItem);
	}

	/**
	 * Creates the event for specified {@link ExpandBarItem} with specified type
	 * and details.
	 * 
	 * @param type type of the event
	 * @param detail details of the event
	 * @param expandBarItem expand bar item to handle
	 * @return event for specified expand bar
	 */
	private static Event createEventForExpandBar(int type, int detail,
			ExpandBarItem expandBarItem) {
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
	 * Expands specified {@link ExpandItem} under specified {@link ExpandBar}.
	 * 
	 * @param expandItem expand item to handle
	 * @param expandBar parent expand bar
	 */
	public void expand(final ExpandItem expandItem, final ExpandBar expandBar) {
		notifyExpandBar(createEventForExpandBar(SWT.Expand, expandItem, expandBar),
				expandBar);
		Display.syncExec(new Runnable() {
			@Override
			public void run() {
				expandItem.setExpanded(true);
			}
		});
	}

	/**
	 * Collapses specified {@link ExpandItem} under specified {@link ExpandBar}.
	 * 
	 * @param expandItem expand item to handle
	 * @param expandBar parent expand bar
	 */
	public void collapse(final ExpandItem expandItem, final ExpandBar expandBar) {
		Display.syncExec(new Runnable() {
			@Override
			public void run() {
				expandItem.setExpanded(false);
			}
		});
		notifyExpandBar(
				createEventForExpandBar(SWT.Collapse, expandItem, expandBar),
				expandBar);
	}

	private void notifyExpandBar(final Event event, final ExpandBar expandBar) {
		Display.syncExec(new Runnable() {
			public void run() {
				expandBar.notifyListeners(event.type, event);
			}
		});
	}

	private Event createEventForExpandBar(int type, ExpandItem expandItem,
			ExpandBar expandBar) {
		return createEventForExpandBar(type, SWT.NONE, expandItem, expandBar);
	}

	private Event createEventForExpandBar(int type, int detail,
			ExpandItem expandItem, ExpandBar expandBar) {
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
	 * Gets tool tip text of specified expand item.
	 * 
	 * @param item expand item to get tool tip
	 * @return tool tip text of specified item
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
	 * Checks whether specified {@link ExpandItem} is expanded or not.
	 * 
	 * @param item item to handle
	 * @return true if expand item is expanded, false otherwise
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
	 * Gets parent {@link ExpandBar} of specified {@link ExpandItem}.
	 * 
	 * @param item to handle
	 * @return parent expand bar of specified item
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
