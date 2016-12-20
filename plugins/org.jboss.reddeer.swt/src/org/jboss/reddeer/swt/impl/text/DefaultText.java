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
package org.jboss.reddeer.swt.impl.text;

import org.hamcrest.Matcher;
import org.jboss.reddeer.swt.api.Text;
import org.jboss.reddeer.core.matcher.WithTextMatcher;
import org.jboss.reddeer.core.reference.ReferencedComposite;

/**
 * Default Text implementation. Most standard Text implementation
 * @author Jiri Peterka
 *
 */
public class DefaultText extends AbstractText implements Text {
	
	/**
	 * First text.
	 */
	public DefaultText(){
		this((ReferencedComposite) null);
	}
	
	public DefaultText(org.eclipse.swt.widgets.Text widget){
		super(widget);
	}
	
	/**
	 * Text with text value.
	 *
	 * @param title the title
	 */
	public DefaultText(String title) {
		this(null, title);
	}
	
	/**
	 * Text with given matchers.
	 *
	 * @param matchers the matchers
	 */
	public DefaultText(Matcher<?>... matchers){
		this(null, matchers);
	}

	/**
	 * Text with given index.
	 *
	 * @param index the index
	 * @param matchers the matchers
	 */
	public DefaultText(int index, Matcher<?>... matchers){
		this(null, index, matchers);
	}
	
	/**
	 * First text inside given composite.
	 *
	 * @param referencedComposite the referenced composite
	 */
	public DefaultText(ReferencedComposite referencedComposite){
		this(referencedComposite, 0);
	}

	/**
	 * Text with text value inside given composite.
	 *
	 * @param referencedComposite the referenced composite
	 * @param title the title
	 */
	public DefaultText(ReferencedComposite referencedComposite, String title) {
		this(referencedComposite, 0, new WithTextMatcher(title));
	}
	
	/**
	 * Text with given matchers inside given composite.
	 *
	 * @param referencedComposite the referenced composite
	 * @param matchers the matchers
	 */
	@SuppressWarnings("rawtypes")
	public DefaultText(ReferencedComposite referencedComposite, Matcher... matchers){
		this(referencedComposite, 0, matchers);
	}
	
	/**
	 * Text with given index inside given composite.
	 *
	 * @param referencedComposite the referenced composite
	 * @param index the index
	 * @param matchers the matchers
	 */
	public DefaultText(ReferencedComposite referencedComposite, int index, Matcher<?>... matchers){
		super(referencedComposite, index, matchers);
	}
}
