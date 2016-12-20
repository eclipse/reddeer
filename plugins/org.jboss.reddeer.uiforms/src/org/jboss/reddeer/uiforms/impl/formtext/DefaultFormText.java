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
package org.jboss.reddeer.uiforms.impl.formtext;

import org.hamcrest.Matcher;
import org.jboss.reddeer.core.reference.ReferencedComposite;
import org.jboss.reddeer.uiforms.matcher.FormTextWithTextMatcher;

/**
 * Default implementation of FormText widget
 * 
 * @author rhopp
 *
 */

public class DefaultFormText extends AbstractFormText {

	/**
	 * Represents widget of type FormText.
	 */

	public DefaultFormText() {
		this(0);
	}
	
	public DefaultFormText(org.eclipse.ui.forms.widgets.FormText widget){
		super(widget);
	}

	/**
	 * Represents nth widget of type FormText that matches given matchers.
	 *
	 * @param index the index
	 * @param matchers the matchers
	 */

	public DefaultFormText(int index, Matcher<?>... matchers) {
		this(null, index);
	}

	/**
	 * Represents widget of type FormText with given text.
	 *
	 * @param text the text
	 */

	public DefaultFormText(String text) {
		this(null, text);
	}

	/**
	 * Represents widget of type FormText which.
	 *
	 * @param matchers the matchers
	 */

	public DefaultFormText(Matcher<?>... matchers) {
		this(null, matchers);
	}

	/**
	 * Represents first widget of type FormText inside of given
	 * ReferencedComposite.
	 *
	 * @param referencedComposite the referenced composite
	 */

	public DefaultFormText(ReferencedComposite referencedComposite) {
		this(referencedComposite, 0);
	}

	/**
	 * Represents nth widget of type FormText inside of given
	 * ReferencedComposite that matches given matchers.
	 *
	 * @param referencedComposite the referenced composite
	 * @param index the index
	 * @param matchers the matchers
	 */

	public DefaultFormText(ReferencedComposite referencedComposite, int index, Matcher<?>... matchers) {
		super(referencedComposite, index, matchers);
	}

	/**
	 * Represents widget of type FormText inside of given ReferencedComposite
	 * with given text.
	 *
	 * @param referencedComposite the referenced composite
	 * @param text the text
	 */

	public DefaultFormText(ReferencedComposite referencedComposite, String text) {
		this(referencedComposite, new FormTextWithTextMatcher(text));
	}

	/**
	 * Represents first widget of type FormText inside of given
	 * ReferencedComposite that fulfils given matcher.
	 *
	 * @param referencedComposite the referenced composite
	 * @param matchers the matchers
	 */

	public DefaultFormText(ReferencedComposite referencedComposite,
			Matcher<?>... matchers) {
		this(referencedComposite, 0, matchers);
	}
}
