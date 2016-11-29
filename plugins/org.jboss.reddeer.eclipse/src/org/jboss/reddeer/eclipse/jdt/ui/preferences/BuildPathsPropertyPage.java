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
package org.jboss.reddeer.eclipse.jdt.ui.preferences;

import java.util.LinkedList;
import java.util.List;

import org.hamcrest.Matcher;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.common.wait.WaitWhile;
import org.jboss.reddeer.core.condition.ShellWithTextIsAvailable;
import org.jboss.reddeer.core.matcher.TreeItemTextMatcher;
import org.jboss.reddeer.eclipse.ui.dialogs.PropertyPage;
import org.jboss.reddeer.swt.api.Shell;
import org.jboss.reddeer.swt.api.Tree;
import org.jboss.reddeer.swt.api.TreeItem;
import org.jboss.reddeer.swt.impl.button.CancelButton;
import org.jboss.reddeer.swt.impl.button.OkButton;
import org.jboss.reddeer.swt.impl.button.PushButton;
import org.jboss.reddeer.swt.impl.button.YesButton;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.swt.impl.tab.DefaultTabItem;
import org.jboss.reddeer.swt.impl.table.DefaultTableItem;
import org.jboss.reddeer.swt.impl.tree.DefaultTree;
import org.jboss.reddeer.swt.impl.tree.DefaultTreeItem;
import org.jboss.reddeer.workbench.core.condition.JobIsRunning;
import org.jboss.reddeer.workbench.ui.dialogs.FilteredPreferenceDialog;

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
		FilteredPreferenceDialog preferencesDialog = new FilteredPreferenceDialog();
		preferencesDialog.open();
		String result = new ClasspathVariablesPreferencePage().addVariable(name, value, overwriteIfExists);
		preferencesDialog.ok();
		new DefaultShell("New Variable Classpath Entry");
		new OkButton().click();
		new WaitWhile(new ShellWithTextIsAvailable("New Variable Classpath Entry"));
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
			new FilteredPreferenceDialog().open();
			new ClasspathVariablesPreferencePage().removeVariable(label);
			new OkButton().click();
			new DefaultShell("Classpath Variables Changed");
			new YesButton().click();
			new WaitWhile(new ShellWithTextIsAvailable("Classpath Variables Changed"));
			new DefaultShell("New Variable Classpath Entry");
			new CancelButton().click();
			new WaitWhile(new ShellWithTextIsAvailable("New Variable Classpath Entry"));
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
