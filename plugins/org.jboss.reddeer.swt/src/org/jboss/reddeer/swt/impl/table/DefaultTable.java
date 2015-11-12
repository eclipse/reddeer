package org.jboss.reddeer.swt.impl.table;

import org.hamcrest.Matcher;
import org.jboss.reddeer.swt.api.Table;
import org.jboss.reddeer.core.reference.ReferencedComposite;

/**
 * Default Table implementation
 * @author Jiri Peterka
 *
 */
public class DefaultTable extends AbstractTable implements Table {
	
	/**
	 * Table with index 0.
	 */
	public DefaultTable() {
		this((ReferencedComposite) null);
	}
	
	/**
	 * Table with index 0 inside given composite.
	 *
	 * @param referencedComposite the referenced composite
	 */
	public DefaultTable(ReferencedComposite referencedComposite){
		this(referencedComposite, 0);
	}
	
	/**
	 * Table that matches given matchers.
	 *
	 * @param matchers the matchers
	 */
	public DefaultTable(Matcher<?>... matchers) {
		this(null, matchers);
	}
	
	/**
	 * Table that matches given matchers.
	 *
	 * @param referencedComposite the referenced composite
	 * @param matchers the matchers
	 */
	public DefaultTable(ReferencedComposite referencedComposite, Matcher<?>... matchers) {
		this(referencedComposite, 0, matchers);
	}
	
	/**
	 * Table with given index that matches given matchers.
	 *
	 * @param index the index
	 * @param matchers the matchers
	 */
	public DefaultTable(int index, Matcher<?>... matchers) {
		this(null, index, matchers);
	}
	
	/**
	 * Table with given index inside given composite that matches given matchers.
	 *
	 * @param referencedComposite the referenced composite
	 * @param index the index
	 * @param matchers the matchers
	 */
	public DefaultTable(ReferencedComposite referencedComposite, int index, Matcher<?>... matchers) {
		super(referencedComposite, index, matchers);
	}
}
