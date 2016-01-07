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
package org.jboss.reddeer.swt.impl.scale;

import org.hamcrest.Matcher;
import org.jboss.reddeer.core.reference.ReferencedComposite;
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
