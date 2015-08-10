package org.jboss.reddeer.swt.impl.table;

import org.hamcrest.Matcher;
import org.jboss.reddeer.core.matcher.WithTextMatcher;
import org.jboss.reddeer.core.reference.ReferencedComposite;

public class DefaultTableItem extends AbstractTableItem{
	
	/**
	 * Default constructor
	 */
	public DefaultTableItem() {
		this((ReferencedComposite) null);
	}
	
	/**
	 * TableItem inside given composite
	 * @param referencedComposite
	 */
	public DefaultTableItem(ReferencedComposite referencedComposite) {
		this(referencedComposite, 0);
	}
	
	/**
	 * Table item with specified path will be constructed that matches given matchers
	 * 
	 * @param tableItem
	 * @param matchers
	 */
	public DefaultTableItem(int itemIndex, Matcher<?>... matchers) {
		this(null, itemIndex);
	}
	
	/**
	 * Table item with specified path inside given composite will be constructed that matches given matchers
	 * @param referencedComposite
	 * @param tableItem
	 * @param matchers
	 */
	public DefaultTableItem(ReferencedComposite referencedComposite, int itemIndex, Matcher<?>... matchers) {
		super(referencedComposite, itemIndex, matchers);
	}
	
	/**
	 * Table item with specified path will be constructed 
	 * 
	 * @param tableItem
	 */
	public DefaultTableItem(String tableItem) {
		this(null, tableItem);
	}
	
	/**
	 * Table item with specified path inside given compoiste will be constructed 
	 * @param referencedComposite
	 * @param tableItem
	 */
	public DefaultTableItem(ReferencedComposite referencedComposite, String tableItem) {
		this(referencedComposite, 0, new WithTextMatcher(tableItem));
	}
	
	/**
	 * TableItem that matches given matchers
	 * @param matchers
	 */
	public DefaultTableItem(Matcher<?>... matchers) {
		this(null, matchers);
	}
	
	/**
	 * TableItem that matches given matchers
	 * @param referencedComposite
	 * @param matchers
	 */
	public DefaultTableItem(ReferencedComposite referencedComposite, Matcher<?>... matchers) {
		this(referencedComposite, 0, matchers);
	}
}
