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
package org.eclipse.reddeer.swt.impl.progressbar;

import org.hamcrest.Matcher;
import org.eclipse.reddeer.core.matcher.WithLabelMatcher;
import org.eclipse.reddeer.core.reference.ReferencedComposite;

public class DefaultProgressBar extends AbstractProgressBar {

	/**
	 * ProgressBar with index 0.
	 */
	public DefaultProgressBar(){
		this((ReferencedComposite) null);
	}
	
	public DefaultProgressBar(org.eclipse.swt.widgets.ProgressBar widget) {
		super(widget);
	}
	
	/**
	 * ProgressBar with index 0 inside given composite.
	 *
	 * @param referencedComposite the referenced composite
	 */
	public DefaultProgressBar(ReferencedComposite referencedComposite){
		this(referencedComposite, 0);
	}
	
	/**
	 * ProgressBar with given label.
	 *
	 * @param label of list
	 */
	public DefaultProgressBar(String label){
		this(null, label);
	}
	
	/**
	 * ProgressBar with given label inside given composite.
	 *
	 * @param referencedComposite the referenced composite
	 * @param label of list
	 */
	public DefaultProgressBar(ReferencedComposite referencedComposite, String label){
		this(referencedComposite, 0, new WithLabelMatcher(label));
	}
	
	/**
	 * ProgressBar that matches given matchers.
	 *
	 * @param matchers the matchers
	 */
	public DefaultProgressBar(Matcher<?>... matchers){
		this(null, matchers);
	}
	
	/**
	 * ProgressBar that matches given matchers inside given composite.
	 *
	 * @param referencedComposite the referenced composite
	 * @param matchers the matchers
	 */
	public DefaultProgressBar(ReferencedComposite referencedComposite, Matcher<?>... matchers){
		this(referencedComposite, 0, matchers);
	}
	
	/**
	 * ProgressBar with given index that matches given matchers.
	 *
	 * @param index of list
	 * @param matchers the matchers
	 */
	public DefaultProgressBar(int index, Matcher<?>... matchers){
		this(null, index, matchers);
	}
	
	/**
	 * ProgressBar with given index that matches given matchers inside given composite.
	 *
	 * @param referencedComposite the referenced composite
	 * @param index of list
	 * @param matchers the matchers
	 */
	public DefaultProgressBar(ReferencedComposite referencedComposite, int index, Matcher<?>... matchers){
		super(referencedComposite, index, matchers);
	}
}
