package org.jboss.reddeer.swt.lookup;

import org.eclipse.swt.widgets.Spinner;
import org.hamcrest.Matcher;
import org.jboss.reddeer.swt.reference.ReferencedComposite;
import org.jboss.reddeer.swt.widgets.AbstractWidget;

/**
 * Spinner lookup containing lookup routines for Spinner widget type
 * 
 * @author Andrej Podhradsky
 * @deprecated Use {@link AbstractWidget}. Will be removed in 1.0.0
 */
public class SpinnerLookup {

	private static SpinnerLookup instance = null;

	private SpinnerLookup() {
	}

	/**
	 * Creates and returns instance of Spinner Lookup
	 * 
	 * @return SpinnerLookup instance
	 */
	public static SpinnerLookup getInstance() {
		if (instance == null)
			instance = new SpinnerLookup();
		return instance;
	}

	/**
	 * Return Spinner instance
	 * 
	 * @param matcher
	 * @return Spinner Widget matching criteria
	 */
	@SuppressWarnings({ "rawtypes" })
	public Spinner getSpinner(ReferencedComposite refComposite, int index, Matcher... matchers) {
		return (Spinner) WidgetLookup.getInstance().activeWidget(refComposite, Spinner.class,
				index, matchers);
	}
}
