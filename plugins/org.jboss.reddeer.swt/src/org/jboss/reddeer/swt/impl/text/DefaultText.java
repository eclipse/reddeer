package org.jboss.reddeer.swt.impl.text;

import org.hamcrest.Matcher;
import org.jboss.reddeer.swt.api.Text;
import org.jboss.reddeer.swt.matcher.WithTextMatcher;
import org.jboss.reddeer.swt.reference.ReferencedComposite;

/**
 * Default Text implementation. Most standard Text implementation
 * @author Jiri Peterka
 *
 */
public class DefaultText extends AbstractText implements Text {
	
	/**
	 * First text
	 * @param index
	 */
	public DefaultText(){
		this((ReferencedComposite) null);
	}
	
	/**
	 * Text with text value
	 * @param title
	 */
	public DefaultText(String title) {
		this(null, title);
	}
	
	/**
	 * Text with given matchers
	 * @param matchers
	 */
	public DefaultText(Matcher<?>... matchers){
		this(null, matchers);
	}

	/**
	 * Text with given index
	 * @param index
	 */
	public DefaultText(int index, Matcher<?>... matchers){
		this(null, index, matchers);
	}
	
	/**
	 * First text inside given composite
	 * @param referencedComposite
	 * @param index
	 */
	public DefaultText(ReferencedComposite referencedComposite){
		this(referencedComposite, 0);
	}

	/**
	 * Text with text value inside given composite
	 * @param referencedComposite
	 * @param title
	 */
	public DefaultText(ReferencedComposite referencedComposite, String title) {
		this(referencedComposite, 0, new WithTextMatcher(title));
	}
	
	/**
	 * Text with given matchers inside given composite
	 * @param referencedComposite
	 * @param matchers
	 */
	@SuppressWarnings("rawtypes")
	public DefaultText(ReferencedComposite referencedComposite, Matcher... matchers){
		this(referencedComposite, 0, matchers);
	}
	
	/**
	 * Text with given index inside given composite
	 * @param referencedComposite
	 * @param index
	 */
	public DefaultText(ReferencedComposite referencedComposite, int index, Matcher<?>... matchers){
		super(referencedComposite, index, matchers);
	}
}
