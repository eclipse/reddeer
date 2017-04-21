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
package org.eclipse.reddeer.swt.impl.button;

import org.eclipse.swt.SWT;
import org.hamcrest.Matcher;
import org.eclipse.reddeer.swt.api.Button;
import org.eclipse.reddeer.core.matcher.WithMnemonicTextMatcher;
import org.eclipse.reddeer.core.reference.ReferencedComposite;

/**
 * PushButton is simple button implementation that can be pushed
 * @author Jiri Peterka
 *
 */
public class PushButton extends AbstractButton implements Button {

	/**
	 * Push button with index 0.
	 */
	public PushButton(){
		this((ReferencedComposite) null);
	}
	
	public PushButton(org.eclipse.swt.widgets.Button widget){
		super(widget);
	}
	
	/**
	 * Push button with index 0 inside given composite.
	 *
	 * @param referencedComposite the referenced composite
	 */
	public PushButton(ReferencedComposite referencedComposite){
		this(referencedComposite, 0);
	}
	
	/**
	 * Push button with given text.
	 *
	 * @param text the text
	 */
	public PushButton(String text) {
		this(null, text);
	}
	
	/**
	 * Push button with given text inside given composite.
	 *
	 * @param referencedComposite the referenced composite
	 * @param text the text
	 */
	public PushButton(ReferencedComposite referencedComposite, String text) {
		this(referencedComposite, 0, new WithMnemonicTextMatcher(text));
	}
	
	/**
	 * Push button that matches given matchers.
	 *
	 * @param matchers the matchers
	 */
	public PushButton(Matcher<?>... matchers) {
		this(null, matchers);
	}
	
	/**
	 * Push button that matches given matchers.
	 *
	 * @param referencedComposite the referenced composite
	 * @param matchers the matchers
	 */
	public PushButton(ReferencedComposite referencedComposite, Matcher<?>... matchers) {
		this(referencedComposite, 0, matchers);
	}
	
	/**
	 * Push button with given index that matches given matchers.
	 *
	 * @param index the index
	 * @param matchers the matchers
	 */
	public PushButton(int index, Matcher<?>... matchers) {
		this(null, index, matchers);
	}
	
	/**
	 * Push button with given index inside given composite that matches given matchers.
	 *
	 * @param referencedComposite the referenced composite
	 * @param index the index
	 * @param matchers the matchers
	 */
	public PushButton(ReferencedComposite referencedComposite, int index, Matcher<?>... matchers) {
		super(referencedComposite, index, SWT.PUSH, matchers);
	}
}
