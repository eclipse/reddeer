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
package org.jboss.reddeer.swt.impl.list;

import org.hamcrest.Matcher;
import org.jboss.reddeer.swt.api.List;
import org.jboss.reddeer.core.matcher.WithLabelMatcher;
import org.jboss.reddeer.core.reference.ReferencedComposite;


/**
 * Default Label implementation.
 * @author Rastislav Wagner
 *
 */
public class DefaultList extends AbstractList implements List{
	
	/**
	 * List with index 0.
	 */
	public DefaultList(){
		this((ReferencedComposite) null);
	}
	
	/**
	 * List with index 0 inside given composite.
	 *
	 * @param referencedComposite the referenced composite
	 */
	public DefaultList(ReferencedComposite referencedComposite){
		this(referencedComposite, 0);
	}
	
	/**
	 * List with given label.
	 *
	 * @param label of list
	 */
	public DefaultList(String label){
		this(null, label);
	}
	
	/**
	 * List with given label inside given composite.
	 *
	 * @param referencedComposite the referenced composite
	 * @param label of list
	 */
	public DefaultList(ReferencedComposite referencedComposite, String label){
		this(referencedComposite, 0, new WithLabelMatcher(label));
	}
	
	/**
	 * List that matches given matchers.
	 *
	 * @param matchers the matchers
	 */
	public DefaultList(Matcher<?>... matchers){
		this(null, matchers);
	}
	
	/**
	 * List that matches given matchers inside given composite.
	 *
	 * @param referencedComposite the referenced composite
	 * @param matchers the matchers
	 */
	public DefaultList(ReferencedComposite referencedComposite, Matcher<?>... matchers){
		this(referencedComposite, 0, matchers);
	}
	
	/**
	 * List with given index that matches given matchers.
	 *
	 * @param index of list
	 * @param matchers the matchers
	 */
	public DefaultList(int index, Matcher<?>... matchers){
		this(null, index, matchers);
	}
	
	/**
	 * List with given index that matches given matchers inside given composite.
	 *
	 * @param referencedComposite the referenced composite
	 * @param index of list
	 * @param matchers the matchers
	 */
	public DefaultList(ReferencedComposite referencedComposite, int index, Matcher<?>... matchers){
		super(referencedComposite, index, matchers);
	}
}
