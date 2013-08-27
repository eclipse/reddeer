package org.jboss.reddeer.swt.lookup;

import org.eclipse.swt.widgets.Label;
import org.hamcrest.Matcher;
import org.jboss.reddeer.swt.lookup.impl.WidgetLookup;

/**
 * Label lookup containing lookup routines for Label widget type
 * @author Jiri Peterka
 *
 */
public class LabelLookup {

	private static LabelLookup instance = null;
	
	private LabelLookup() {
	}
	
	/**
	 * Creates and returns instance of Label Lookup
	 * @return LabelLookup instance
	 */
	public static LabelLookup getInstance() {
		if (instance == null) instance = new LabelLookup();
		return instance;
	}
	
	/**
	 * Return Label instance
	 * @param matcher
	 * @return Label Widget matching criteria
	 */
	@SuppressWarnings({ "rawtypes" })
	public Label getLabel(int index, Matcher... matchers) {
		return (Label)WidgetLookup.getInstance().activeWidget(Label.class, index, matchers);
	}
}
