package org.jboss.reddeer.uiforms.impl.form;

import org.hamcrest.Matcher;
import org.jboss.reddeer.core.matcher.WithTextMatcher;
import org.jboss.reddeer.core.reference.ReferencedComposite;

/**
 * Represents Eclipse Form. This class will be used mainly for its children discovering
 * 
 * @author jjankovi
 *
 */
public class DefaultForm extends AbstractForm {

	/**
	 * Default constructor, represents the first form. 
	 */
	public DefaultForm() {
		this(0);
	}
	
	/**
	 * Represents the form with the specified order that matches given matchers
	 * @param index
	 */
	public DefaultForm(int index, Matcher<?>... matchers) {
		this(null, index);
	}
	
	/**
	 * Represents the form with the specified title. 
	 * @param text
	 */
	public DefaultForm(String title) {
		this(null, title);
	}
	
	/**
	 * Represents the form that fulfills specified matchers
	 * @param matchers
	 */
	public DefaultForm(Matcher<?>... matchers) {
		this(null, matchers);
	}
	
	/**
	 * Represents the first form inside specified composite
	 * @param referencedComposite
	 */
	public DefaultForm(ReferencedComposite referencedComposite) {
		this(referencedComposite, 0);
	}
	
	/**
	 * Represents the form with the specified order inside specified composite that matches given matchers
	 * @param referencedComposite
	 * @param index
	 */
	public DefaultForm(ReferencedComposite referencedComposite, int index, Matcher<?>... matchers) {
		super(referencedComposite, index, matchers);
	}
	
	/**
	 * Represents the form with the specified title inside specified composite
	 * @param referencedComposite
	 * @param text
	 */
	public DefaultForm(ReferencedComposite referencedComposite, String title) {
		this(referencedComposite, new WithTextMatcher(title));
	}
	
	/**
	 * Represents the form that fulfills specified matchers inside specified composite
	 * @param referencedComposite
	 * @param matchers
	 */
	public DefaultForm(ReferencedComposite referencedComposite, Matcher<?>... matchers) {
		this(referencedComposite, 0, matchers);
	}
}
