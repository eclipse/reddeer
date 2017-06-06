/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Red Hat Inc. - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.eclipse.debug.ui.preferences;

import org.eclipse.reddeer.jface.preference.PreferencePage;
import org.eclipse.reddeer.swt.impl.button.CheckBox;
import org.eclipse.reddeer.swt.impl.text.LabeledText;

/**
 * Represents <i>Console</i> preference page
 * 
 * @author Andrej Podhradsky (apodhrad@redhat.com)
 * 
 */
public class ConsolePreferencePage extends PreferencePage {

	public static final String CATEGORY = "Run/Debug";
	public static final String PAGE_NAME = "Console";

	public static final String LIMIT_OUTPUT = "Limit console output";
	public static final String CONSOLE_SIZE = "Console buffer size (characters):";
	public static final String SHOW_ON_OUTPUT = "Show when program writes to standard out";
	public static final String SHOW_ON_ERROR = "Show when program writes to standard error";

	public ConsolePreferencePage() {
		super(CATEGORY, PAGE_NAME);
	}

	/**
	 * Decides whether a check box for limiting console output is checked.
	 * 
	 * @return true if the check box checked; false otherwise
	 */
	public boolean isConsoleOutputLimited() {
		return new CheckBox(LIMIT_OUTPUT).isChecked();
	}

	/**
	 * Toggles a check box for limiting console output.
	 * 
	 * @param checked
	 */
	public void toggleConsoleOutputLimited(boolean checked) {
		new CheckBox(LIMIT_OUTPUT).toggle(checked);
	}

	/**
	 * Gets console output size.
	 * 
	 * @return console output size
	 */
	public int getConsoleOutputSize() {
		return Integer.valueOf(new LabeledText(CONSOLE_SIZE).getText());
	}

	/**
	 * Sets console output size. This method also checks a check box for limiting console output.
	 * 
	 * @param size
	 */
	public void setConsoleOutputSize(int size) {
		toggleConsoleOutputLimited(true);
		new LabeledText(CONSOLE_SIZE).setText(String.valueOf(size));
	}

	/**
	 * Decides whether a check box for showing a console on standard output is checked.
	 * 
	 * @return true if the check box is checked; false otherwise
	 */
	public boolean isConsoleOpenedOnOutput() {
		return new CheckBox(SHOW_ON_OUTPUT).isChecked();
	}

	/**
	 * Toggles a check box for showing a console on standard output.
	 * 
	 * @param checked
	 */
	public void toggleShowConsoleOnOutput(boolean checked) {
		new CheckBox(SHOW_ON_OUTPUT).toggle(checked);
	}

	/**
	 * Decides whether a check box for showing a console on standard error is checked.
	 * 
	 * @return true if the check box is checked; false otherwise
	 */
	public boolean isConsoleOpenedOnError() {
		return new CheckBox(SHOW_ON_ERROR).isChecked();
	}

	/**
	 * Toggles a check box for showing a console on standard error.
	 * 
	 * @param checked
	 */
	public void toggleShowConsoleErrorWrite(boolean checked) {
		new CheckBox(SHOW_ON_ERROR).toggle(checked);
	}

}