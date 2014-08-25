package org.jboss.reddeer.uiforms.impl.section;


import org.hamcrest.Matcher;
import org.jboss.reddeer.swt.matcher.WithTextMatcher;
import org.jboss.reddeer.swt.reference.ReferencedComposite;

/**
 * Default implementation of {@link org.jboss.reddeer.uiforms.api.Section}
 * 
 * @author Lucia Jelinkova
 *
 */
public class DefaultSection extends AbstractSection {

	/**
	 * Default constructor, represents the first section. 
	 */
	public DefaultSection() {
		this(0);
	}
	
	/**
	 * Represents the section with the specified order that matches given matchers
	 * @param index
	 * @param matchers
	 */
	public DefaultSection(int index, Matcher<?>... matchers) {
		this(null, index);
	}
	
	/**
	 * Represents the section with the specified title. 
	 * @param text
	 */
	public DefaultSection(String text) {
		this(null, text);
	}
	
	/**
	 * Represents the section that fulfills specified matchers
	 * @param matchers
	 */
	public DefaultSection(Matcher<?>... matchers) {
		this(null, matchers);
	}
	
	/**
	 * Represents the first section inside specified composite
	 * @param referencedComposite
	 */
	public DefaultSection(ReferencedComposite referencedComposite) {
		this(referencedComposite, 0);
	}
	
	/**
	 * Represents the section with the specified order inside specified composite
	 * @param referencedComposite that matches given matchers
	 * @param index
	 * @param matchers
	 */
	public DefaultSection(ReferencedComposite referencedComposite, int index, Matcher<?>... matchers) {
		super(referencedComposite, index, matchers);
	}
	
	/**
	 * Represents the section with the specified title inside specified composite
	 * @param referencedComposite
	 * @param text
	 */
	public DefaultSection(ReferencedComposite referencedComposite, String text) {
		this(referencedComposite, new WithTextMatcher(text));
	}
	
	/**
	 * Represents the section that fulfills specified matchers inside specified composite
	 * @param referencedComposite
	 * @param matchers
	 */
	public DefaultSection(ReferencedComposite referencedComposite, Matcher<?>... matchers) {
		this(referencedComposite, 0, matchers);
	}
}
