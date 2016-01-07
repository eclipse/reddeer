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
package org.jboss.reddeer.swt.impl.progressbar;

import org.jboss.reddeer.core.matcher.WithStyleMatcher;


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
