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
package org.eclipse.reddeer.swt.impl.scale;

import org.hamcrest.Matcher;
import org.eclipse.reddeer.core.reference.ReferencedComposite;
/**
 * DefaultScale implementation represents most common Scale widget type
 * and provide API for basic operation needed in UI tests
 * @author Vlado Pakan
 *
 */
public class DefaultScale extends AbstractScale {

	/**
	 * Scale with index 0.
	 */
	public DefaultScale(){
		this((ReferencedComposite) null);
	}
	
	public DefaultScale(org.eclipse.swt.widgets.Scale widget){
		super(widget);
	}
	
	/**
	 * Scale with index 0 inside given composite.
	 *
	 * @param referencedComposite the referenced composite
	 */
	public DefaultScale(ReferencedComposite referencedComposite){
		this(referencedComposite, 0);
	}
	
	/**
	 * Scale that matches given matchers.
	 *
	 * @param matchers the matchers
	 */
	public DefaultScale(Matcher<?>... matchers){
		this(null, matchers);
	}
	
	/**
	 * Scale that matches given matchers inside given composite.
	 *
	 * @param referencedComposite the referenced composite
	 * @param matchers the matchers
	 */
	public DefaultScale(ReferencedComposite referencedComposite, Matcher<?>... matchers){
		this(referencedComposite, 0, matchers);
	}
	
	/**
	 * Scale with given index that matches given matchers.
	 *
	 * @param index of Scale
	 * @param matchers the matchers
	 */
	public DefaultScale(int index, Matcher<?>... matchers){
		this(null, index, matchers);
	}
	
	/**
	 * Scale with given index that matches given matchers inside given composite.
	 *
	 * @param referencedComposite the referenced composite
	 * @param index of Scale
	 * @param matchers the matchers
	 */
	public DefaultScale(ReferencedComposite referencedComposite, int index, Matcher<?>... matchers){
		super(referencedComposite, index, matchers);
	}
}
