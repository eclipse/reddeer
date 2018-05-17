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
package org.eclipse.reddeer.swt.impl.button;

import org.eclipse.swt.SWT;
import org.eclipse.reddeer.core.reference.ReferencedComposite;

/**
 * BackButton is simple button implementation for "Yes" button
 * @author Jiri Peterka
 *
 */
public class YesButton extends PredefinedButton {

	
	/**
	 * YesButton default constructor.
	 */
	public YesButton() {		
		this(null);
		
	}
	
	/**
	 * Instantiates new YesButton
	 * @param referencedComposite composite where button should be looked up
	 */
	public YesButton(ReferencedComposite referencedComposite) {		
		this(referencedComposite, 0);
		
	}
	
	/**
	 * Instantiates new YesButton
	 * @param referencedComposite composite where button should be looked up
	 * @param index index of yes button
	 */
	public YesButton(ReferencedComposite referencedComposite, int index) {		
		super(referencedComposite, index, "Yes", SWT.PUSH);
		
	}

}
