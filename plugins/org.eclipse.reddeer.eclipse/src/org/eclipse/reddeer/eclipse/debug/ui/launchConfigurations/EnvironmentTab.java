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

import org.eclipse.reddeer.common.wait.WaitUntil;
import org.eclipse.reddeer.swt.api.TableItem;
import org.eclipse.reddeer.swt.condition.ShellIsAvailable;
import org.eclipse.reddeer.swt.impl.button.OkButton;
import org.eclipse.reddeer.swt.impl.button.PushButton;
import org.eclipse.reddeer.swt.impl.button.RadioButton;
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

	public EnvironmentTab() {
		super("Environment");
	}

	/**
	 * Return the list of all environment variables
	 *
	 * @return list of items
	 */
	public List<TableItem> getVariables() {
		return new DefaultTable().getItems();
	}

	/**
	 * Add new environment variable
	 *
	 * @param Name  variable name
	 * @param Value variable value
	 */
	public void add(String name, String nalue) {
		new PushButton("Add...").click();
		new WaitUntil(new ShellIsAvailable(ADD_SHELL_TITLE));
		new LabeledText("Name:").setText(name);
		new LabeledText("Value:").setText(nalue);
		new OkButton().click();
	}

	/**
	 * Select environment variable by name
	 *
	 * @param selectVariable variable name for select
	 */
	public void select(String selectVariable) {
		new PushButton("Select...").click();
		new WaitUntil(new ShellIsAvailable(SELECT_SHELL_TITLE));
		new DefaultTableItem(selectVariable).setChecked(true);
		new OkButton().click();
	}

	/**
	 * Select environment variable by id
	 *
	 * @param id variable index
	 */
	public void select(int id) {
		new PushButton("Select...").click();
		new WaitUntil(new ShellIsAvailable(SELECT_SHELL_TITLE));
		new DefaultTable().getItem(id).setChecked(true);
		new OkButton().click();
	}

	/**
	 * Select/deselect all environment variables in select button list
	 *
	 * @param selectAllBool true if need to select all variables, false for deselect
	 */
	public void select(boolean selectAllBool) {
		new PushButton("Select...").click();
		new WaitUntil(new ShellIsAvailable(SELECT_SHELL_TITLE));
		if (selectAllBool) {
			new PushButton("Select All").click();
		} else {
			new PushButton("Deselect All").click();
		}
		new OkButton().click();
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
