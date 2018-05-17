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
import org.eclipse.reddeer.eclipse.ui.console.ConsoleView;

/**
 * Wait condition for existence of text.
 * 
 * @author apodhrad, mlabuda@redhat.com, jkopriva@redhat.com
 * 
 */
public class ConsoleHasText extends AbstractWaitCondition {

	private String text;
	private ConsoleView consoleView;
	private String resultText;
	
	/**
	 * Creates new ConsoleHasText wait condition waiting until a console
	 * contains specified text.
	 * 
	 * @param view console view to use
	 * @param text Text
	 */
	public ConsoleHasText(ConsoleView view, String text) {
		this.text = text;
		consoleView = view;
		view.open();
	}
	
	/**
	 * Creates new ConsoleHasText wait condition waiting until a console
	 * contains specified text.
	 * 
	 * @param text Text
	 */
	public ConsoleHasText(String text) {
		this(new ConsoleView(), text);
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
			if (consoleText.contains(text)) {
				this.resultText = consoleText;
				return true;
			}
		}
		return false;
	}

	@Override
	public String description() {
		return "console contains '" + text + "'\n" + consoleView.getConsoleText();
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.reddeer.common.condition.WaitCondition#errorMessageWhile()
	 */
	@Override
	public String errorMessageWhile() {
		return "console contains '" + text + "'\n" + consoleView.getConsoleText();
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.reddeer.common.condition.WaitCondition#errorMessageUntil()
	 */
	@Override
	public String errorMessageUntil() {
		return "console does not contain text: '" + text + "', Console output:\n" + consoleView.getConsoleText();
	}
	
	@SuppressWarnings("unchecked")
	@Override 
	public String getResult() {
		return this.resultText;
	}	

}
