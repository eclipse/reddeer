package org.jboss.reddeer.uiforms.lookup;

import org.eclipse.ui.forms.widgets.FormText;
import org.hamcrest.Matcher;
import org.jboss.reddeer.swt.lookup.WidgetLookup;
import org.jboss.reddeer.swt.reference.ReferencedComposite;
import org.jboss.reddeer.swt.widgets.AbstractWidget;

/**
 * This class contains lookup routines for FormText widgets
 * @deprecated Use {@link AbstractWidget} instead of lookups. Will be removed in 1.0.0
 * 
 * @author rhopp
 *
 */

public class FormTextLookup {

	private static FormTextLookup instance;

	/**
	 * Creates and returns instance of this class (Singleton design pattern)
	 * 
	 * @return
	 */

	public static FormTextLookup getInstance() {
		if (instance == null) {
			instance = new FormTextLookup();
		}
		return instance;
	}

	/**
	 * Looks up and returns widget of type FormText specified by given mathcers
	 * and index
	 * 
	 * @param referencedComposite
	 * @param index
	 * @param matchers
	 * @return
	 */

	@SuppressWarnings("rawtypes")
	public FormText getFormText(ReferencedComposite referencedComposite,
			int index, Matcher... matchers) {
		return (FormText) WidgetLookup.getInstance().activeWidget(
				referencedComposite, FormText.class, index, matchers);
	}

}
