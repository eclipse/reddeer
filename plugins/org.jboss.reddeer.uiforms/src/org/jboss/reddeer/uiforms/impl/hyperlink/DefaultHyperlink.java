package org.jboss.reddeer.uiforms.impl.hyperlink;

import org.hamcrest.Matcher;
import org.jboss.reddeer.swt.matcher.WithTextMatcher;
import org.jboss.reddeer.swt.reference.ReferencedComposite;
import org.jboss.reddeer.uiforms.lookup.HyperlinkLookup;


/**
 * Default implementation of {@link org.jboss.reddeer.uiforms.api.Hyperlink}
 * 
 * @author Lucia Jelinkova
 *
 */
public class DefaultHyperlink extends AbstractHyperlink {

	/**
	 * Default constructor, represents the first hyperlink. 
	 */
	public DefaultHyperlink() {
		this(0);
	}
	
	/**
	 * Represents the hyperlink with the specified order. 
	 * @param index
	 */
	public DefaultHyperlink(int index) {
		this(null, index);
	}
	
	/**
	 * Represents the hyperlink with the specified title. 
	 * @param text
	 */
	public DefaultHyperlink(String title) {
		this(null, title);
	}
	
	/**
	 * Represents the hyperlink that fulfills specified matchers
	 * @param matchers
	 */
	public DefaultHyperlink(Matcher<?>... matchers) {
		this(null, matchers);
	}
	
	/**
	 * Represents the first hyperlink inside specified composite
	 * @param referencedComposite
	 */
	public DefaultHyperlink(ReferencedComposite referencedComposite) {
		this(referencedComposite, 0);
	}
	
	/**
	 * Represents the hyperlink with the specified order inside specified composite
	 * @param referencedComposite
	 * @param index
	 */
	public DefaultHyperlink(ReferencedComposite referencedComposite, int index) {
		hyperLink = HyperlinkLookup.getInstance().getHyperlink(referencedComposite, index);
		setFocus();
	}
	
	/**
	 * Represents the hyperlink with the specified title inside specified composite
	 * @param referencedComposite
	 * @param text
	 */
	public DefaultHyperlink(ReferencedComposite referencedComposite, String title) {
		this(referencedComposite, new WithTextMatcher(title));
	}
	
	/**
	 * Represents the hyperlink that fulfills specified matchers inside specified composite
	 * @param referencedComposite
	 * @param matchers
	 */
	public DefaultHyperlink(ReferencedComposite referencedComposite, Matcher<?>... matchers) {
		hyperLink = HyperlinkLookup.getInstance().getHyperlink(null, 0, matchers);
		setFocus();
	}
}
