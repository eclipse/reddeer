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
 * Wait condition for existence of text.
 * 
 * @author apodhrad, mlabuda@redhat.com
 * @contributor jkopriva@redhat.com
 * 
 */
public class ConsoleHasText extends AbstractWaitCondition {

	private String text;
	private ConsoleView consoleView;
	
	/**
	 * Creates new ConsoleHasText wait condition waiting until a console
	 * contains specified text.
	 * 
	 * @param text Text
	 */
	public ConsoleHasText(String text) {
		this.text = text;
		consoleView = new ConsoleView();
		consoleView.activate();
	}

	/**
	 * Creates new ConsoleHasText wait condition waiting until a console 
	 * has any text. If console is opened, but there is no console to display
	 * at the time, waiting is not satisfied.
	 */
	public ConsoleHasText() {
		this("");
	}
	
	@Override
	public boolean test() {
		String consoleText = consoleView.getConsoleText();
		if (consoleText == null) {
			return false;
		} else {
			return consoleText.contains(text);
		}
	}

	@Override
	public String description() {
		return "console contains '" + text + "'\n" + consoleView.getConsoleText();
	}
	
	/* (non-Javadoc)
	 * @see org.jboss.reddeer.common.condition.WaitCondition#errorMessageWhile()
	 */
	@Override
	public String errorMessageWhile() {
		return "console contains '" + text + "'\n" + consoleView.getConsoleText();
	}
	
	/* (non-Javadoc)
	 * @see org.jboss.reddeer.common.condition.WaitCondition#errorMessageUntil()
	 */
	@Override
	public String errorMessageUntil() {
		return "console does not contain text: '" + text + "', Console output:\n" + consoleView.getConsoleText();
	}
	
}
