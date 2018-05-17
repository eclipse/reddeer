/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Red Hat, Inc - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.swt.impl.table;

import org.hamcrest.Matcher;
import org.eclipse.reddeer.core.matcher.WithTextMatcher;
import org.eclipse.reddeer.core.reference.ReferencedComposite;

public class DefaultTableItem extends AbstractTableItem{
	
	/**
	 * Default constructor.
	 */
	public DefaultTableItem() {
		this((ReferencedComposite) null);
	}
	
	public DefaultTableItem(org.eclipse.swt.widgets.TableItem widget){
		super(widget);
	}
	
	/**
	 * TableItem inside given composite.
	 *
	 * @param referencedComposite the referenced composite
	 */
	public DefaultTableItem(ReferencedComposite referencedComposite) {
		this(referencedComposite, 0);
	}
	
	/**
	 * Table item with specified path will be constructed that matches given matchers.
	 *
	 * @param itemIndex the item index
	 * @param matchers the matchers
	 */
	public DefaultTableItem(int itemIndex, Matcher<?>... matchers) {
		this(null, itemIndex);
	}
	
	/**
	 * Table item with specified path inside given composite will be constructed that matches given matchers.
	 *
	 * @param referencedComposite the referenced composite
	 * @param itemIndex the item index
	 * @param matchers the matchers
	 */
	public DefaultTableItem(ReferencedComposite referencedComposite, int itemIndex, Matcher<?>... matchers) {
		super(referencedComposite, itemIndex, matchers);
	}
	
	/**
	 * Table item with specified path will be constructed .
	 *
	 * @param tableItem the table item
	 */
	public DefaultTableItem(String tableItem) {
		this(null, tableItem);
	}
	
	/**
	 * Table item with specified path inside given compoiste will be constructed .
	 *
	 * @param referencedComposite the referenced composite
	 * @param tableItem the table item
	 */
	public DefaultTableItem(ReferencedComposite referencedComposite, String tableItem) {
		this(referencedComposite, 0, new WithTextMatcher(tableItem));
	}
	
	/**
	 * TableItem that matches given matchers.
	 *
	 * @param matchers the matchers
	 */
	public DefaultTableItem(Matcher<?>... matchers) {
		this(null, matchers);
	}
	
	/**
	 * TableItem that matches given matchers.
	 *
	 * @param referencedComposite the referenced composite
	 * @param matchers the matchers
	 */
	public DefaultTableItem(ReferencedComposite referencedComposite, Matcher<?>... matchers) {
		this(referencedComposite, 0, matchers);
	}
}
