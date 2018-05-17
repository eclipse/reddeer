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

import org.eclipse.reddeer.core.handler.ProgressBarHandler;
import org.eclipse.reddeer.core.matcher.WithStyleMatcher;


/**
 * This class represents determinate progress bars (both horizontal and vertical) with possibility to read status. 
 * 
 * @author rhopp
 *
 */

public abstract class DeterminateProgressBar extends DefaultProgressBar {
	
	protected DeterminateProgressBar(int index, int style) {
		super(index, new WithStyleMatcher(style));
	}
	
	/**
	 * Gets the maximum.
	 *
	 * @return maximum value of this progressbar
	 */
	public int getMaximum(){
		return ProgressBarHandler.getInstance().getMaximum(swtWidget);
	}
	
	/**
	 * Gets the minimum.
	 *
	 * @return minimum value of this progressbar
	 */
	public int getMinimum(){
		return ProgressBarHandler.getInstance().getMinimum(swtWidget);
	}
	
	/**
	 * Gets the selection.
	 *
	 * @return current value of this progressbar
	 */
	public int getSelection(){
		return ProgressBarHandler.getInstance().getSelection(swtWidget);
	}

}
