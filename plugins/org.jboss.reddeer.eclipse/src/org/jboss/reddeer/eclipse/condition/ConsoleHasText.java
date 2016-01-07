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
 * Returns true if the console contains a given text
 * 
 * @author apodhrad
 * 
 */
public class ConsoleHasText extends AbstractWaitCondition {

	private String text;

	/**
	 * Construct the condition with a given text.
	 * 
	 * @param text Text
	 */
	public ConsoleHasText(String text) {
		this.text = text;
	}

	/* (non-Javadoc)
	 * @see org.jboss.reddeer.common.condition.WaitCondition#test()
	 */
	@Override
	public boolean test() {
		String consoleText = getConsoleText();
		return consoleText.contains(text);
	}

	/* (non-Javadoc)
	 * @see org.jboss.reddeer.common.condition.AbstractWaitCondition#description()
	 */
	@Override
	public String description() {
		String consoleText = getConsoleText();
		return "console contains '" + text + "'\n" + consoleText;
	}

	private static String getConsoleText() {
		ConsoleView consoleView = new ConsoleView();
		consoleView.open();
		return consoleView.getConsoleText();
	}

}
