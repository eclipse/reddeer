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
package org.jboss.reddeer.uiforms.impl.section;

import org.hamcrest.Matcher;
import org.jboss.reddeer.core.matcher.WithTextMatcher;
import org.jboss.reddeer.core.reference.ReferencedComposite;

/**
 * Default implementation of {@link org.jboss.reddeer.uiforms.api.Section}
 * 
 * @author Lucia Jelinkova
 *
 */
public class DefaultSection extends AbstractSection {

	/**
	 * Default constructor, represents the first section. 
	 */
	public DefaultSection() {
		this(0);
	}
	
	public DefaultSection(org.eclipse.ui.forms.widgets.Section widget){
		super(widget);
	}
	
	/**
	 * Represents the section with the specified order that matches given matchers.
	 *
	 * @param index the index
	 * @param matchers the matchers
	 */
	public DefaultSection(int index, Matcher<?>... matchers) {
		this(null, index);
	}
	
	/**
	 * Represents the section with the specified title. 
	 *
	 * @param text the text
	 */
	public DefaultSection(String text) {
		this(null, text);
	}
	
	/**
	 * Represents the section that fulfills specified matchers.
	 *
	 * @param matchers the matchers
	 */
	public DefaultSection(Matcher<?>... matchers) {
		this(null, matchers);
	}
	
	/**
	 * Represents the first section inside specified composite.
	 *
	 * @param referencedComposite the referenced composite
	 */
	public DefaultSection(ReferencedComposite referencedComposite) {
		this(referencedComposite, 0);
	}
	
	/**
	 * Represents the section with the specified order inside specified composite.
	 *
	 * @param referencedComposite that matches given matchers
	 * @param index the index
	 * @param matchers the matchers
	 */
	public DefaultSection(ReferencedComposite referencedComposite, int index, Matcher<?>... matchers) {
		super(referencedComposite, index, matchers);
	}
	
	/**
	 * Represents the section with the specified title inside specified composite.
	 *
	 * @param referencedComposite the referenced composite
	 * @param text the text
	 */
	public DefaultSection(ReferencedComposite referencedComposite, String text) {
		this(referencedComposite, new WithTextMatcher(text));
	}
	
	/**
	 * Represents the section that fulfills specified matchers inside specified composite.
	 *
	 * @param referencedComposite the referenced composite
	 * @param matchers the matchers
	 */
	public DefaultSection(ReferencedComposite referencedComposite, Matcher<?>... matchers) {
		this(referencedComposite, 0, matchers);
	}
}
