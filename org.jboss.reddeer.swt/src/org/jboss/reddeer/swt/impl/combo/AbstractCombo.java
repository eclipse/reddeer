package org.jboss.reddeer.swt.impl.combo;

import org.jboss.reddeer.junit.logging.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.jboss.reddeer.swt.api.Combo;
import org.jboss.reddeer.swt.handler.WidgetHandler;
import org.jboss.reddeer.swt.lookup.WidgetLookup;
import org.jboss.reddeer.swt.util.Display;

/**
 * Abstract class for all Combo implementations
 * 
 * @author Vlado Pakan
 * 
 */
public abstract class AbstractCombo implements Combo {
	protected final Logger log = Logger.getLogger(this.getClass());
	org.eclipse.swt.widgets.Combo swtCombo;

	/**
	 * See {@link Combo}
	 */
	@Override
	public void setText(String str) {
		log.info("Set text of Combo " + getText() + " to:" + str);
		WidgetHandler.getInstance().setText(swtCombo, str);
	}

	/**
	 * See {@link Combo}
	 */
	@Override
	public void setSelection(int index) {
		log.info("Set selection of Combo " + getText() + " to index: "
				+ index);
		WidgetHandler.getInstance().setSelection(swtCombo, index);
		notifyCombo(createEventForCombo(SWT.Selection));
	}

	/**
	 * See {@link Combo}
	 */
	@Override
	public void setSelection(String selection) {
		log.info("Set selection of Combo " + getText() + " to selection: "
				+ selection);
		WidgetHandler.getInstance().setSelection(swtCombo, selection);
		notifyCombo(createEventForCombo(SWT.Selection));
	}

	/**
	 * See {@link Combo}
	 */
	@Override
	public String getSelection() {
		return WidgetHandler.getInstance().getSelection(swtCombo);
	}

	/**
	 * See {@link Combo}
	 */
	@Override
	public int getSelectionIndex() {
		return WidgetHandler.getInstance().getSelectionIndex(swtCombo);
	}

	/**
	 * See {@link Combo}
	 */
	@Override
	public boolean isEnabled() {
		return WidgetLookup.getInstance().isEnabled(swtCombo);
	}
	/**
	 * See {@link Combo}
	 */
	@Override
	public String getText() {
		return WidgetHandler.getInstance().getText(swtCombo);
	}
	/**
	 * Creates event for CTabItem with specified type
	 * 
	 * @param type
	 * @return
	 */
	private Event createEventForCombo(int type) {
		Event event = new Event();
		event.type = type;
		event.display = Display.getDisplay();
		event.time = (int) System.currentTimeMillis();
		event.widget = swtCombo;
		return event;
	}
	/**
	 * Notifies Combo listeners about event event.type field has to be
	 * properly set
	 * 
	 * @param event
	 */
	private void notifyCombo(final Event event) {
		Display.syncExec(new Runnable() {
			public void run() {
				swtCombo.notifyListeners(event.type, event);
			}
		});
	}
}
