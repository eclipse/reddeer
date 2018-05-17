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
package org.eclipse.reddeer.swt.impl.combo;

import org.hamcrest.Matcher;
import org.eclipse.reddeer.swt.api.Combo;
import org.eclipse.reddeer.core.matcher.WithTextMatcher;
import org.eclipse.reddeer.core.reference.ReferencedComposite;

/**
 * Default Combo implementation. Most standard Combo implementation
 * @author Rastislav Wagner, Lucia Jelinkova
 *
 */
public class DefaultCombo extends AbstractCombo implements Combo{
	
	/**
	 * Default constructor which looks for combo with index 0.
	 */
	public DefaultCombo(){
		this((ReferencedComposite) null);
	}
	
	public DefaultCombo(org.eclipse.swt.widgets.Combo widget) {
		super(widget);
	}
	
	/**
	 * Finds combo inside given referenced composite.
	 *
	 * @param ref composite inside which combo should be looked for
	 */
	public DefaultCombo(ReferencedComposite ref){
		this(ref,0);
	}
	
	/**
	 * Finds combo with given text written inside.
	 *
	 * @param text which is written in combo
	 */
	public DefaultCombo(String text){
		this(null,text);
	}
	
	/**
	 * Finds combo inside given referenced composite with given text.
	 *
	 * @param ref composite inside which combo should be looked for
	 * @param text which is written in combo
	 */
	public DefaultCombo(ReferencedComposite ref, String text){
		this(ref, 0, new WithTextMatcher(text));
	}

	/**
	 * Finds combo matching to given matchers.
	 *
	 * @param matchers to match combo
	 */
	public DefaultCombo(Matcher<?>... matchers){
		this(null, matchers);
	}
	
	/**
	 * Finds combo inside given referenced composite which is matching given matchers.
	 *
	 * @param ref composite inside which combo should be looked for
	 * @param matchers matchers to match combo
	 */
	public DefaultCombo(ReferencedComposite ref, Matcher<?>... matchers){
		this(ref, 0, matchers);
	}
	
	/**
	 * Finds combo with given index.
	 *
	 * @param index of combo
	 * @param matchers the matchers
	 */
	public DefaultCombo(int index, Matcher<?>... matchers){
		this(null, index, matchers);
	}
	
	/**
	 * Finds combo inside given referenced composite with given index.
	 *
	 * @param ref composite inside which combo should be looked for
	 * @param index of combo
	 * @param matchers the matchers
	 */
	public DefaultCombo(ReferencedComposite ref, int index, Matcher<?>... matchers){
		super(ref, index, matchers);
	}
}
