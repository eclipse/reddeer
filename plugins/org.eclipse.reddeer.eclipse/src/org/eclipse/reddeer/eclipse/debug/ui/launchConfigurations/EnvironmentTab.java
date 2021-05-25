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

import org.eclipse.reddeer.common.exception.RedDeerException;
import org.eclipse.reddeer.common.exception.WaitTimeoutExpiredException;
import org.eclipse.reddeer.common.wait.TimePeriod;
import org.eclipse.reddeer.common.wait.WaitUntil;
import org.eclipse.reddeer.swt.api.TableItem;
import org.eclipse.reddeer.swt.condition.ShellIsAvailable;
import org.eclipse.reddeer.swt.impl.button.NoButton;
import org.eclipse.reddeer.swt.impl.button.OkButton;
import org.eclipse.reddeer.swt.impl.button.PushButton;
import org.eclipse.reddeer.swt.impl.button.RadioButton;
import org.eclipse.reddeer.swt.impl.button.YesButton;
import org.eclipse.reddeer.swt.impl.shell.DefaultShell;
import org.eclipse.reddeer.swt.impl.table.DefaultTable;
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
	public void add(String name, String value) {
		add(name, value, true);
	}

	/**
	 * Add new environment variable
	 *
	 * @param name      variable name
	 * @param value     variable value
	 * @param overwrite if variable with the same name exists, then true for
	 *                  overwrite, else false
	 */
	public void add(String name, String value, boolean overwrite) {
		new PushButton("Add...").click();
		new WaitUntil(new ShellIsAvailable(ADD_SHELL_TITLE));
		new LabeledText("Name:").setText(name);
		new LabeledText("Value:").setText(value);
		new OkButton().click();
		try {
			new WaitUntil(new ShellIsAvailable(OVERWRITE_SHELL_TITLE), TimePeriod.SHORT);
			if (overwrite) {
				new YesButton().click();
			} else {
				new NoButton().click();
			}
		} catch (WaitTimeoutExpiredException e) {
			// variable is new, dont need to overwrite
		}
	}

	/**
	 * Select environment variable by name
	 *
	 * @param variableName variable name for select
	 */
	public void selectEnvironmentVariable(String variableName) {
		selectEnvironmentVariableImpl(() -> {
			int varId = getEvnironmentIdByName(variableName);
			new DefaultTable().getItem(varId).setChecked(true);
		});
	}

	private int getEvnironmentIdByName(String namePart) {
		List<TableItem> allEnvironments = new DefaultTable().getItems();
		for (TableItem environment : allEnvironments) {
			if (environment.getText().contains(namePart)) {
				return allEnvironments.indexOf(environment);
			}
		}
		new DefaultShell(SELECT_SHELL_TITLE).close();
		throw new RedDeerException("Environment variable name does not exists!");
	}

	/**
	 * Select environment variable by id
	 *
	 * @param variableId variable index for select
	 */
	public void selectEnvironmentVariable(int variableId) {
		selectEnvironmentVariableImpl(() -> {
			new DefaultTable().getItem(variableId).setChecked(true);
		});
	}

	/**
	 * Select/deselect all environment variables in select button list
	 *
	 * @param selectAllBool true if need to select all variables, false for deselect
	 */
	public void selectEnvironmentVariable(boolean selectAllBool) {
		selectEnvironmentVariableImpl(() -> {
			if (selectAllBool) {
				new PushButton("Select All").click();
			} else {
				new PushButton("Deselect All").click();
			}
		});
	}

	private void selectEnvironmentVariableImpl(Runnable selectedImpl) {
		new PushButton("Select...").click();
		new WaitUntil(new ShellIsAvailable(SELECT_SHELL_TITLE));
		selectedImpl.run();
		OkButton oB = new OkButton();
		if (oB.isEnabled()) { // no need to select if already selected
			oB.click();
		}
	}

	/**
	 * edit environment variable
	 *
	 * @param variableName variable name
	 * @param newName      new variable name, null for leave same
	 * @param newValue     new variable value, null for leave same
	 */
	public void edit(String variableName, String newName, String newValue) {
		getVariable(variableName).select();
		new PushButton("Edit...").click();
		new WaitUntil(new ShellIsAvailable(EDIT_SHELL_TITLE));
		if (newName != null) {
			new LabeledText("Name:").setText(newName);
		}
		if (newValue != null) {
			new LabeledText("Value:").setText(newValue);
		}
		new OkButton().click();
	}

	/**
	 * Remove environment variable
	 * 
	 * @param variableName variable name
	 */
	public void remove(String variableName) {
		getVariable(variableName).select();
		new PushButton("Remove").click();
	}

	/**
	 * Copy environment variable
	 * 
	 * @param variableName variable name
	 */
	public void copy(String variableName) {
		getVariable(variableName).select();
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
