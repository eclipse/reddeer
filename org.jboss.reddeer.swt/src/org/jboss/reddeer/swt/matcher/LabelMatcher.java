package org.jboss.reddeer.swt.matcher;

import org.eclipse.swt.widgets.List;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.jboss.reddeer.swt.handler.WidgetHandler;

/**
 * Label matcher
 * @author Rastislav Wagner
 * 
 */
public class LabelMatcher extends BaseMatcher<String> {

	private String label;
	
	/**
	 * Creates label matcher
	 * @param label given label for matcher usage
	 */
	public LabelMatcher(String label) {
		this.label = label;
	}
	
	@Override
	public void describeTo(Description description) {
		// TODO Auto-generated method stub

	}

	/**
	 * Matches given object
	 * @returns true if object is matching given label
	 */
	@Override
	public boolean matches(Object item) {
		
		if (item instanceof List) {
			String widgetLabel = WidgetHandler.getInstance().getLabel(item).replaceAll("&", "").split("\t")[0];;
			if (widgetLabel.equals(label)){
				return true;
			}
		}
		return false;
	}
	
	@Override
	public String toString() {
		return "Matcher matching label \"" + label +"\"";
	}
	
}
