package org.jboss.reddeer.swt.impl.label;

import org.jboss.reddeer.swt.lookup.LabelLookup;
import org.jboss.reddeer.swt.matcher.TextMatcher;
import org.jboss.reddeer.swt.reference.ReferencedComposite;

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
		w = LabelLookup.getInstance().getLabel(null, 0);
	}
	
	/**
	 * Create DefautLabel instance based on first available Label found inside given composite
	 * @param referencedComposite
	 */
	public DefaultLabel(ReferencedComposite referencedComposite) {
		w = LabelLookup.getInstance().getLabel(referencedComposite, 0);
	}

	/**
	 * Create DefaultLabel instance matching given text
	 * @param text
	 */
	public DefaultLabel(String text) {
		w = LabelLookup.getInstance().getLabel(null, 0, new TextMatcher(text));
	}
	
	/**
	 * Create DefaultLabel instance matching given text inside given composite
	 * @param referencedComposite
	 * @param text
	 */
	public DefaultLabel(ReferencedComposite referencedComposite, String text) {
		w = LabelLookup.getInstance().getLabel(referencedComposite, 0, new TextMatcher(text));
	}
	
	/**
	 * Create DefaultLabel instance matching given index
	 * @param index
	 */
	public DefaultLabel(int index) {
		w = LabelLookup.getInstance().getLabel(null, index);
	}
	
	/**
	 * Create DefaultLabel instance matching given index inside given composite
	 * @param referencedComposite
	 * @param index
	 */
	public DefaultLabel(ReferencedComposite referencedComposite, int index) {
		w = LabelLookup.getInstance().getLabel(referencedComposite, index);
	}
	
	/**
	 * Create DefaultLabel instance matching given text and index
	 * @param text
	 * @param index
	 */
	public DefaultLabel(String text, int index) {
		w = LabelLookup.getInstance().getLabel(null, index,new TextMatcher(text));		
	}
	
	/**
	 * Create DefaultLabel instance matching given text and index inside given composite
	 * @param referencedComposite
	 * @param text
	 * @param index
	 */
	public DefaultLabel(ReferencedComposite referencedComposite, String text, int index) {
		w = LabelLookup.getInstance().getLabel(referencedComposite, index,new TextMatcher(text));		
	}
}
