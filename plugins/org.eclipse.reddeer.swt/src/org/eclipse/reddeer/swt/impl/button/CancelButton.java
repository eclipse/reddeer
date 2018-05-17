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
 * Cancel button implementation
 * @author Jiri Peterka
 *
 */
public class CancelButton extends PredefinedButton {
	
	/**
	 * CancelButton default constructor.
	 */
	public CancelButton() {		
		this(null);
		
	}
	
	/**
	 * Instantiates new CancelButton
	 * @param referencedComposite composite where button should be looked up
	 */
	public CancelButton(ReferencedComposite referencedComposite) {		
		this(referencedComposite, 0);
		
	}
	
	/**
	 * Instantiates new CancelButton
	 * @param referencedComposite composite where button should be looked up
	 * @param index index of cancel button
	 */
	public CancelButton(ReferencedComposite referencedComposite, int index) {		
		super(referencedComposite, index, "Cancel", SWT.PUSH);
		
	}

}
