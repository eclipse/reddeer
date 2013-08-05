package org.jboss.reddeer.swt.impl.ctab;

import java.lang.reflect.Field;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Event;
import org.jboss.reddeer.swt.api.CTabItem;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.swt.handler.WidgetHandler;
import org.jboss.reddeer.swt.util.Display;
import org.jboss.reddeer.swt.util.ResultRunnable;

/**
 * Abstract class for all CTabItem implementations
 * 
 * @author Vlado Pakan
 * 
 */
public class AbstractCTabItem implements CTabItem {

	protected final Logger logger = Logger.getLogger(this.getClass());

	protected org.eclipse.swt.custom.CTabItem swtCTabItem;
	protected org.eclipse.swt.custom.CTabFolder swtParent;
	/**
	 * See {@link CTabItem}
	 */
	@Override
	public void activate() {
		logger.info("Activating " + this.getText());
		Display.syncExec(new Runnable() {
			public void run() {
				swtCTabItem.getParent().setSelection(swtCTabItem);
			}
		});
		notifyCTabFolder(createEventForCTabItem(SWT.Selection));
	}

	protected AbstractCTabItem(final org.eclipse.swt.custom.CTabItem swtCTabItem) {
		if (swtCTabItem != null) {
			this.swtCTabItem = swtCTabItem;
			this.swtParent = Display
					.syncExec(new ResultRunnable<org.eclipse.swt.custom.CTabFolder>() {
						@Override
						public CTabFolder run() {
							return swtCTabItem.getParent();
						}
					});
		} else {
			throw new SWTLayerException(
					"SWT Tree passed to constructor is null");
		}
	}

	/**
	 * See {@link CTabItem}
	 */
	@Override
	public String getText() {
		return WidgetHandler.getInstance().getText(swtCTabItem);
	}

	/**
	 * See {@link CTabItem}
	 */
	@Override
	public String getToolTipText() {
		return WidgetHandler.getInstance().getToolTipText(swtCTabItem);
	}

	/**
	 * See {@link CTabItem}
	 */
	@Override
	public void close() {
		if (isShowClose()) {
			activate();
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
			clickCloseButton(rectangleCloseBox.x + (rectangleCloseBox.width / 2), 
				rectangleCloseBox.y	+ (rectangleCloseBox.height / 2));
		} else {
			throw new SWTLayerException("CTabItem with label " + getText()
					+ " has no close button");
		}
	}

	/**
	 * See {@link CTabItem}
	 */
	@Override
	public boolean isShowClose() {
		return Display.syncExec(new ResultRunnable<Boolean>() {
			public Boolean run() {
				return swtCTabItem.getShowClose();
			}
		});
	}

	/**
	 * Creates event for CTabItem with specified type
	 * 
	 * @param type
	 * @return
	 */
	private Event createEventForCTabItem(int type) {
		Event event = new Event();
		event.type = type;
		event.display = Display.getDisplay();
		event.time = (int) System.currentTimeMillis();
		event.item = swtCTabItem;
		event.widget = swtParent;
		return event;
	}

	/**
	 * Notifies CTabFolder listeners about event event.type field has to be
	 * properly set
	 * 
	 * @param event
	 */
	private void notifyCTabFolder(final Event event) {
		Display.syncExec(new Runnable() {
			public void run() {
				swtParent.notifyListeners(event.type, event);
			}
		});
	}

	/**
	 * Clicks close button on position x, y
	 * 
	 * @param x
	 * @param y
	 */
	private void clickCloseButton(int x, int y) {
		logger.info("Clicking on close button");
		notifyCTabFolder(createMouseEvent(SWT.MouseDown,x, y, 1, SWT.NONE, 1));
		// this event being button 1 is what allows CTabItem to close
		notifyCTabFolder(createMouseEvent(SWT.MouseUp,x, y, 1, SWT.BUTTON1, 1));
	}
	/**
	 * Create a mouse event
	 *
	 * @param x
	 * @param y
	 * @param button
	 * @param stateMask
	 * @param count
	 * @return
	 */
	private Event createMouseEvent(int type , int x, int y, int button, int stateMask, int count) {
		Event event = createEventForCTabItem(type);
		event.time = (int) System.currentTimeMillis();
		event.x = x;
		event.y = y;
		event.button = button;
		event.stateMask = stateMask;
		event.count = count;
		return event;
	}
}
