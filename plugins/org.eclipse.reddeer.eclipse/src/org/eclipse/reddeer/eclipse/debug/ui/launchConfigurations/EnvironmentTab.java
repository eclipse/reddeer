/******************************************************************************* 
 * Copyright (c) 2021 Red Hat, Inc. 
 * Distributed under license by Red Hat, Inc. All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 ******************************************************************************/
package org.eclipse.reddeer.eclipse.debug.ui.launchConfigurations;

import java.util.List;

import org.eclipse.reddeer.common.exception.WaitTimeoutExpiredException;
import org.eclipse.reddeer.common.wait.TimePeriod;
import org.eclipse.reddeer.common.wait.WaitUntil;
import org.eclipse.reddeer.swt.api.TableItem;
import org.eclipse.reddeer.swt.condition.ShellIsAvailable;
import org.eclipse.reddeer.swt.impl.button.OkButton;
import org.eclipse.reddeer.swt.impl.button.PushButton;
import org.eclipse.reddeer.swt.impl.button.RadioButton;
import org.eclipse.reddeer.swt.impl.button.YesButton;
import org.eclipse.reddeer.swt.impl.table.DefaultTable;
import org.eclipse.reddeer.swt.impl.table.DefaultTableItem;
import org.eclipse.reddeer.swt.impl.text.LabeledText;

/**
 * Represents Environment tab in Launch Configuration
 * 
 * @author Oleksii Korniienko olkornii@redhat.com
 *
 */
public class EnvironmentTab extends LaunchConfigurationTab {

	private static final String ADD_SHELL_TITLE = "New Environment Variable";
	private static final String SELECT_SHELL_TITLE = "Select Environment Variables";
	private static final String EDIT_SHELL_TITLE = "Edit Environment Variable";
	private static final String OVERWRITE_SHELL_TITLE = "Overwrite variable?";

	public EnvironmentTab() {
		super("Environment");
	}

	/**
	 * Return the list of all environment variables
	 *
	 * @return list of items
	 */
	public List<TableItem> getAllVariables() {
		return new DefaultTable().getItems();
	}

	/**
	 * Return the environment variable by name
	 *
	 * @return Environment Tab item
	 */
	public TableItem getVariable(String variableName) {
		return new DefaultTable().getItem(variableName);
	}

	/**
	 * Return the environment variable by id
	 *
	 * @return Environment Tab item
	 */
	public TableItem getVariable(int variableId) {
		return new DefaultTable().getItem(variableId);
	}

	/**
	 * Add new environment variable
	 *
	 * @param name  variable name
	 * @param value variable value
	 */
	public void add(String name, String nalue) {
		new PushButton("Add...").click();
		new WaitUntil(new ShellIsAvailable(ADD_SHELL_TITLE));
		new LabeledText("Name:").setText(name);
		new LabeledText("Value:").setText(nalue);
		new OkButton().click();
		try {
			new WaitUntil(new ShellIsAvailable(OVERWRITE_SHELL_TITLE), TimePeriod.SHORT);
			new YesButton().click();
		} catch (WaitTimeoutExpiredException e) {
		}
	}

	/**
	 * Select environment variable by name
	 *
	 * @param variableName variable name for select
	 */
	public void selectEnvironmentVariable(String variableName) {
		new PushButton("Select...").click();
		new WaitUntil(new ShellIsAvailable(SELECT_SHELL_TITLE));
		new DefaultTableItem(variableName).setChecked(true);
		OkButton oB = new OkButton();
		if (oB.isEnabled()) {
			oB.click();
		}
	}

	/**
	 * Select environment variable by id
	 *
	 * @param variableId variable index for select
	 */
	public void selectEnvironmentVariable(int variableId) {
		new PushButton("Select...").click();
		new WaitUntil(new ShellIsAvailable(SELECT_SHELL_TITLE));
		new DefaultTable().getItem(variableId).setChecked(true);
		OkButton oB = new OkButton();
		if (oB.isEnabled()) {
			oB.click();
		}
	}

	/**
	 * Select/deselect all environment variables in select button list
	 *
	 * @param selectAllBool true if need to select all variables, false for deselect
	 */
	public void selectEnvironmentVariable(boolean selectAllBool) {
		new PushButton("Select...").click();
		new WaitUntil(new ShellIsAvailable(SELECT_SHELL_TITLE));
		if (selectAllBool) {
			new PushButton("Select All").click();
		} else {
			new PushButton("Deselect All").click();
		}
		OkButton oB = new OkButton();
		if (oB.isEnabled()) {
			oB.click();
		}
	}

	/**
	 * edit environment variable
	 *
	 * @param name  new variable name
	 * @param value new variable value
	 */
	public void edit(String name, String value) {
		new PushButton("Edit...").click();
		new WaitUntil(new ShellIsAvailable(EDIT_SHELL_TITLE));
		new LabeledText("Name:").setText(name);
		new LabeledText("Value:").setText(value);
		new OkButton().click();
	}

	/**
	 * Remove environment variable
	 */
	public void remove() {
		new PushButton("Remove").click();
	}

	/**
	 * Copy environment variable
	 */
	public void copy() {
		new PushButton("Copy").click();
	}

	/**
	 * Paste environment variable
	 */
	public void paste() {
		new PushButton("Paste").click();
	}

	/**
	 * Append environment to native environment
	 * 
	 * @return append radio button
	 */
	public RadioButton getAppendRadioButton() {
		return new RadioButton("Append environment to native environment");
	}

	/**
	 * Replace native environment with specified environment
	 * 
	 * @return replace radio button
	 */
	public RadioButton getReplaceRadioButton() {
		return new RadioButton("Replace native environment with specified environment");
	}
}
