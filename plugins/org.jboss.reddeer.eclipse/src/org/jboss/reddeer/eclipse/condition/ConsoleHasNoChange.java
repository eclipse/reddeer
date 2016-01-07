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
import org.jboss.reddeer.common.wait.TimePeriod;
import org.jboss.reddeer.eclipse.ui.console.ConsoleView;

/**
 * Returns true if a console has no change for the specified time period.
 * 
 * @author Andrej Podhradsky
 * 
 */
public class ConsoleHasNoChange extends AbstractWaitCondition {

	private TimePeriod timePeriod;

	private String consoleText;
	private long consoleTime;

	/**
	 * Construct the condition with {@link TimePeriod#NORMAL}.
	 */
	public ConsoleHasNoChange() {
		this(TimePeriod.NORMAL);
	}

	/**
	 * Constructs the condition with a given time period.
	 * 
	 * @param timePeriod
	 *            Time period
	 */
	public ConsoleHasNoChange(TimePeriod timePeriod) {
		this.timePeriod = timePeriod;
		this.consoleText = getConsoleText();
		this.consoleTime = System.currentTimeMillis();
	}

	/* (non-Javadoc)
	 * @see org.jboss.reddeer.common.condition.WaitCondition#test()
	 */
	@Override
	public boolean test() {
		String currentConsoleText = getConsoleText();
		long currentConsoleTime = System.currentTimeMillis();

		if (!currentConsoleText.equals(consoleText)) {
			consoleText = currentConsoleText;
			consoleTime = currentConsoleTime;
			return false;
		}

		return currentConsoleTime - consoleTime - timePeriod.getSeconds() * 1000 >= 0;
	}

	/* (non-Javadoc)
	 * @see org.jboss.reddeer.common.condition.AbstractWaitCondition#description()
	 */
	@Override
	public String description() {
		return "Console is still changing";
	}

	private static String getConsoleText() {
		ConsoleView consoleView = new ConsoleView();
		consoleView.open();

		return consoleView.getConsoleText();
	}
}
