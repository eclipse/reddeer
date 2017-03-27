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
package org.jboss.reddeer.swt.impl.button;

import org.eclipse.swt.SWT;
import org.jboss.reddeer.core.reference.ReferencedComposite;

/**
 * BackButton is a simple button implementation for "Back" button
 * @author Jiri Peterka
 *
 */
public class BackButton extends PredefinedButton {

	
	/**
	 * BackButton default constructor.
	 */
	public BackButton() {		
		this(null);
		
	}
	
	/**
	 * Instantiates new BackButton
	 * @param referencedComposite composite where button should be looked up
	 */
	public BackButton(ReferencedComposite referencedComposite) {		
		this(referencedComposite, 0);
		
	}
	
	/**
	 * Instantiates new BackButton
	 * @param referencedComposite composite where button should be looked up
	 * @param index index of back button
	 */
	public BackButton(ReferencedComposite referencedComposite, int index) {		
		super(referencedComposite, index, "< Back", SWT.PUSH);
		
	}

}
