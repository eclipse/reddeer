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

import org.eclipse.reddeer.core.reference.ReferencedComposite;
import org.eclipse.swt.SWT;

/**
 * ApplyAndCloseButton is a simple button implementation for 'Apply and Close'
 * button
 * 
 * @author Andrej Podhradsky
 *
 */
public class ApplyAndCloseButton extends PredefinedButton {

	/**
	 * OpenButton default constructor.
	 */
	public ApplyAndCloseButton() {
		this(null);

	}

	/**
	 * Instantiates new ApplyAndCloseButton
	 * 
	 * @param referencedComposite
	 *            Composite where button should be looked up
	 */
	public ApplyAndCloseButton(ReferencedComposite referencedComposite) {
		this(referencedComposite, 0);

	}

	/**
	 * Instantiates new ApplyAndCloseButton
	 * 
	 * @param referencedComposite
	 *            Composite where button should be looked up
	 * @param index
	 *            Index of Open button
	 */
	public ApplyAndCloseButton(ReferencedComposite referencedComposite, int index) {
		super(referencedComposite, index, "Apply and Close", SWT.PUSH);

	}

}
