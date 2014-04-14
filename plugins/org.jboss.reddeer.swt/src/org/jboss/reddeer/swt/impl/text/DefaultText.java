package org.jboss.reddeer.swt.impl.text;

import org.hamcrest.Matcher;
import org.jboss.reddeer.swt.api.Text;
import org.jboss.reddeer.swt.lookup.TextLookup;
import org.jboss.reddeer.swt.matcher.WithTextMatcher;
import org.jboss.reddeer.swt.reference.ReferencedComposite;

/**
 * Default Text implementation. Most standard Text implementation
 * @author Jiri Peterka
 *
 */
public class DefaultText extends AbstractText implements Text {
		
	/**
	 * Text with given index
	 * @param index
	 */
	public DefaultText(int index){
		w = TextLookup.getInstance().getText(null, index);
	}
	
	/**
	 * Text with given index inside given composite
	 * @param referencedComposite
	 * @param index
	 */
	public DefaultText(ReferencedComposite referencedComposite, int index){
		w = TextLookup.getInstance().getText(referencedComposite, index);
	}
	
	@SuppressWarnings("rawtypes")
	/**
	 * Text with given matchers
	 * @param matchers
	 */
	public DefaultText(Matcher... matchers){
		w = TextLookup.getInstance().getText(null, 0, matchers);
	}
	
	@SuppressWarnings("rawtypes")
	/**
	 * Text with given matchers inside given composite
	 * @param referencedComposite
	 * @param matchers
	 */
	public DefaultText(ReferencedComposite referencedComposite, Matcher... matchers){
		w = TextLookup.getInstance().getText(referencedComposite, 0, matchers);
	}
	
	/**
	 * Text with text value
	 * @param title
	 */
	public DefaultText(String title) {
		w = TextLookup.getInstance().getText(null, 0, new WithTextMatcher(title));
	}
	
	/**
	 * Text with text value inside given composite
	 * @param referencedComposite
	 * @param title
	 */
	public DefaultText(ReferencedComposite referencedComposite, String title) {
		w = TextLookup.getInstance().getText(referencedComposite, 0, new WithTextMatcher(title));
	}
}
