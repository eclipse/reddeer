package org.jboss.reddeer.swt.impl.ctab;

import org.hamcrest.Matcher;
import org.jboss.reddeer.swt.matcher.WithMnemonicTextMatcher;
import org.jboss.reddeer.swt.reference.ReferencedComposite;

/**
 * Default CTabItem implementation
 * @author Vlado Pakan
 *
 */
public class DefaultCTabItem extends AbstractCTabItem {

	/**
	 * Default parameter-less constructor
	 */
	public DefaultCTabItem() {
		this(0);
	}
	
	/**
	 * CTabItem inside given composite
	 * @param referencedComposite
	 */
	public DefaultCTabItem(ReferencedComposite referencedComposite) {
		this(referencedComposite, 0);
	}
	
	/**
	 * CTabItem with specified text will be constructed 
	 * 
	 * @param text
	 */
	public DefaultCTabItem(String text) {
		this(null, text);
	}
	
	/**
	 * CTabItem with specified text inside given composite will be constructed 
	 * @param referencedComposite
	 * @param text
	 */
	public DefaultCTabItem(ReferencedComposite referencedComposite, String text) {
		this(referencedComposite, 0, new WithMnemonicTextMatcher(text));
	}
	
	/**
	 * CTabItem that matches given matchers
	 * @param matchers
	 */
	public DefaultCTabItem(Matcher<?>... matchers) {
		this(null, matchers);
	}
	
	/**
	 * CTabItem that matches given matchers
	 * @param referencedComposite
	 * @param matchers
	 */
	public DefaultCTabItem(ReferencedComposite referencedComposite, Matcher<?>... matchers) {
		this(referencedComposite, 0, matchers);
	}
	
	/**
	 * CTabItem with specified index will be constructed 
	 * 
	 * @param index
	 */
	public DefaultCTabItem(int index, Matcher<?>... matchers) {
		this(null, index, matchers);
	}
	
	/**
	 * CTabItem with specified index inside given composite will be constructed 
	 * @param referencedComposite
	 * @param index
	 */
	public DefaultCTabItem(ReferencedComposite referencedComposite, int index, Matcher<?>... matchers) {
		super(referencedComposite, index, matchers);
	}
	
	/**
	 * CTabItem with specified index and text will be constructed 
	 * 
	 * @param index
	 * @param text
	 * @deprecated Since 1.0.0 this is not default widget constructor
	 */
	public DefaultCTabItem(int index, String text) {
		this(null, index, text);
	}
	
	/**
	 * CTabItem with specified index and text inside given composite will be constructed 
	 * @param referencedComposite
	 * @param index
	 * @param text
	 * @deprecated Since 1.0.0 this is not default widget constructor
	 */
	public DefaultCTabItem(ReferencedComposite referencedComposite, int index, String text) {
		this(referencedComposite, index, new WithMnemonicTextMatcher(text));
	}
}
