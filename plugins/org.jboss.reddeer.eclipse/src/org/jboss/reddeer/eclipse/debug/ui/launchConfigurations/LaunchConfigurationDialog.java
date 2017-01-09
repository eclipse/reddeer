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
package org.jboss.reddeer.eclipse.debug.ui.launchConfigurations;

import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Tree;
import org.jboss.reddeer.common.condition.WaitCondition;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.common.wait.TimePeriod;
import org.jboss.reddeer.common.wait.WaitUntil;
import org.jboss.reddeer.common.wait.WaitWhile;
import org.jboss.reddeer.core.condition.ShellWithTextIsAvailable;
import org.jboss.reddeer.core.condition.WidgetIsFound;
import org.jboss.reddeer.core.lookup.WidgetLookup;
import org.jboss.reddeer.core.matcher.WithTextMatcher;
import org.jboss.reddeer.swt.api.Button;
import org.jboss.reddeer.swt.api.Menu;
import org.jboss.reddeer.swt.api.TreeItem;
import org.jboss.reddeer.swt.impl.button.PushButton;
import org.jboss.reddeer.swt.impl.button.YesButton;
import org.jboss.reddeer.swt.impl.menu.ContextMenu;
import org.jboss.reddeer.swt.impl.menu.ShellMenu;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.swt.impl.tree.DefaultTreeItem;
import org.jboss.reddeer.workbench.core.condition.JobIsRunning;

/**
 * Common ancestor for both the Run Configurations dialog and Debug configurations dialog. 
 * 
 * @author Lucia Jelinkova
 *
 */
public abstract class LaunchConfigurationDialog {

	private static final Logger log = Logger.getLogger(LaunchConfigurationDialog.class);

	/**
	 * Return the title of the dialog. 
	 * 
	 * @return Title of the dialog
	 */
	public abstract String getTitle();

	/**
	 * Gets the menu item name.
	 *
	 * @return the menu item name
	 */
	protected abstract String getMenuItemName();

	/**
	 * Open the dialog using top menu.
	 */
	public void open() {
		log.info("Open launch configuration dialog");

		Menu menu = new ShellMenu("Run", getMenuItemName());
		menu.select();

		new DefaultShell(getTitle());
	}

	/**
	 * Select the launch configuration.
	 *
	 * @param configuration the configuration
	 */
	public void select(LaunchConfiguration configuration) {
		log.info("Select launch configuration " + configuration.getType());
		TreeItem t = new DefaultTreeItem(configuration.getType());
		t.select();
	}
	
	/**
	 * Select the launch configuration with the specified name.
	 *
	 * @param configuration the configuration
	 * @param name the name
	 */
	public void select(LaunchConfiguration configuration, String name) {
		log.info("Select launch configuration " + configuration.getType() + " with name " + name);
		TreeItem t = new DefaultTreeItem(configuration.getType(), name);
		t.select();

		new WaitUntil(new WidgetIsFound(org.eclipse.swt.custom.CLabel.class, new WithTextMatcher(name)), TimePeriod.NORMAL, false);
	}

	/**
	 * Create new configuration with default name.
	 *
	 * @param configuration the configuration
	 */
	public void create(LaunchConfiguration configuration){
		log.info("Create new launch configuration " + configuration.getType());
		create(configuration, null);
	}

	/**
	 * Create new configuration with specified name.
	 *
	 * @param configuration the configuration
	 * @param name the name
	 */
	public void create(LaunchConfiguration configuration, String name) {
		log.info("Create new launch configuration " + configuration.getType() + " with name " + name);
		final TreeItem t = new DefaultTreeItem(configuration.getType());
		t.select();

		new WaitUntil(new TreeIsSelectedAndHasFocus(t));

		new ContextMenu("New").select();
		if (name != null) {
			configuration.setName(name);
			configuration.apply();
		}
	}

	/**
	 * Delete the configuration with specified name.
	 *
	 * @param configuration the configuration
	 * @param name the name
	 */
	public void delete(LaunchConfiguration configuration, String name){
		log.info("Delete launch configuration " + configuration.getType() + " with name " + name);
		TreeItem t = new DefaultTreeItem(configuration.getType(), name);
		t.select();

		new ContextMenu("Delete").select();
		new YesButton().click();
	}
	
	/**
	 * Run the selected run configuration.
	 */
	public void run(){
		log.info("Run the launch configuration");

		String shellText = new DefaultShell().getText();
		Button button = new PushButton("Run");
		button.click();

		new WaitWhile(new ShellWithTextIsAvailable(shellText), TimePeriod.VERY_LONG);
		new WaitWhile(new JobIsRunning(), TimePeriod.VERY_LONG);			
	}

	/**
	 * Close the dialog.
	 */
	public void close(){
		log.info("Close the launch configuration dialog");

		String shellText = new DefaultShell().getText();
		Button button = new PushButton("Close");
		button.click();

		new WaitWhile(new ShellWithTextIsAvailable(shellText));
		new WaitWhile(new JobIsRunning());
	}
	
	private class TreeIsSelectedAndHasFocus implements WaitCondition{

		private TreeItem item;
		
		public TreeIsSelectedAndHasFocus(TreeItem item) {
			this.item = item;
		}
		
		@Override
		public boolean test() {
			Control focusControl = WidgetLookup.getInstance().getFocusControl();
			if (!(focusControl instanceof Tree)){
				return false;
			}
			return item.isSelected();
		}

		@Override
		public String description() {
			return "Tree has focus and TreeItem "+item.getText()+" is selected";
		}

		@Override
		public String errorMessage() {
			return "Tree does not has focus or TreeItem "+item.getText()+" is not selected";
		}
		
	}
}
