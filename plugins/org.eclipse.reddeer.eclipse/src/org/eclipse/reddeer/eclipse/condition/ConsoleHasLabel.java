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

import org.hamcrest.Matcher;
import org.hamcrest.core.IsEqual;
import org.eclipse.reddeer.common.condition.AbstractWaitCondition;
import org.eclipse.reddeer.eclipse.ui.console.ConsoleView;

/**
 * Returns true if the console label matches a given String matcher
 * or given text
 * 
 * @author vlado pakan
 * 
 */
public class ConsoleHasLabel extends AbstractWaitCondition {

	private Matcher<String> matcher;
	private static ConsoleView consoleView = null;
	private String resultLabel;

	/**
	 * Construct the condition with a given text.
	 * 
	 * @param text Text
	 */
	public ConsoleHasLabel(String text) {
		this.matcher = new IsEqual<String>(text);
	}
	
	/**
	 * Instantiates a new console has label.
	 *
	 * @param matcher the matcher
	 */
	public ConsoleHasLabel(Matcher<String> matcher) {
		this.matcher = matcher;
	}
	

	/* (non-Javadoc)
	 * @see org.eclipse.reddeer.common.condition.WaitCondition#test()
	 */
	@Override
	public boolean test() {
		String consoleLabel = ConsoleHasLabel.getConsoleLabel();
		if (matcher.matches(consoleLabel)) {
			this.resultLabel = consoleLabel;
			return true;
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.reddeer.common.condition.AbstractWaitCondition#description()
	 */
	@Override
	public String description() {
		return "console label matches '" + matcher ;
	}

	private static String getConsoleLabel() {
		if (consoleView == null){
			consoleView = new ConsoleView();
		}
		consoleView.open();
		return consoleView.getConsoleLabel();
	}

	@SuppressWarnings("unchecked")
	@Override 
	public String getResult() {
		return this.resultLabel;
	}
	
}
