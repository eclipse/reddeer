package org.jboss.reddeer.swt.impl.label;

import org.jboss.reddeer.swt.lookup.LabelLookup;
import org.jboss.reddeer.swt.matcher.TextMatcher;

/**
 * DefaultLabel implementation represents most common Label widget type
 * and provide API for basic operation needed in UI tests
 * @author Jiri Peterka
 *
 */
public class DefaultLabel extends AbstractLabel {

	/**
	 * Create DefautLabel instance based on first available Label found
	 */
	public DefaultLabel() {
		w = LabelLookup.getInstance().getLabel(0);
	}

	/**
	 * Create DefaultLabel instance matching given text
	 * @param text
	 */
	public DefaultLabel(String text) {
		w = LabelLookup.getInstance().getLabel(0, new TextMatcher(text));
	}
	
	/**
	 * Create DefaultLabel instance matching given index
	 * @param index
	 */
	public DefaultLabel(int index) {
		w = LabelLookup.getInstance().getLabel(index);
	}
	
	/**
	 * Create DefaultLabel instance matching given text and index
	 * @param text
	 * @param index
	 */
	public DefaultLabel(String text, int index) {
		w = LabelLookup.getInstance().getLabel(index,new TextMatcher(text));		
	}
}
