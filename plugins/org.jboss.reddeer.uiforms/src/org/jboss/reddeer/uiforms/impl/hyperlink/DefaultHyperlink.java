/******************************************************************************* 
 * Copyright (c) 2016 Red Hat, Inc. 
 * Distributed under license by Red Hat, Inc. All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 ******************************************************************************/ 
package org.jboss.reddeer.uiforms.impl.hyperlink;

import org.hamcrest.Matcher;
import org.jboss.reddeer.core.matcher.WithTextMatcher;
import org.jboss.reddeer.core.reference.ReferencedComposite;


/**
 * Default implementation of {@link org.jboss.reddeer.uiforms.api.Hyperlink}
 * 
 * @author Lucia Jelinkova
 *
 */
public class DefaultHyperlink extends AbstractHyperlink {

	/**
	 * Default constructor, represents the first hyperlink. 
	 */
	public DefaultHyperlink() {
		this(0);
	}
	
	/**
	 * Represents the hyperlink with the specified order that matches given matchers.
	 *
	 * @param index the index
	 * @param matchers the matchers
	 */
	public DefaultHyperlink(int index, Matcher<?>... matchers) {
		this(null, index);
	}
	
	/**
	 * Represents the hyperlink with the specified title. 
	 *
	 * @param title the title
	 */
	public DefaultHyperlink(String title) {
		this(null, title);
	}
	
	/**
	 * Represents the hyperlink that fulfills specified matchers.
	 *
	 * @param matchers the matchers
	 */
	public DefaultHyperlink(Matcher<?>... matchers) {
		this(null, matchers);
	}
	
	/**
	 * Represents the first hyperlink inside specified composite.
	 *
	 * @param referencedComposite the referenced composite
	 */
	public DefaultHyperlink(ReferencedComposite referencedComposite) {
		this(referencedComposite, 0);
	}
	
	/**
	 * Represents the hyperlink with the specified order inside specified composite.
	 *
	 * @param referencedComposite the referenced composite
	 * @param index the index
	 * @param matchers the matchers
	 */
	public DefaultHyperlink(ReferencedComposite referencedComposite, int index, Matcher<?>... matchers) {
		super(referencedComposite, index, matchers);
	}
	
	/**
	 * Represents the hyperlink with the specified title inside specified composite.
	 *
	 * @param referencedComposite the referenced composite
	 * @param title the title
	 */
	public DefaultHyperlink(ReferencedComposite referencedComposite, String title) {
		this(referencedComposite, new WithTextMatcher(title));
	}
	
	/**
	 * Represents the hyperlink that fulfills specified matchers inside specified composite.
	 *
	 * @param referencedComposite the referenced composite
	 * @param matchers the matchers
	 */
	public DefaultHyperlink(ReferencedComposite referencedComposite, Matcher<?>... matchers) {
		this(referencedComposite, 0, matchers);
	}
}
