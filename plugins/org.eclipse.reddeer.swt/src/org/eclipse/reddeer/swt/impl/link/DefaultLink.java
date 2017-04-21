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
package org.eclipse.reddeer.swt.impl.link;

import org.hamcrest.Matcher;
import org.eclipse.reddeer.core.matcher.LinkTextMatcher;
import org.eclipse.reddeer.core.reference.ReferencedComposite;

public class DefaultLink extends AbstractLink{
	
	/**
	 * Link with index 0.
	 */
	public DefaultLink() {
		this((ReferencedComposite) null);
	}
	
	public DefaultLink(org.eclipse.swt.widgets.Link widget){
		super(widget);
	}
	
	/**
	 * Link with index 0 inside given composite.
	 *
	 * @param referencedComposite the referenced composite
	 */
	public DefaultLink(ReferencedComposite referencedComposite){
		this(referencedComposite, 0);
	}
	
	/**
	 * Link with given text.
	 *
	 * @param text the text
	 */
	public DefaultLink(String text) {
		this(null, text);
	}
	
	/**
	 * Link with given text inside given composite.
	 *
	 * @param referencedComposite the referenced composite
	 * @param text the text
	 */
	public DefaultLink(ReferencedComposite referencedComposite, String text) {
		this(referencedComposite, 0, new LinkTextMatcher(text));
	}
	
	/**
	 * Link that matches given matchers.
	 *
	 * @param matchers the matchers
	 */
	public DefaultLink(Matcher<?>... matchers) {
		this(null, matchers);
	}
	
	/**
	 * Link that matches given matchers.
	 *
	 * @param referencedComposite the referenced composite
	 * @param matchers the matchers
	 */
	public DefaultLink(ReferencedComposite referencedComposite, Matcher<?>... matchers) {
		this(referencedComposite, 0, matchers);
	}
	
	/**
	 * Link with given index that matches given matchers.
	 *
	 * @param index the index
	 * @param matchers the matchers
	 */
	public DefaultLink(int index, Matcher<?>... matchers) {
		this(null, index, matchers);
	}
	
	/**
	 * Link with given index inside given composite that matches given matchers.
	 *
	 * @param referencedComposite the referenced composite
	 * @param index the index
	 * @param matchers the matchers
	 */
	public DefaultLink(ReferencedComposite referencedComposite, int index, Matcher<?>... matchers) {
		super(referencedComposite, index, matchers);
	}
}
