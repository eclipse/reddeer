package org.jboss.reddeer.swt.impl.browser;

import org.hamcrest.Matcher;
import org.jboss.reddeer.swt.matcher.WithLabelMatcher;
import org.jboss.reddeer.swt.reference.ReferencedComposite;
/**
 * Implements SWT Browser manipulations
 * @author Jiri Peterka, Vlado Pakan
 *
 */
public class InternalBrowser extends AbstractBrowser{
	
	/**
	 * Finds first Browser
	 */
	public InternalBrowser() {
		this((ReferencedComposite) null);
	}
	
	/**
	 * Finds first Browser inside given composite
	 * @param referencedComposite
	 */
	public InternalBrowser(ReferencedComposite referencedComposite) {
		this(referencedComposite, 0);
	}
	
	/**
	 * Finds Browser specified by label
	 * @param label
	 */
	public InternalBrowser(String label) {
		this(null, label);
	}
	
	/**
	 * Browser that matches given matchers
	 * @param matchers
	 */
	public InternalBrowser(Matcher<?>... matchers) {
		this(null, matchers);
	}
	
	/**
	 * Finds Browser specified by label inside given composite
	 * @param referencedComposite
	 * @param label
	 */
	public InternalBrowser(ReferencedComposite referencedComposite, String label) {
		this(referencedComposite, 0, new WithLabelMatcher(label));
	}
	
	/**
	 * Browser that matches given matchers
	 * @param referencedComposite
	 * @param matchers
	 */
	public InternalBrowser(ReferencedComposite referencedComposite, Matcher<?>... matchers) {
		this(referencedComposite, 0, matchers);
	}
	
	/**
	 * Finds Browser specified by index that matches given matchers
	 * @param index
	 * @param matchers
	 */
	public InternalBrowser(int index, Matcher<?>... matchers) {
		this(null, index, matchers);
	}
	
	/**
	 * Finds Browser specified by index inside given composite that matches given matchers
	 * @param referencedComposite
	 * @param index
	 * @param matchers
	 */
	public InternalBrowser(ReferencedComposite referencedComposite, int index, Matcher<?>... matchers) {
		super(referencedComposite, index, matchers);
	}
}
