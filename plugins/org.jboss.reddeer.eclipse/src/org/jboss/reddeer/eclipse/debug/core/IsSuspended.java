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
package org.jboss.reddeer.eclipse.debug.core;

import org.jboss.reddeer.common.condition.AbstractWaitCondition;
import org.jboss.reddeer.common.wait.AbstractWait;
import org.jboss.reddeer.common.wait.TimePeriod;

/**
 * A wait condition which detects whether a debugging is suspended. It returns
 * true if the Resume button is enabled.
 * 
 * @author Andrej Podhradsky
 *
 */
public class IsSuspended extends AbstractWaitCondition {

	private ResumeButton resumeButton;

	/* (non-Javadoc)
	 * @see org.jboss.reddeer.common.condition.WaitCondition#test()
	 */
	@Override
	public boolean test() {
		if (resumeButton == null) {
			resumeButton = new ResumeButton();
		}
		if (resumeButton.isEnabled()) {
			AbstractWait.sleep(TimePeriod.SHORT);
			return true;
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see org.jboss.reddeer.common.condition.AbstractWaitCondition#description()
	 */
	@Override
	public String description() {
		return "Debugger didn't suspend";
	}

}
