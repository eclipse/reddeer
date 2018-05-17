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
package org.eclipse.reddeer.eclipse.jdt.ui.preferences;

import java.util.LinkedList;
import java.util.List;

import org.hamcrest.Matcher;
import org.eclipse.reddeer.common.logging.Logger;
import org.eclipse.reddeer.common.wait.WaitWhile;
import org.eclipse.reddeer.core.condition.WidgetIsFound;
import org.eclipse.reddeer.core.matcher.TreeItemTextMatcher;
import org.eclipse.reddeer.core.matcher.WithMnemonicTextMatcher;
import org.eclipse.reddeer.core.reference.ReferencedComposite;
import org.eclipse.reddeer.eclipse.ui.dialogs.PropertyPage;
import org.eclipse.reddeer.swt.api.Button;
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
	public BuildPathsPropertyPage(ReferencedComposite referencedComposite) {
		super(referencedComposite, NAME);
	}
	
	/**
	 * Activates Source tab.
	 */
	public BuildPathsPropertyPage activateSourceTab(){
		new DefaultTabItem(this, "Source").activate();
		new WaitWhile(new JobIsRunning());
		return this;
	}
	
	/**
	 * Activates Projects tab.
	 */
	public BuildPathsPropertyPage activateProjectsTab(){
		new DefaultTabItem(this, "Projects").activate();
		new WaitWhile(new JobIsRunning());
		return this;
	}
	
	/**
	 * Activates Libraries tab.
	 */
	public BuildPathsPropertyPage activateLibrariesTab(){
		new DefaultTabItem(this, "Libraries").activate();
		new WaitWhile(new JobIsRunning());
		return this;
	}
	
	/**
	 * Activates Order and Export tab.
	 */
	public BuildPathsPropertyPage activateOrderAndExportTab(){
		new DefaultTabItem(this, "Order and Export").activate();
		new WaitWhile(new JobIsRunning());
		return this;
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
		log.info("Adding variable: " + name + "=" + value);
		activateLibrariesTab();	
		new PushButton(this, "Add Variable...").click();
		Shell variableEntryShell = new DefaultShell("New Variable Classpath Entry");
		new PushButton(variableEntryShell, "Configure Variables...").click();
		WorkbenchPreferenceDialog preferencesDialog = new WorkbenchPreferenceDialog();
		preferencesDialog.open();
		String result = new ClasspathVariablesPreferencePage(preferencesDialog).addVariable(name, value, overwriteIfExists);
		preferencesDialog.ok();
		new OkButton(variableEntryShell).click();
		new WaitWhile(new ShellIsAvailable(variableEntryShell));
		new WaitWhile(new JobIsRunning());
		new PushButton(this, "Apply").click();
		new WaitWhile(new JobIsRunning());
		return result;
	}
	
	/**
	 * Removes variable from Build Path Libraries.
	 *
	 * @param label the label
	 * @param removeGlobally the remove globally
	 */
	public BuildPathsPropertyPage removeVariable(String label, boolean removeGlobally){
		log.info("Removing variable: " + label);
		selectLibrary(new TreeItemTextMatcher(label));
		new PushButton(this, "Remove").click();
		if (removeGlobally) {
			new PushButton(this, "Add Variable...").click();
			Shell variableEntryShell = new DefaultShell("New Variable Classpath Entry");
			new DefaultTableItem(variableEntryShell, label).select();
			new PushButton(variableEntryShell, "Configure Variables...").click();
			WorkbenchPreferenceDialog workbenchDialog = new WorkbenchPreferenceDialog();
			workbenchDialog.open();
			new ClasspathVariablesPreferencePage(workbenchDialog).removeVariable(label);
			
			WidgetIsFound applyAndCloseButton = new WidgetIsFound(org.eclipse.swt.widgets.Button.class,
					new WithMnemonicTextMatcher("Apply and Close"));
			
			
			Button button;
			if(applyAndCloseButton.test()){
				button = new PushButton("Apply and Close"); //oxygen changed button text
			} else {
				button = new OkButton();	
			}
			button.click();
			
			Shell varaiblesChanged = new DefaultShell("Classpath Variables Changed");
			new YesButton(varaiblesChanged).click();
			new WaitWhile(new ShellIsAvailable(varaiblesChanged));
			new CancelButton(variableEntryShell).click();
			new WaitWhile(new ShellIsAvailable(variableEntryShell));
			new WaitWhile(new JobIsRunning());
		}
		new PushButton(this, "Apply").click();
		new WaitWhile(new JobIsRunning());
		return this;
	}
	
	/**
	 * Selects library matching matcher .
	 *
	 * @param matcher the matcher
	 */
	public BuildPathsPropertyPage selectLibrary (Matcher<org.eclipse.swt.widgets.TreeItem> matcher){
		new DefaultTreeItem(getLibraryTree(),matcher).select();
		return this;
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
		return new DefaultTree(this, 1);
	}
}
