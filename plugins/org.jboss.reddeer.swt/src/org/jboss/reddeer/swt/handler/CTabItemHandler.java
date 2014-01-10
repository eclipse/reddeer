package org.jboss.reddeer.swt.handler;

import java.lang.reflect.Field;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Event;
import org.jboss.reddeer.junit.logging.Logger;
import org.jboss.reddeer.swt.util.Display;
import org.jboss.reddeer.swt.util.ResultRunnable;

/**
 * CTabItem handler handles operations for SWT CTabItem instances
 * @author Vlado Pakan
 *
 */
public class CTabItemHandler {
	
	private final Logger logger = Logger.getLogger(this.getClass());	  
	private static CTabItemHandler instance;

	private CTabItemHandler() {

	}
	/**
	 * Returns cTabFolder containing swtCTabItem 
	 * @param swtCTabItem
	 * @return
	 */
	public CTabFolder getCTabFolder (final CTabItem swtCTabItem){
		return Display.syncExec(new ResultRunnable<org.eclipse.swt.custom.CTabFolder>() {
			@Override
			public CTabFolder run() {
				return swtCTabItem.getParent();
			}
		});
	}
	/**
	 * Creates and returns instance of CTabItemHandler class
	 * 
	 * @return
	 */
	public static CTabItemHandler getInstance() {
		if (instance == null) {
			instance = new CTabItemHandler();
		}
		return instance;
	}
	
	/**
	 * Creates event for CTabItem with specified type
	 * 
	 * @param swtCTabItem
	 * @param type
	 * @return
	 */
	public Event createEventForCTabItem(final CTabItem swtCTabItem, final int type) {
		Event event = new Event();
		event.type = type;
		event.display = Display.getDisplay();
		event.time = (int) System.currentTimeMillis();
		event.item = swtCTabItem;
		event.widget = getCTabFolder(swtCTabItem);
		return event;
	}

	/**
	 * Notifies CTabFolder listeners about event event.type field has to be
	 * properly set
	 * 
	 * @param swtCTabItem
	 * @param event
	 */
	public void notifyCTabFolder(final CTabItem swtCTabItem,final Event event) {
		Display.syncExec(new Runnable() {
			public void run() {
				swtCTabItem.getParent().notifyListeners(event.type, event);
			}
		});
	}

	/**
	 * Clicks close button on position x, y
	 * 
	 * @param swtCTabItem
	 */
	public void clickCloseButton(final CTabItem swtCTabItem) {
		Rectangle rectangleCloseBox = Display
				.syncExec(new ResultRunnable<Rectangle>() {
					public Rectangle run() {
						try {
							Field field = org.eclipse.swt.custom.CTabItem.class
									.getDeclaredField("closeRect");
							field.setAccessible(true);
							return (Rectangle) field.get(swtCTabItem);
						} catch (Exception e) {

						}
						return null;
					}
				});
		logger.info("Clicking on close button");
		int x = rectangleCloseBox.x + (rectangleCloseBox.width / 2);
		int y = rectangleCloseBox.y	+ (rectangleCloseBox.height / 2);
		notifyCTabFolder(swtCTabItem,createMouseEvent(swtCTabItem,SWT.MouseDown,x, y, 1, SWT.NONE, 1));
		// this event being button 1 is what allows CTabItem to close
		notifyCTabFolder(swtCTabItem,createMouseEvent(swtCTabItem,SWT.MouseUp,x, y, 1, SWT.BUTTON1, 1));
	}
	/**
	 * Create a mouse event
	 *
	 * @param swtCTabItem
	 * @param x
	 * @param y
	 * @param button
	 * @param stateMask
	 * @param count
	 * @return
	 */
	private Event createMouseEvent(final CTabItem swtCTabItem,int type , int x, int y, int button, int stateMask, int count) {
		Event event = createEventForCTabItem(swtCTabItem,type);
		event.time = (int) System.currentTimeMillis();
		event.x = x;
		event.y = y;
		event.button = button;
		event.stateMask = stateMask;
		event.count = count;
		return event;
	}
	
	public boolean isShowClose(final CTabItem swtCTabItem) {
		return Display.syncExec(new ResultRunnable<Boolean>() {
			public Boolean run() {
				return swtCTabItem.getShowClose();
			}
		});
	}
	/**
	 * Selects swtCTabItem
	 * @param swtCTabItem
	 */
	public void select (final CTabItem swtCTabItem){
		Display.syncExec(new Runnable() {
			public void run() {
				swtCTabItem.getParent().setSelection(swtCTabItem);
			}
		});
	}
}
