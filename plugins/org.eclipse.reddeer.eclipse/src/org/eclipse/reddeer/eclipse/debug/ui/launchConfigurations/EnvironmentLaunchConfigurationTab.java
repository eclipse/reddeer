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

import org.eclipse.reddeer.swt.api.TableItem;
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
public class EnvironmentLaunchConfigurationTab extends LaunchConfigurationTab {

	public EnvironmentLaunchConfigurationTab() {
		super("Environment");
	}

	/**
	 * Return the list of all environment variables
	 *
	 * @return list of items
	 */
	public List<TableItem> get_variables() {
		return new DefaultTable().getItems();
	}

	/**
	 * Add new environment variable
	 *
	 * @param Name  variable name
	 * @param Value variable value
	 */
	public void add(String Name, String Value) {
		new PushButton("Add...").click();
		new LabeledText("Name:").setText(Name);
		new LabeledText("Value:").setText(Value);
		new OkButton().click();
	}

	/**
	 * Select environment variable by name
	 *
	 * @param Name variable name
	 */
	public void select(String Name) {
		new PushButton("Select...").click();
		new DefaultTableItem(Name).setChecked(true);
		new OkButton().click();
	}

	/**
	 * Select environment variable by id
	 *
	 * @param id variable index
	 */
	public void select(int id) {
		new PushButton("Select...").click();
		new DefaultTable().getItem(id).setChecked(true);
		new OkButton().click();
	}

	/**
	 * Select/deselect all environment variables in select button list
	 *
	 * @param id variable index
	 */
	public void select(boolean selectAllBool) {
		new PushButton("Select...").click();
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
	 * @param Name  new variable name
	 * @param Value new variable value
	 */
	public void edit(String Name, String Value) {
		new PushButton("Edit...").click();
		new LabeledText("Name:").setText(Name);
		new LabeledText("Value:").setText(Value);
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
	 */
	public void append() {
		new RadioButton("Append environment to native environment").click();
	}

	/**
	 * Replace native environment with specified environment
	 */
	public void replace() {
		new RadioButton("Replace native environment with specified environment").click();
	}
}
