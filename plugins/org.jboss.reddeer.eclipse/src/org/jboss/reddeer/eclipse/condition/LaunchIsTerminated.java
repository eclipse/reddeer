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
package org.jboss.reddeer.eclipse.condition;

import org.jboss.reddeer.common.condition.AbstractWaitCondition;
import org.jboss.reddeer.eclipse.debug.ui.views.launch.TerminateButton;

/**
 * A wait condition which detects whether a debugging is terminated. It returns
 * true if the Terminate button is disabled.
 * 
 * @author Andrej Podhradsky
 *
 */
public class LaunchIsTerminated extends AbstractWaitCondition {

	private TerminateButton terminateButton;

	/* (non-Javadoc)
	 * @see org.jboss.reddeer.common.condition.WaitCondition#test()
	 */
	@Override
	public boolean test() {
		if (terminateButton == null) {
			terminateButton = new TerminateButton();
		}
		return !terminateButton.isEnabled();
	}

	/* (non-Javadoc)
	 * @see org.jboss.reddeer.common.condition.AbstractWaitCondition#description()
	 */
	@Override
	public String description() {
		return "Tool item with tooltip 'Terminate' is still enabled";
	}

}
