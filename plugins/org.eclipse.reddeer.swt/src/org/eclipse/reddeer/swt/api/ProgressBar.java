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
package org.eclipse.reddeer.swt.api;

/**
 * API for progress bar manipulation.
 * 
 * @author Jiri Peterka
 * @author rhopp
 *
 */
public interface ProgressBar extends Control<org.eclipse.swt.widgets.ProgressBar> {

	/**
	 * Gets state of the progress bar.
	 * 
	 * @return current state (SWT.NORMAL, SWT.ERROR, SWT.PAUSED)
	 */
	int getState();
}
