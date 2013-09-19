package org.jboss.reddeer.swt.handler;

import org.jboss.reddeer.junit.logging.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.jboss.reddeer.swt.api.ExpandBarItem;
import org.jboss.reddeer.swt.util.Display;
import org.jboss.reddeer.swt.wait.AbstractWait;
import org.jboss.reddeer.swt.wait.TimePeriod;

/**
 * Implements ExpandBarItem UI methods
 * @author Vlado Pakan
 *
 */
public class ExpandBarItemHandler {
	private static final Logger logger = Logger.getLogger(ExpandBarItemHandler.class);
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
}
