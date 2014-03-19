package org.jboss.reddeer.swt.impl.combo;

import java.util.Arrays;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.jboss.reddeer.junit.logging.Logger;
import org.jboss.reddeer.swt.api.Combo;
import org.jboss.reddeer.swt.handler.ComboHandler;
import org.jboss.reddeer.swt.handler.WidgetHandler;
import org.jboss.reddeer.swt.util.Display;

/**
 * Abstract class for all Combo implementations
 * 
 * @author Vlado Pakan
 * 
 */
public abstract class AbstractCombo implements Combo {
	protected final Logger log = Logger.getLogger(this.getClass());
	protected org.eclipse.swt.widgets.Combo swtCombo;

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
		ComboHandler.getInstance().setSelection(swtCombo, index);
		notifyCombo(createEventForCombo(SWT.Selection));
	}

	/**
	 * See {@link Combo}
	 */
	@Override
	public void setSelection(String selection) {
		log.info("Set selection of Combo " + getText() + " to selection: "
				+ selection);
		ComboHandler.getInstance().setSelection(swtCombo, selection);
		notifyCombo(createEventForCombo(SWT.Selection));
	}

	/**
	 * See {@link Combo}
	 */
	@Override
	public String getSelection() {
		return ComboHandler.getInstance().getSelection(swtCombo);
	}

	/**
	 * See {@link Combo}
	 */
	@Override
	public int getSelectionIndex() {
		return ComboHandler.getInstance().getSelectionIndex(swtCombo);
	}

	/**
	 * See {@link Combo}
	 */
	@Override
	public boolean isEnabled() {
		return WidgetHandler.getInstance().isEnabled(swtCombo);
	}
	/**
	 * See {@link Combo}
	 */
	@Override
	public String getText() {
		return WidgetHandler.getInstance().getText(swtCombo);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getItems() {
		return Arrays.asList(ComboHandler.getInstance().getItems(swtCombo));
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
	
	public org.eclipse.swt.widgets.Combo getSWTWidget(){
		return swtCombo;
	}
}
