package org.jboss.reddeer.swt.impl.combo;

import org.apache.log4j.Logger;
import org.jboss.reddeer.swt.api.Combo;
import org.jboss.reddeer.swt.handler.WidgetHandler;
import org.jboss.reddeer.swt.lookup.impl.WidgetLookup;

/**
 * Abstract class for all Combo implementations
 * 
 * @author Vlado Pakan
 * 
 */
public abstract class AbstractCombo implements Combo {
	protected final Logger log = Logger.getLogger(this.getClass());
	org.eclipse.swt.widgets.Combo w;

	/**
	 * See {@link Combo}
	 */
	@Override
	public void setText(String str) {
		log.info("Set text of Combo " + getText() + " to:" + str);
		WidgetHandler.getInstance().setText(w, str);
	}

	/**
	 * See {@link Combo}
	 */
	@Override
	public void setSelection(int index) {
		log.info("Set selection of Combo " + getSelection() + " to index: "
				+ index);
		WidgetHandler.getInstance().setSelection(w, index);
	}

	/**
	 * See {@link Combo}
	 */
	@Override
	public void setSelection(String selection) {
		log.info("Set selection of Combo " + getSelection() + " to selection: "
				+ selection);
		WidgetHandler.getInstance().setSelection(w, selection);
	}

	/**
	 * See {@link Combo}
	 */
	@Override
	public String getSelection() {
		return WidgetHandler.getInstance().getSelection(w);
	}

	/**
	 * See {@link Combo}
	 */
	@Override
	public int getSelectionIndex() {
		return WidgetHandler.getInstance().getSelectionIndex(w);
	}

	/**
	 * See {@link Combo}
	 */
	@Override
	public boolean isEnabled() {
		return WidgetLookup.getInstance().isEnabled(w);
	}
	/**
	 * See {@link Combo}
	 */
	@Override
	public String getText() {
		return WidgetHandler.getInstance().getText(w);
	}
}
