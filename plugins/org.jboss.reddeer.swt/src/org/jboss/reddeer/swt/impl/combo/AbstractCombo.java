package org.jboss.reddeer.swt.impl.combo;

import java.util.Arrays;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.hamcrest.Matcher;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.swt.api.Combo;
import org.jboss.reddeer.core.handler.ComboHandler;
import org.jboss.reddeer.core.handler.WidgetHandler;
import org.jboss.reddeer.core.reference.ReferencedComposite;
import org.jboss.reddeer.core.util.Display;
import org.jboss.reddeer.swt.widgets.AbstractWidget;

/**
 * Abstract class for all Combo implementations
 * 
 * @author Vlado Pakan
 * 
 */
public abstract class AbstractCombo extends AbstractWidget<org.eclipse.swt.widgets.Combo> implements Combo {
	
	private static final Logger log = Logger.getLogger(AbstractCombo.class);

	protected AbstractCombo(ReferencedComposite refComposite, int index, Matcher<?>... matchers) {
		super(org.eclipse.swt.widgets.Combo.class, refComposite, index, matchers);
	}
	
	/**
	 * See {@link Combo}.
	 *
	 * @param str the new text
	 */
	@Override
	public void setText(String str) {
		log.info("Set text of Combo " + getText() + " to:" + str);
		WidgetHandler.getInstance().setText(swtWidget, str);
	}

	/**
	 * See {@link Combo}.
	 *
	 * @param index the new selection
	 */
	@Override
	public void setSelection(int index) {
		log.info("Set selection of Combo " + getText() + " to index: "
				+ index);
		ComboHandler.getInstance().setSelection(swtWidget, index);
		notifyCombo(createEventForCombo(SWT.Selection));
	}

	/**
	 * See {@link Combo}.
	 *
	 * @param selection the new selection
	 */
	@Override
	public void setSelection(String selection) {
		log.info("Set selection of Combo " + getText() + " to selection: "
				+ selection);
		ComboHandler.getInstance().setSelection(swtWidget, selection);
		notifyCombo(createEventForCombo(SWT.Selection));
	}

	/**
	 * See {@link Combo}.
	 *
	 * @return the selection
	 */
	@Override
	public String getSelection() {
		return ComboHandler.getInstance().getSelection(swtWidget);
	}

	/**
	 * See {@link Combo}.
	 *
	 * @return the selection index
	 */
	@Override
	public int getSelectionIndex() {
		return ComboHandler.getInstance().getSelectionIndex(swtWidget);
	}

	/**
	 * See {@link Combo}.
	 *
	 * @return the text
	 */
	@Override
	public String getText() {
		return WidgetHandler.getInstance().getText(swtWidget);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getItems() {
		return Arrays.asList(ComboHandler.getInstance().getItems(swtWidget));
	}
	
	/**
	 * Creates event for CTabItem with specified type
	 * 
	 * @param type the type
	 * @return event
	 */
	private Event createEventForCombo(int type) {
		Event event = new Event();
		event.type = type;
		event.display = Display.getDisplay();
		event.time = (int) System.currentTimeMillis();
		event.widget = swtWidget;
		return event;
	}

	/**
	 * Notifies Combo listeners about event event.type field has to be
	 * properly set
	 * 
	 * @param event the event
	 */
	private void notifyCombo(final Event event) {
		Display.syncExec(new Runnable() {
			public void run() {
				swtWidget.notifyListeners(event.type, event);
			}
		});
	}
}
