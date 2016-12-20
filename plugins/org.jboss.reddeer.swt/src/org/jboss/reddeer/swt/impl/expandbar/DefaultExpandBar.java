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
package org.jboss.reddeer.swt.impl.expandbar;

import org.hamcrest.Matcher;
import org.jboss.reddeer.core.reference.ReferencedComposite;

/**
 * Default Expand Bar implementation
 * @author Vlado Pakan
 *
 */
public class DefaultExpandBar extends AbstractExpandBar {

	/**
	 * Default parameter-less constructor.
	 */
	public DefaultExpandBar() {
		this(0);
	}
	
	public DefaultExpandBar(org.eclipse.swt.widgets.ExpandBar widget){
		super(widget);
	}
	
	/**
	 * ExpandBar inside given composite.
	 *
	 * @param referencedComposite the referenced composite
	 */
	public DefaultExpandBar(ReferencedComposite referencedComposite) {
		this(referencedComposite, 0);
	}
	
	/**
	 * Expand bar that matches given matchers.
	 *
	 * @param matchers the matchers
	 */
	public DefaultExpandBar(Matcher<?>... matchers) {
		this(null, matchers);
	}
	
	/**
	 * Expand bar that matches given matchers.
	 *
	 * @param referencedComposite the referenced composite
	 * @param matchers the matchers
	 */
	public DefaultExpandBar(ReferencedComposite referencedComposite, Matcher<?>... matchers) {
		this(referencedComposite, 0, matchers);
	}
	
	/**
	 * Expand Bar with specified index that matches given matchers will be constructed .
	 *
	 * @param index the index
	 * @param matchers the matchers
	 */
	public DefaultExpandBar(int index, Matcher<?>... matchers) {
		this(null, index, matchers);
	}
	
	/**
	 * Expand Bar with specified index that matches given matchers inside given composite will be constructed .
	 *
	 * @param referencedComposite the referenced composite
	 * @param index the index
	 * @param matchers the matchers
	 */
	public DefaultExpandBar(ReferencedComposite referencedComposite, int index, Matcher<?>... matchers) {
		super(referencedComposite, index, matchers);
	}
}
