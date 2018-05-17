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
package org.eclipse.reddeer.eclipse.condition;

import org.eclipse.reddeer.common.condition.AbstractWaitCondition;
import org.eclipse.reddeer.common.wait.TimePeriod;
import org.eclipse.reddeer.eclipse.ui.console.ConsoleView;

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
	 * Construct the condition with {@link TimePeriod#DEFAULT}.
	 */
	public ConsoleHasNoChange() {
		this(TimePeriod.DEFAULT);
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
	 * @see org.eclipse.reddeer.common.condition.WaitCondition#test()
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
	 * @see org.eclipse.reddeer.common.condition.AbstractWaitCondition#description()
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
