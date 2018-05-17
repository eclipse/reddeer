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
package org.eclipse.reddeer.eclipse.debug.ui.preferences;

import org.eclipse.reddeer.core.reference.ReferencedComposite;
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

	public ConsolePreferencePage(ReferencedComposite referencedComposite) {
		super(referencedComposite, CATEGORY, PAGE_NAME);
	}

	/**
	 * Decides whether a check box for limiting console output is checked.
	 * 
	 * @return true if the check box checked; false otherwise
	 */
	public boolean isConsoleOutputLimited() {
		return new CheckBox(this, LIMIT_OUTPUT).isChecked();
	}

	/**
	 * Toggles a check box for limiting console output.
	 * 
	 * @param checked
	 */
	public ConsolePreferencePage toggleConsoleOutputLimited(boolean checked) {
		new CheckBox(this, LIMIT_OUTPUT).toggle(checked);
		return this;
	}

	/**
	 * Gets console output size.
	 * 
	 * @return console output size
	 */
	public int getConsoleOutputSize() {
		return Integer.valueOf(new LabeledText(this, CONSOLE_SIZE).getText());
	}

	/**
	 * Sets console output size. This method also checks a check box for limiting console output.
	 * 
	 * @param size
	 */
	public ConsolePreferencePage setConsoleOutputSize(int size) {
		toggleConsoleOutputLimited(true);
		new LabeledText(this, CONSOLE_SIZE).setText(String.valueOf(size));
		return this;
	}

	/**
	 * Decides whether a check box for showing a console on standard output is checked.
	 * 
	 * @return true if the check box is checked; false otherwise
	 */
	public boolean isConsoleOpenedOnOutput() {
		return new CheckBox(this, SHOW_ON_OUTPUT).isChecked();
	}

	/**
	 * Toggles a check box for showing a console on standard output.
	 * 
	 * @param checked
	 */
	public ConsolePreferencePage toggleShowConsoleOnOutput(boolean checked) {
		new CheckBox(this, SHOW_ON_OUTPUT).toggle(checked);
		return this;
	}

	/**
	 * Decides whether a check box for showing a console on standard error is checked.
	 * 
	 * @return true if the check box is checked; false otherwise
	 */
	public boolean isConsoleOpenedOnError() {
		return new CheckBox(this, SHOW_ON_ERROR).isChecked();
	}

	/**
	 * Toggles a check box for showing a console on standard error.
	 * 
	 * @param checked
	 */
	public ConsolePreferencePage toggleShowConsoleErrorWrite(boolean checked) {
		new CheckBox(this, SHOW_ON_ERROR).toggle(checked);
		return this;
	}

}