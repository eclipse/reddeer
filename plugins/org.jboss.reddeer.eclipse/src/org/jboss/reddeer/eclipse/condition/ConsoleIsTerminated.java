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
import org.jboss.reddeer.eclipse.ui.console.ConsoleView;

/**
 * Returns true if console is terminated
 * 
 * @author vlado pakan
 * 
 */
public class ConsoleIsTerminated extends AbstractWaitCondition {

	private ConsoleView consoleView = new ConsoleView();

	/**
	 * Construct the condition.
	 */
	public ConsoleIsTerminated() {
	}

	/* (non-Javadoc)
	 * @see org.jboss.reddeer.common.condition.WaitCondition#test()
	 */
	@Override
	public boolean test() {
		consoleView.open();
		return consoleView.consoleIsTerminated();
	}

	/* (non-Javadoc)
	 * @see org.jboss.reddeer.common.condition.AbstractWaitCondition#description()
	 */
	@Override
	public String description() {
		return "console is terminated";
	}

}
