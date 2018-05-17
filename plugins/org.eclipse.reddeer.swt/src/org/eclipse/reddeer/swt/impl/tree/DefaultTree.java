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
package org.eclipse.reddeer.swt.impl.tree;

import org.hamcrest.Matcher;
import org.eclipse.reddeer.core.reference.ReferencedComposite;

/**
 * Default Tree implementation
 * 
 * @author jjankovi
 *
 */
public class DefaultTree extends AbstractTree {
	
	/**
	 * Tree with index 0.
	 */
	public DefaultTree() {
		this((ReferencedComposite) null);
	}
	
	public DefaultTree(org.eclipse.swt.widgets.Tree widget){
		super(widget);
	}
	/**
	 * Tree with index 0 inside given composite.
	 *
	 * @param referencedComposite the referenced composite
	 */
	public DefaultTree(ReferencedComposite referencedComposite){
		this(referencedComposite, 0);
	}
	
	/**
	 * Tree that matches given matchers.
	 *
	 * @param matchers the matchers
	 */
	public DefaultTree(Matcher<?>... matchers) {
		this(null, matchers);
	}
	
	/**
	 * Tree that matches given matchers.
	 *
	 * @param referencedComposite the referenced composite
	 * @param matchers the matchers
	 */
	public DefaultTree(ReferencedComposite referencedComposite, Matcher<?>... matchers) {
		this(referencedComposite, 0, matchers);
	}
	
	/**
	 * Tree with given index that matches given matchers.
	 *
	 * @param index the index
	 * @param matchers the matchers
	 */
	public DefaultTree(int index, Matcher<?>... matchers) {
		this(null, index, matchers);
	}
	
	/**
	 * Tree with given index inside given composite that matches given matchers.
	 *
	 * @param referencedComposite the referenced composite
	 * @param index the index
	 * @param matchers the matchers
	 */
	public DefaultTree(ReferencedComposite referencedComposite, int index, Matcher<?>... matchers) {
		super(referencedComposite, index, matchers);
	}
}
