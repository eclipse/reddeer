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
package org.jboss.reddeer.swt.impl.ccombo;

import org.hamcrest.Matcher;
import org.jboss.reddeer.core.matcher.WithTextMatcher;
import org.jboss.reddeer.core.reference.ReferencedComposite;
import org.jboss.reddeer.swt.api.CCombo;

/**
 * Default Custom Combo implementation.
 * 
 * @author Andrej Podhradsky
 *
 */
public class DefaultCCombo extends AbstractCCombo implements CCombo {

	/**
	 * Default constructor which looks for custom combo with index 0.
	 */
	public DefaultCCombo() {
		this((ReferencedComposite) null);
	}

	/**
	 * Finds custom combo inside given referenced composite.
	 *
	 * @param ref            composite inside which custom combo should be looked for
	 */
	public DefaultCCombo(ReferencedComposite ref) {
		this(ref, 0);
	}

	/**
	 * Finds custom combo with given text written inside.
	 *
	 * @param text            which is written in custom combo
	 */
	public DefaultCCombo(String text) {
		this(null, text);
	}

	/**
	 * Finds custom combo inside given referenced composite with given text.
	 *
	 * @param ref   composite inside which custom combo should be looked for
	 * @param text  text which is written in custom combo
	 */
	public DefaultCCombo(ReferencedComposite ref, String text) {
		this(ref, 0, new WithTextMatcher(text));
	}

	/**
	 * Finds custom combo matching to given matchers.
	 *
	 * @param matchers            to match custom combo
	 */
	public DefaultCCombo(Matcher<?>... matchers) {
		this(null, matchers);
	}

	/**
	 * Finds custom combo inside given referenced composite which is matching
	 * given matchers.
	 *
	 * @param ref            composite inside which custom combo should be looked for
	 * @param matchers            matchers to match the custom combo
	 */
	public DefaultCCombo(ReferencedComposite ref, Matcher<?>... matchers) {
		this(ref, 0, matchers);
	}

	/**
	 * Finds custom combo with given index.
	 *
	 * @param index            index of the custom combo
	 * @param matchers            matchers to match the custom combo
	 */
	public DefaultCCombo(int index, Matcher<?>... matchers) {
		this(null, index, matchers);
	}

	/**
	 * Finds custom combo inside given referenced composite with given index.
	 *
	 * @param ref            composite inside which custom combo should be looked for
	 * @param index            index of the custom combo
	 * @param matchers            matchers to match the custom combo
	 */
	public DefaultCCombo(ReferencedComposite ref, int index, Matcher<?>... matchers) {
		super(ref, index, matchers);
	}
}
