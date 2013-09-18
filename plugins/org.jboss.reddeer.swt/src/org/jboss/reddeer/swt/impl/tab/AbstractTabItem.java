package org.jboss.reddeer.swt.impl.tab;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.jboss.reddeer.junit.logging.Logger;
import org.jboss.reddeer.swt.api.TabItem;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.swt.handler.WidgetHandler;
import org.jboss.reddeer.swt.util.Display;
import org.jboss.reddeer.swt.util.ResultRunnable;

/**
 * Abstract class for all TabItem implementations
 * 
 * @author Andrej Podhradsky
 * @author Vlado Pakan
 * 
 */
public class AbstractTabItem implements TabItem {

	protected final Logger logger = Logger.getLogger(this.getClass());

	protected org.eclipse.swt.widgets.TabItem swtTabItem;
	protected org.eclipse.swt.widgets.TabFolder swtParent;

	protected AbstractTabItem(final org.eclipse.swt.widgets.TabItem swtTabItem) {
		if (swtTabItem != null) {
			this.swtTabItem = swtTabItem;
			this.swtParent = Display
					.syncExec(new ResultRunnable<org.eclipse.swt.widgets.TabFolder>() {
						@Override
						public org.eclipse.swt.widgets.TabFolder run() {
							return swtTabItem.getParent();
						}
					});
		} else {
			throw new SWTLayerException("SWT TabItem passed to constructor is null");
		}
	}

	/**
	 * See {@link TabItem}
	 */
	@Override
	public void activate() {
		logger.info("Activating " + this.getText());
		Display.syncExec(new Runnable() {
			public void run() {
				swtTabItem.getParent().setSelection(swtTabItem);
			}
		});
		notifyTabFolder(createEventForTabItem(SWT.Selection));
	}

	/**
	 * Creates event for TabItem with specified type
	 * 
	 * @param type
	 * @return
	 */
	private Event createEventForTabItem(int type) {
		Event event = new Event();
		event.type = type;
		event.display = Display.getDisplay();
		event.time = (int) System.currentTimeMillis();
		event.item = swtTabItem;
		event.widget = swtParent;
		return event;
	}

	/**
	 * Notifies TabFolder listeners about event event.type field has to be
	 * properly set
	 * 
	 * @param event
	 */
	private void notifyTabFolder(final Event event) {
		Display.syncExec(new Runnable() {
			public void run() {
				swtParent.notifyListeners(event.type, event);
			}
		});
	}

	/**
	 * See {@link TabItem}
	 */
	@Override
	public String getText() {
		return WidgetHandler.getInstance().getText(swtTabItem);
	}
}
