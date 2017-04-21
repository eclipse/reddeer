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
package org.eclipse.reddeer.eclipse.jdt.ui.preferences;

import java.util.LinkedList;
import java.util.List;

import org.hamcrest.Matcher;
import org.eclipse.reddeer.common.logging.Logger;
import org.eclipse.reddeer.common.wait.WaitWhile;
import org.eclipse.reddeer.core.matcher.TreeItemTextMatcher;
import org.eclipse.reddeer.eclipse.ui.dialogs.PropertyPage;
import org.eclipse.reddeer.swt.api.Shell;
import org.eclipse.reddeer.swt.api.Tree;
import org.eclipse.reddeer.swt.api.TreeItem;
import org.eclipse.reddeer.swt.condition.ShellIsAvailable;
import org.eclipse.reddeer.swt.impl.button.CancelButton;
import org.eclipse.reddeer.swt.impl.button.OkButton;
import org.eclipse.reddeer.swt.impl.button.PushButton;
import org.eclipse.reddeer.swt.impl.button.YesButton;
import org.eclipse.reddeer.swt.impl.shell.DefaultShell;
import org.eclipse.reddeer.swt.impl.tab.DefaultTabItem;
import org.eclipse.reddeer.swt.impl.table.DefaultTableItem;
import org.eclipse.reddeer.swt.impl.tree.DefaultTree;
import org.eclipse.reddeer.swt.impl.tree.DefaultTreeItem;
import org.eclipse.reddeer.workbench.core.condition.JobIsRunning;
import org.eclipse.reddeer.workbench.ui.dialogs.WorkbenchPreferenceDialog;

/**
 * "Java Build Path" property page. 
 * 
 * @author Vlado Pakan
 *
 */
public class BuildPathsPropertyPage extends PropertyPage {

	public static final String NAME = "Java Build Path"; 

	private static final Logger log = Logger.getLogger(BuildPathsPropertyPage.class);
	/**
	 * Constructs a new Build Path property page.
	 */
	public BuildPathsPropertyPage() {
		super(NAME);
	}
	
	/**
	 * Activates Source tab.
	 */
	public void activateSourceTab(){
		new DefaultTabItem("Source").activate();
		new WaitWhile(new JobIsRunning());
	}
	
	/**
	 * Activates Projects tab.
	 */
	public void activateProjectsTab(){
		new DefaultTabItem("Projects").activate();
		new WaitWhile(new JobIsRunning());
	}
	
	/**
	 * Activates Libraries tab.
	 */
	public void activateLibrariesTab(){
		new DefaultTabItem("Libraries").activate();
		new WaitWhile(new JobIsRunning());
	}
	
	/**
	 * Activates Order and Export tab.
	 */
	public void activateOrderAndExportTab(){
		new DefaultTabItem("Order and Export").activate();
		new WaitWhile(new JobIsRunning());
	}
	
	/**
	 * Adds new variable to Build Path Libraries.
	 *
	 * @param name the name
	 * @param value the value
	 * @param overwriteIfExists the overwrite if exists
	 * @return added variable label
	 */
	public String addVariable(String name , String value , boolean overwriteIfExists){
		Shell propertiesDialogShell = new DefaultShell();
		log.info("Adding variable: " + name + "=" + value);
		activateLibrariesTab();	
		new PushButton("Add Variable...").click();
		new DefaultShell("New Variable Classpath Entry");
		new PushButton("Configure Variables...").click();
		WorkbenchPreferenceDialog preferencesDialog = new WorkbenchPreferenceDialog();
		preferencesDialog.open();
		String result = new ClasspathVariablesPreferencePage().addVariable(name, value, overwriteIfExists);
		preferencesDialog.ok();
		new DefaultShell("New Variable Classpath Entry");
		new OkButton().click();
		new WaitWhile(new ShellIsAvailable("New Variable Classpath Entry"));
		new WaitWhile(new JobIsRunning());
		propertiesDialogShell.setFocus();
		new PushButton("Apply").click();
		new WaitWhile(new JobIsRunning());
		return result;
	}
	
	/**
	 * Removes variable from Build Path Libraries.
	 *
	 * @param label the label
	 * @param removeGlobally the remove globally
	 */
	public void removeVariable(String label, boolean removeGlobally){
		log.info("Removing variable: " + label);
		Shell propertiesDialogShell = new DefaultShell();
		selectLibrary(new TreeItemTextMatcher(label));
		new PushButton("Remove").click();
		if (removeGlobally) {
			new PushButton("Add Variable...").click();
			new DefaultShell("New Variable Classpath Entry");
			new DefaultTableItem(label).select();
			new PushButton("Configure Variables...").click();
			new WorkbenchPreferenceDialog().open();
			new ClasspathVariablesPreferencePage().removeVariable(label);
			new OkButton().click();
			new DefaultShell("Classpath Variables Changed");
			new YesButton().click();
			new WaitWhile(new ShellIsAvailable("Classpath Variables Changed"));
			new DefaultShell("New Variable Classpath Entry");
			new CancelButton().click();
			new WaitWhile(new ShellIsAvailable("New Variable Classpath Entry"));
			new WaitWhile(new JobIsRunning());
		}
		propertiesDialogShell.setFocus();
		new PushButton("Apply").click();
		new WaitWhile(new JobIsRunning());
	}
	
	/**
	 * Selects library matching matcher .
	 *
	 * @param matcher the matcher
	 */
	public void selectLibrary (Matcher<org.eclipse.swt.widgets.TreeItem> matcher){
		new DefaultTreeItem(getLibraryTree(),matcher).select();
	}
	
	/**
	 * Returns list with defined libraries on build path.
	 *
	 * @return the libraries
	 */
	public List<String> getLibraries (){
		LinkedList<String> libraries = new LinkedList<String>();
		for (TreeItem ti :  getLibraryTree().getItems()){
			libraries.addLast(ti.getText());
		}
		return libraries;
	}
	/**
	 * Returns Libraries Tree 
	 * @return
	 */
	private Tree getLibraryTree () {
		activateLibrariesTab();
		return new DefaultTree(1);
	}
}
