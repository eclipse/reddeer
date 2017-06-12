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
 * OpenButton is a simple button implementation for Open button
 * 
 * @author Andrej Podhradsky
 *
 */
public class OpenButton extends PredefinedButton {

	/**
	 * OpenButton default constructor.
	 */
	public OpenButton() {
		this(null);

	}

	/**
	 * Instantiates new OpenButton
	 * 
	 * @param referencedComposite
	 *            Composite where button should be looked up
	 */
	public OpenButton(ReferencedComposite referencedComposite) {
		this(referencedComposite, 0);

	}

	/**
	 * Instantiates new OpenButton
	 * 
	 * @param referencedComposite
	 *            Composite where button should be looked up
	 * @param index
	 *            Index of Open button
	 */
	public OpenButton(ReferencedComposite referencedComposite, int index) {
		super(referencedComposite, index, "Open", SWT.PUSH);

	}

}
