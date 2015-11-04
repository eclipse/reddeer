package org.jboss.reddeer.swt.impl.ccombo;

import java.util.Arrays;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.hamcrest.Matcher;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.core.handler.CComboHandler;
import org.jboss.reddeer.core.handler.ComboHandler;
import org.jboss.reddeer.core.handler.WidgetHandler;
import org.jboss.reddeer.core.reference.ReferencedComposite;
import org.jboss.reddeer.core.util.Display;
import org.jboss.reddeer.swt.api.CCombo;
import org.jboss.reddeer.swt.widgets.AbstractWidget;

/**
 * Abstract class for all CCombo implementations
 * 
 * @author Andrej Podhradsky
 * 
 */
public abstract class AbstractCCombo extends AbstractWidget<org.eclipse.swt.custom.CCombo> implements CCombo {

	private static final Logger log = Logger.getLogger(AbstractCCombo.class);

	protected AbstractCCombo(ReferencedComposite refComposite, int index, Matcher<?>... matchers) {
		super(org.eclipse.swt.custom.CCombo.class, refComposite, index, matchers);
	}

	@Override
	public void setText(String str) {
		log.info("Set text of CCombo " + getText() + " to:" + str);
		WidgetHandler.getInstance().setText(swtWidget, str);
	}

	@Override
	public void setSelection(int index) {
		log.info("Set selection of CCombo " + getText() + " to index: " + index);
		CComboHandler.getInstance().setSelection(swtWidget, index);
		notifyCCombo(createEventForCCombo(SWT.Selection));
	}

	@Override
	public void setSelection(String selection) {
		log.info("Set selection of CCombo " + getText() + " to selection: " + selection);
		CComboHandler.getInstance().setSelection(swtWidget, selection);
		notifyCCombo(createEventForCCombo(SWT.Selection));
	}

	@Override
	public String getSelection() {
		return CComboHandler.getInstance().getSelection(swtWidget);
	}

	@Override
	public int getSelectionIndex() {
		return CComboHandler.getInstance().getSelectionIndex(swtWidget);
	}

	@Override
	public String getText() {
		return WidgetHandler.getInstance().getText(swtWidget);
	}

	@Override
	public List<String> getItems() {
		return Arrays.asList(CComboHandler.getInstance().getItems(swtWidget));
	}

	/**
	 * Creates event for CCombo with specified type
	 * 
	 * @param type
	 * @return
	 */
	private Event createEventForCCombo(int type) {
		Event event = new Event();
		event.type = type;
		event.display = Display.getDisplay();
		event.time = (int) System.currentTimeMillis();
		event.widget = swtWidget;
		return event;
	}

	/**
	 * Notifies CCombo listeners about event event.type field has to be properly
	 * set
	 * 
	 * @param event
	 */
	private void notifyCCombo(final Event event) {
		Display.syncExec(new Runnable() {
			public void run() {
				swtWidget.notifyListeners(event.type, event);
			}
		});
	}
}
