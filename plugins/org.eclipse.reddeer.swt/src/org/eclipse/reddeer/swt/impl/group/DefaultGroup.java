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
package org.eclipse.reddeer.swt.impl.group;

import org.eclipse.swt.widgets.Group;
import org.hamcrest.Matcher;
import org.eclipse.reddeer.core.matcher.WithMnemonicTextMatcher;
import org.eclipse.reddeer.core.reference.ReferencedComposite;

/**
 * Default Group implementation
 * @author Rastislav Wagner
 * @since 0.4
 *
 */
public class DefaultGroup extends AbstractGroup {

	/**
	 * Default group constructor.
	 */
	public DefaultGroup(){
		this(null, 0);		
	}
	
	public DefaultGroup(Group widget){
		super(widget);
	}

	/**
	 * Group inside given composite.
	 *
	 * @param referencedComposite the referenced composite
	 */
	public DefaultGroup(ReferencedComposite referencedComposite){
		this(referencedComposite, 0);
	}

	/**
	 * Group with given text.
	 *
	 * @param text group text
	 */
	public DefaultGroup(String text){
		this(null, text);
	}

	/**
	 * Group with given text inside given composite.
	 *
	 * @param referencedComposite the referenced composite
	 * @param text group text
	 */
	public DefaultGroup(ReferencedComposite referencedComposite, String text){
		this(referencedComposite, 0, new WithMnemonicTextMatcher(text));
	}

	/**
	 * Group that matches given matchers.
	 *
	 * @param matchers the matchers
	 */
	public DefaultGroup(Matcher<?>... matchers) {
		this(null, matchers);
	}

	/**
	 * Group that matches given matchers.
	 *
	 * @param referencedComposite the referenced composite
	 * @param matchers the matchers
	 */
	public DefaultGroup(ReferencedComposite referencedComposite, Matcher<?>... matchers) {
		this(referencedComposite, 0, matchers);
	}

	/**
	 * Group with given index that matches given matchers.
	 *
	 * @param index group index
	 * @param matchers the matchers
	 */
	public DefaultGroup(int index, Matcher<?>... matchers){
		this(null, index);
	}

	/**
	 * Group with given index inside given composite that matches given matchers.
	 *
	 * @param referencedComposite the referenced composite
	 * @param index group index
	 * @param matchers the matchers
	 */
	public DefaultGroup(ReferencedComposite referencedComposite, int index, Matcher<?>... matchers){
		super(referencedComposite, index, matchers);
	}
}
