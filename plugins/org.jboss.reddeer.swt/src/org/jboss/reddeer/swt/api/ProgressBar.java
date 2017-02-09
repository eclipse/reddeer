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
package org.jboss.reddeer.swt.api;

import org.jboss.reddeer.swt.widgets.Widget;

/**
 * API for progress bar manipulation.
 * 
 * @author Jiri Peterka
 * @author rhopp
 *
 */
public interface ProgressBar extends Widget<org.eclipse.swt.widgets.ProgressBar> {

	/**
	 * Gets state of the progress bar.
	 * 
	 * @return current state (SWT.NORMAL, SWT.ERROR, SWT.PAUSED)
	 */
	int getState();
}
