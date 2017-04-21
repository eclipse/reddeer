/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Red Hat Inc. - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.swt.impl.table;

import org.hamcrest.Matcher;
import org.eclipse.reddeer.swt.api.Table;
import org.eclipse.reddeer.core.reference.ReferencedComposite;

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
	
	public DefaultTable(org.eclipse.swt.widgets.Table widget){
		super(widget);
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
