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
package org.jboss.reddeer.swt.impl.label;

import org.hamcrest.Matcher;
import org.jboss.reddeer.core.matcher.WithTextMatcher;
import org.jboss.reddeer.core.reference.ReferencedComposite;

/**
 * DefaultLabel implementation represents most common Label widget type
 * and provide API for basic operation needed in UI tests
 * @author Jiri Peterka
 *
 */
public class DefaultLabel extends AbstractLabel {

	/**
	 * Label with index 0.
	 */
	public DefaultLabel() {
		this((ReferencedComposite) null);
	}
	
	public DefaultLabel(org.eclipse.swt.widgets.Label widget){
		super(widget);
	}
	
	/**
	 * Label with index 0 inside given composite.
	 *
	 * @param referencedComposite the referenced composite
	 */
	public DefaultLabel(ReferencedComposite referencedComposite){
		this(referencedComposite, 0);
	}
	
	/**
	 * Label with given text.
	 *
	 * @param text the text
	 */
	public DefaultLabel(String text) {
		this(null, text);
	}
	
	/**
	 * Label with given text inside given composite.
	 *
	 * @param referencedComposite the referenced composite
	 * @param text the text
	 */
	public DefaultLabel(ReferencedComposite referencedComposite, String text) {
		this(referencedComposite, 0, new WithTextMatcher(text));
	}
	
	/**
	 * Label that matches given matchers.
	 *
	 * @param matchers the matchers
	 */
	public DefaultLabel(Matcher<?>... matchers) {
		this(null, matchers);
	}
	
	/**
	 * Label that matches given matchers.
	 *
	 * @param referencedComposite the referenced composite
	 * @param matchers the matchers
	 */
	public DefaultLabel(ReferencedComposite referencedComposite, Matcher<?>... matchers) {
		this(referencedComposite, 0, matchers);
	}
	
	/**
	 * Label with given index that matches given matchers.
	 *
	 * @param index the index
	 * @param matchers the matchers
	 */
	public DefaultLabel(int index, Matcher<?>... matchers) {
		this(null, index, matchers);
	}
	
	/**
	 * Label with given index inside given composite that matches given matchers.
	 *
	 * @param referencedComposite the referenced composite
	 * @param index the index
	 * @param matchers the matchers
	 */
	public DefaultLabel(ReferencedComposite referencedComposite, int index, Matcher<?>... matchers) {
		super(referencedComposite, index, matchers);
	}
}
