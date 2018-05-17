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
package org.eclipse.reddeer.swt.impl.tab;

import org.hamcrest.Matcher;
import org.eclipse.reddeer.core.reference.ReferencedComposite;

/**
 * Default TabFolder implementation
 * 
 * @author Andrej Podhradsky
 * 
 */
public class DefaultTabFolder extends AbstractTabFolder {

	/**
	 * TabFolder with index 0.
	 */
	public DefaultTabFolder() {
		this((ReferencedComposite) null);
	}
	
	public DefaultTabFolder(org.eclipse.swt.widgets.TabFolder widget) {
		super(widget);
	}
	
	/**
	 * TabFolder with index 0 inside given composite.
	 *
	 * @param referencedComposite the referenced composite
	 */
	public DefaultTabFolder(ReferencedComposite referencedComposite){
		this(referencedComposite, 0);
	}
	
	/**
	 * TabFolder that matches given matchers.
	 *
	 * @param matchers the matchers
	 */
	public DefaultTabFolder(Matcher<?>... matchers) {
		this(null, matchers);
	}
	
	/**
	 * TabFolder that matches given matchers.
	 *
	 * @param referencedComposite the referenced composite
	 * @param matchers the matchers
	 */
	public DefaultTabFolder(ReferencedComposite referencedComposite, Matcher<?>... matchers) {
		this(referencedComposite, 0, matchers);
	}
	
	/**
	 * TabFolder with given index that matches given matchers.
	 *
	 * @param index the index
	 * @param matchers the matchers
	 */
	public DefaultTabFolder(int index, Matcher<?>... matchers) {
		this(null, index, matchers);
	}
	
	/**
	 * TabFolder with given index inside given composite that matches given matchers.
	 *
	 * @param referencedComposite the referenced composite
	 * @param index the index
	 * @param matchers the matchers
	 */
	public DefaultTabFolder(ReferencedComposite referencedComposite, int index, Matcher<?>... matchers) {
		super(referencedComposite, index, matchers);
	}
}
