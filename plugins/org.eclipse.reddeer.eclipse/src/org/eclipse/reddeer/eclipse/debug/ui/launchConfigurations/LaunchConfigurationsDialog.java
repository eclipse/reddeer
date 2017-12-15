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
package org.eclipse.reddeer.eclipse.debug.ui.launchConfigurations;

import org.eclipse.reddeer.common.condition.AbstractWaitCondition;
import org.eclipse.reddeer.common.logging.Logger;
import org.eclipse.reddeer.common.wait.TimePeriod;
import org.eclipse.reddeer.common.wait.WaitUntil;
import org.eclipse.reddeer.common.wait.WaitWhile;
import org.eclipse.reddeer.core.condition.WidgetIsFound;
import org.eclipse.reddeer.core.lookup.WidgetLookup;
import org.eclipse.reddeer.core.matcher.WithMnemonicTextMatcher;
import org.eclipse.reddeer.core.matcher.WithTextMatcher;
import org.eclipse.reddeer.eclipse.exception.EclipseLayerException;
import org.eclipse.reddeer.swt.api.Button;
import org.eclipse.reddeer.swt.api.MenuItem;
import org.eclipse.reddeer.swt.api.Shell;
import org.eclipse.reddeer.swt.api.TreeItem;
import org.eclipse.reddeer.swt.condition.ShellIsAvailable;
import org.eclipse.reddeer.swt.impl.button.PushButton;
import org.eclipse.reddeer.swt.impl.button.YesButton;
import org.eclipse.reddeer.swt.impl.menu.ContextMenu;
import org.eclipse.reddeer.swt.impl.menu.ContextMenuItem;
import org.eclipse.reddeer.swt.impl.menu.ShellMenuItem;
import org.eclipse.reddeer.swt.impl.shell.DefaultShell;
import org.eclipse.reddeer.swt.impl.tree.DefaultTreeItem;
import org.eclipse.reddeer.workbench.core.condition.JobIsRunning;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Tree;

/**
 * Common ancestor for both the Run Configurations dialog and Debug
 * configurations dialog.
 * 
 * @author Lucia Jelinkova
 *
 */
public abstract class LaunchConfigurationsDialog {

	private static final Logger log = Logger.getLogger(LaunchConfigurationsDialog.class);

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

		MenuItem menu = new ShellMenuItem("Run", getMenuItemName());
		menu.select();

		new DefaultShell(getTitle());
	}

	/**
	 * Select the launch configuration.
	 *
	 * @param configuration
	 *            the configuration
	 */
	public void select(LaunchConfiguration configuration) {
		log.info("Select launch configuration " + configuration.getType());
		TreeItem t = new DefaultTreeItem(configuration.getType());
		t.select();
	}

	/**
	 * Select the launch configuration with the specified name.
	 *
	 * @param configuration
	 *            the configuration
	 * @param name
	 *            the name
	 */
	public void select(LaunchConfiguration configuration, String name) {
		log.info("Select launch configuration " + configuration.getType() + " with name " + name);
		TreeItem t = new DefaultTreeItem(configuration.getType(), name);
		t.select();

		new WaitUntil(new WidgetIsFound(org.eclipse.swt.custom.CLabel.class, new WithTextMatcher(name)),
				TimePeriod.DEFAULT, false);
	}

	/**
	 * Create new configuration with default name.
	 *
	 * @param configuration
	 *            the configuration
	 */
	public void create(LaunchConfiguration configuration) {
		log.info("Create new launch configuration " + configuration.getType());
		create(configuration, null);
	}

	/**
	 * Create new configuration with specified name.
	 *
	 * @param configuration
	 *            the configuration
	 * @param name
	 *            the name
	 */
	public void create(LaunchConfiguration configuration, String name) {
		log.info("Create new launch configuration " + configuration.getType() + " with name " + name);
		final TreeItem t = new DefaultTreeItem(configuration.getType());
		t.select();

		new WaitUntil(new TreeIsSelectedAndHasFocus(t));

		//photon changed menu text to "New configuration"
		MenuItem newMenuItem = new ContextMenu().getItems().stream()
			.filter(menuItem -> menuItem.getText().equals("New") || menuItem.getText().toLowerCase().equals("new configuration")).findFirst().get();
		if(newMenuItem == null) {
			throw new EclipseLayerException("Cannot create new launch configuration");
		}
		newMenuItem.select();
		if (name != null) {
			configuration.setName(name);
			configuration.apply();
		}
	}

	/**
	 * Delete the configuration with specified name.
	 *
	 * @param configuration
	 *            the configuration
	 * @param name
	 *            the name
	 */
	public void delete(LaunchConfiguration configuration, String name) {
		log.info("Delete launch configuration " + configuration.getType() + " with name " + name);
		TreeItem t = new DefaultTreeItem(configuration.getType(), name);
		t.select();

		new ContextMenuItem("Delete").select();
		Shell deleteShell = new DefaultShell("Confirm Launch Configuration Deletion");
		WidgetIsFound deleteButton = new WidgetIsFound(org.eclipse.swt.widgets.Button.class, deleteShell.getSWTWidget(),
				new WithMnemonicTextMatcher("Delete"));

		Button button;
		if (deleteButton.test()) {
			button = new PushButton(deleteShell, "Delete"); // photon changed button text
		} else {
			button = new YesButton(deleteShell);
		}
		button.click();
		new WaitWhile(new ShellIsAvailable(deleteShell));
	}

	/**
	 * Run the selected run configuration.
	 */
	public void run() {
		log.info("Run the launch configuration");

		String shellText = new DefaultShell().getText();
		Button button = new PushButton("Run");
		button.click();

		new WaitWhile(new ShellIsAvailable(shellText), TimePeriod.VERY_LONG);
		new WaitWhile(new JobIsRunning(), TimePeriod.VERY_LONG);
	}

	/**
	 * Close the dialog.
	 */
	public void close() {
		log.info("Close the launch configuration dialog");

		String shellText = new DefaultShell().getText();
		Button button = new PushButton("Close");
		button.click();

		new WaitWhile(new ShellIsAvailable(shellText));
		new WaitWhile(new JobIsRunning());
	}

	private class TreeIsSelectedAndHasFocus extends AbstractWaitCondition {

		private TreeItem item;

		public TreeIsSelectedAndHasFocus(TreeItem item) {
			this.item = item;
		}

		@Override
		public boolean test() {
			Control focusControl = WidgetLookup.getInstance().getFocusControl();
			if (!(focusControl instanceof Tree)) {
				return false;
			}
			return item.isSelected();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.reddeer.common.condition.AbstractWaitCondition#description()
		 */
		@Override
		public String description() {
			return "Tree has focus and TreeItem " + item.getText() + " is selected";
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.eclipse.reddeer.common.condition.AbstractWaitCondition#errorMessageWhile(
		 * )
		 */
		@Override
		public String errorMessageWhile() {
			return "TreeItem '" + item.getText() + "' has focus and is selected ";
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.eclipse.reddeer.common.condition.AbstractWaitCondition#errorMessageUntil(
		 * )
		 */
		@Override
		public String errorMessageUntil() {
			return "Tree does not has focus or TreeItem '" + item.getText() + "' is not selected";
		}
	}
}
