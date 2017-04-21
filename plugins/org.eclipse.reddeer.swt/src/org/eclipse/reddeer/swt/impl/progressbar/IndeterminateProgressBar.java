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
package org.eclipse.reddeer.swt.impl.progressbar;

import org.eclipse.swt.SWT;
import org.eclipse.reddeer.core.matcher.WithStyleMatcher;

/**
 * This class represents progressbar with style SWT.INDETERMINATE (without possibility to read status).
 * This progressbar could be either SWT.HORIZONTAL or SWT.VERTICAL
 * 
 * @author rhopp
 *
 */

public class IndeterminateProgressBar extends DefaultProgressBar {

	/**
	 * Instantiates indeterminate progressbar.
	 */
	public IndeterminateProgressBar(){
		this(0);
	}
	
	/**
	 * Instantiates indeterminate progressbar with given index.
	 *
	 * @param index the index
	 */
	public IndeterminateProgressBar(int index) {
		super(index, new WithStyleMatcher(SWT.INDETERMINATE));
	}

}
