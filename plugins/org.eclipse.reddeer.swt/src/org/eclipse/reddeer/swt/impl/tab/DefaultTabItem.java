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
package org.eclipse.reddeer.swt.impl.tab;

import org.hamcrest.Matcher;
import org.eclipse.reddeer.core.matcher.WithMnemonicTextMatcher;
import org.eclipse.reddeer.core.reference.ReferencedComposite;

/**
 * Default TabItem implementation
 * 
 * @author Andrej Podhradsky
 * @author Vlado Pakan
 * @author Lucia Jelinkova
 * 
 */
public class DefaultTabItem extends AbstractTabItem {

	/**
	 * TabItem with index 0.
	 */
	public DefaultTabItem() {
		this((ReferencedComposite) null);
	}
	
	public DefaultTabItem(org.eclipse.swt.widgets.TabItem widget){
		super(widget);
	}
	
	/**
	 * TabItem with index 0 inside given composite.
	 *
	 * @param referencedComposite the referenced composite
	 */
	public DefaultTabItem(ReferencedComposite referencedComposite){
		this(referencedComposite, 0);
	}
	
	/**
	 * TabItem with given text.
	 *
	 * @param text the text
	 */
	public DefaultTabItem(String text) {
		this(null, text);
	}
	
	/**
	 * TabItem with given text inside given composite.
	 *
	 * @param referencedComposite the referenced composite
	 * @param text the text
	 */
	public DefaultTabItem(ReferencedComposite referencedComposite, String text) {
		this(referencedComposite, 0, new WithMnemonicTextMatcher(text));
	}
	
	/**
	 * TabItem that matches given matchers.
	 *
	 * @param matchers the matchers
	 */
	public DefaultTabItem(Matcher<?>... matchers) {
		this(null, matchers);
	}
	
	/**
	 * TabItem that matches given matchers.
	 *
	 * @param referencedComposite the referenced composite
	 * @param matchers the matchers
	 */
	public DefaultTabItem(ReferencedComposite referencedComposite, Matcher<?>... matchers) {
		this(referencedComposite, 0, matchers);
	}
	
	/**
	 * TabItem with given index that matches given matchers.
	 *
	 * @param index the index
	 * @param matchers the matchers
	 */
	public DefaultTabItem(int index, Matcher<?>... matchers) {
		this(null, index, matchers);
	}
	
	/**
	 * TabItem with given index inside given composite that matches given matchers.
	 *
	 * @param referencedComposite the referenced composite
	 * @param index the index
	 * @param matchers the matchers
	 */
	public DefaultTabItem(ReferencedComposite referencedComposite, int index, Matcher<?>... matchers) {
		super(referencedComposite, index, matchers);
	}
}
