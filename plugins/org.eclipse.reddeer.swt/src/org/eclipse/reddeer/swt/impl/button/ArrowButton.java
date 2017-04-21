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
import org.eclipse.reddeer.core.reference.ReferencedComposite;

/**
 * Arrow Button is button implementation with arrow symbol as label
 * @author Vlado Pakan
 *
 */
public class ArrowButton extends AbstractButton implements Button {

	/**
	 * Arrow button with index 0.
	 */
	public ArrowButton(){
		this((ReferencedComposite) null);
	}
	
	public ArrowButton(org.eclipse.swt.widgets.Button widget){
		super(widget);
	}
	
	/**
	 * Arrow button with index 0 inside given composite.
	 *
	 * @param referencedComposite the referenced composite
	 */
	public ArrowButton(ReferencedComposite referencedComposite){
		this(referencedComposite, 0);
	}
	
	/**
	 * Arrow button that matches given matchers.
	 *
	 * @param matchers the matchers
	 */
	public ArrowButton(Matcher<?>... matchers) {
		this(null, matchers);
	}
	
	/**
	 * Arrow button that matches given matchers.
	 *
	 * @param referencedComposite the referenced composite
	 * @param matchers the matchers
	 */
	public ArrowButton(ReferencedComposite referencedComposite, Matcher<?>... matchers) {
		this(referencedComposite, 0, matchers);
	}
	
	/**
	 * Arrow button with given index that matches given matchers.
	 *
	 * @param index the index
	 * @param matchers the matchers
	 */
	public ArrowButton(int index, Matcher<?>... matchers) {
		this(null, index, matchers);
	}
	
	/**
	 * Arrow button with given index inside given composite that matches given matchers.
	 *
	 * @param referencedComposite the referenced composite
	 * @param index the index
	 * @param matchers the matchers
	 */
	public ArrowButton(ReferencedComposite referencedComposite, int index, Matcher<?>... matchers) {
		super(referencedComposite, index, SWT.ARROW);
	}
}
