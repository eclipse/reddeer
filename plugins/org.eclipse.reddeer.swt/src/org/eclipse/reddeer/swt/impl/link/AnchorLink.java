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
package org.eclipse.reddeer.swt.impl.link;

import org.hamcrest.Matcher;
import org.eclipse.reddeer.swt.api.Link;
import org.eclipse.reddeer.core.matcher.AnchorLinkTextMatcher;
import org.eclipse.reddeer.core.reference.ReferencedComposite;

public class AnchorLink extends AbstractLink implements Link{
	
	/**
	 * Link with index 0.
	 */
	public AnchorLink() {
		this((ReferencedComposite) null);
	}
	
	public AnchorLink(org.eclipse.swt.widgets.Link widget){
		super(widget);
	}
	
	/**
	 * Link with index 0 inside given composite.
	 *
	 * @param referencedComposite the referenced composite
	 */
	public AnchorLink(ReferencedComposite referencedComposite){
		this(referencedComposite, 0);
	}
	
	/**
	 * Link with given text.
	 *
	 * @param text the text
	 */
	public AnchorLink(String text) {
		this(null, text);
	}
	
	/**
	 * Link with given text inside given composite.
	 *
	 * @param referencedComposite the referenced composite
	 * @param text the text
	 */
	public AnchorLink(ReferencedComposite referencedComposite, String text) {
		this(referencedComposite, 0, new AnchorLinkTextMatcher(text));
	}
	
	/**
	 * Link that matches given matchers.
	 *
	 * @param matchers the matchers
	 */
	public AnchorLink(Matcher<?>... matchers) {
		this(null, matchers);
	}
	
	/**
	 * Link that matches given matchers.
	 *
	 * @param referencedComposite the referenced composite
	 * @param matchers the matchers
	 */
	public AnchorLink(ReferencedComposite referencedComposite, Matcher<?>... matchers) {
		this(referencedComposite, 0, matchers);
	}
	
	/**
	 * Link with given index that matches given matchers.
	 *
	 * @param index the index
	 * @param matchers the matchers
	 */
	public AnchorLink(int index, Matcher<?>... matchers) {
		this(null, index, matchers);
	}
	
	/**
	 * Link with given index inside given composite that matches given matchers.
	 *
	 * @param referencedComposite the referenced composite
	 * @param index the index
	 * @param matchers the matchers
	 */
	public AnchorLink(ReferencedComposite referencedComposite, int index, Matcher<?>... matchers) {
		super(referencedComposite, index, matchers);
	}
}
