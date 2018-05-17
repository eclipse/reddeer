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
package org.eclipse.reddeer.swt.impl.progressbar;

import org.eclipse.swt.SWT;

/**
 * This class represents progress bar with style SWT.HORIZONTAL
 * 
 * @author rhopp
 *
 */

public class HorizontalProgressBar extends DeterminateProgressBar {

	/**
	 * Instantiates horizontal progressbar with given index.
	 *
	 * @param index the index
	 */
	public HorizontalProgressBar(int index) {
		super(index, SWT.HORIZONTAL);
	}
	
	/**
	 * Instantiates horizontal progressbar.
	 */
	public HorizontalProgressBar() {
		super(0, SWT.HORIZONTAL);
	}

}
