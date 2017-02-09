/******************************************************************************* 
 * Copyright (c) 2017 Red Hat, Inc. 
 * Distributed under license by Red Hat, Inc. All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 ******************************************************************************/ 
package org.jboss.reddeer.eclipse.core.resources;

import static org.jboss.reddeer.common.wait.WaitProvider.waitUntil;
import static org.jboss.reddeer.common.wait.WaitProvider.waitWhile;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.hamcrest.Matcher;
import org.jboss.reddeer.common.adaptable.RedDeerAdaptable;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.common.matcher.RegexMatcher;
import org.jboss.reddeer.common.wait.GroupWait;
import org.jboss.reddeer.common.wait.TimePeriod;
import org.jboss.reddeer.common.wait.WaitWhile;
import org.jboss.reddeer.core.condition.WidgetIsFound;
import org.jboss.reddeer.core.matcher.WithMnemonicTextMatcher;
import org.jboss.reddeer.core.matcher.WithStyleMatcher;
import org.jboss.reddeer.core.matcher.WithTextMatcher;
import org.jboss.reddeer.direct.preferences.Preferences;
import org.jboss.reddeer.eclipse.exception.EclipseLayerException;
import org.jboss.reddeer.eclipse.jdt.ui.junit.JUnitHasFinished;
import org.jboss.reddeer.eclipse.utils.DeleteUtils;
import org.jboss.reddeer.jface.viewer.handler.TreeViewerHandler;
import org.jboss.reddeer.swt.api.Shell;
import org.jboss.reddeer.swt.api.TreeItem;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.swt.impl.button.CheckBox;
import org.jboss.reddeer.swt.impl.button.OkButton;
import org.jboss.reddeer.swt.impl.menu.ContextMenu;
import org.jboss.reddeer.swt.impl.menu.ShellMenu;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.workbench.core.condition.JobIsRunning;
import org.jboss.reddeer.workbench.handler.WorkbenchPartHandler;

public abstract class AbstractResource implements Resource, RedDeerAdaptable<Resource> {

	protected Logger logger = new Logger(AbstractResource.class);

	protected TreeItem treeItem;

	protected TreeViewerHandler treeViewerHandler = TreeViewerHandler.getInstance();

	/**
	 * Creates an instance of AbstractResource.
	 * 
	 * @param treeItem tree item to encapsulate
	 */
	public AbstractResource(TreeItem treeItem) {
		if (treeItem == null) {
			throw new IllegalArgumentException("Tree item of the resource cannot be null.");
		}
		this.treeItem = treeItem;
	}

	@Override
	public TreeItem getTreeItem() {
		return treeItem;
	}

	@Override
	public void open() {
		select();
		treeItem.doubleClick();
		new WaitWhile(new JobIsRunning());
	}

	@Override
	public void refresh() {
		select();
		new ContextMenu("Refresh").select();
		new WaitWhile(new JobIsRunning(), TimePeriod.LONG);
	}

	@Override
	public void expand() {
		treeItem.expand();
	}

	@Override
	public void collapse() {
		treeItem.collapse();
	}

	@Override
	public boolean isExpanded() {
		return treeItem.isExpanded();
	}

	@Override
	public String getText() {
		return treeItem.getText();
	}

	@Override
	public void select() {
		treeItem.select();
	}
	
	@Override
	public boolean isSelected() {
		return treeItem.isSelected();
	}

	@Override
	public boolean containsResource(String... path) {
		activateWrappingView();
		try {
			getResource(path);
			return true;
		} catch (EclipseLayerException jfaceException) {
			return false;
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public void runAs(String launcher) {
		select();

		Matcher<String> launcherMatcher = new WithMnemonicTextMatcher(new RegexMatcher("[0-9]* " + launcher));
		new ContextMenu(new WithMnemonicTextMatcher("Run As"), new WithMnemonicTextMatcher(launcherMatcher)).select();
	}
	
	@Override
	public void runAsJUnitTest() {
		runAsJUnitTest(TimePeriod.LONG);
	}

	@Override
	public void runAsJUnitTest(TimePeriod timeout) {
		// turn off activating console view
		String consoleOpenOnErr = Preferences.get("org.eclipse.debug.ui", "DEBUG.consoleOpenOnErr");
		String consoleOpenOnOut = Preferences.get("org.eclipse.debug.ui", "DEBUG.consoleOpenOnOut");
		Preferences.set("org.eclipse.debug.ui", "DEBUG.consoleOpenOnErr", "false");
		Preferences.set("org.eclipse.debug.ui", "DEBUG.consoleOpenOnOut", "false");

		runAs("JUnit Test");
		new GroupWait(timeout, waitWhile(new JUnitHasFinished()),
				waitUntil(new JUnitHasFinished()));

		// set the settings back
		Preferences.set("org.eclipse.debug.ui", "DEBUG.consoleOpenOnErr", consoleOpenOnErr);
		Preferences.set("org.eclipse.debug.ui", "DEBUG.consoleOpenOnOut", consoleOpenOnOut);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public void debugAs(String launcher) {
		select();

		Matcher<String> launcherMatcher = new WithMnemonicTextMatcher(new RegexMatcher("[0-9]* " + launcher));
		new ContextMenu(new WithMnemonicTextMatcher("Debug As"), new WithMnemonicTextMatcher(launcherMatcher)).select();
	}
	
	@Override
	public void delete() {
		deleteResource(true);
	}
		
	@Override
	public Object[] getAdapterConstructorArguments() {
		return new Object[] {treeItem};
	}
	
	@Override
	public Class<?>[] getAdapterConstructorClasses() {
		return new Class<?>[] {TreeItem.class};
	}
	
	protected void deleteResource(boolean deleteFromFileSystem) {
		String parentTreeItemText = treeItem.getPath()[0];
		if (org.jboss.reddeer.direct.project.Project.isProject(parentTreeItemText) &&
				org.jboss.reddeer.direct.project.Project.isOpen(parentTreeItemText)) {
			refresh();
		}
		logger.debug("Delete resource'" + getName() + "' via Explorer");

		// delete via context menu
		select();
		new ContextMenu("Delete").select();
		Shell sDeleteResources = handleDeleteResourcesShell(deleteFromFileSystem);
		// delete via workbench menu
		if (sDeleteResources == null && treeItem != null
				&& !treeItem.isDisposed()) {
			logger.debug("Delete resource '" + getName() + "' via Workbench menu");
			treeItem.select();
			new ShellMenu("Edit", "Delete").select();
			sDeleteResources = handleDeleteResourcesShell(deleteFromFileSystem);
		}
		if (sDeleteResources != null) {
			new OkButton().click();
			DeleteUtils.handleDeletion(sDeleteResources, TimePeriod.VERY_LONG);
		} else {
			throw new EclipseLayerException("Unable to delete project "
					+ getName() + " via UI calls");
		}
	}
	
	/**
	 * Handles waiting for Delete Resources shell. If there is a check box to
	 * toggle deletion from file system, process deleteFromFileSystem parameter
	 * and check the check box according to it.
	 * 
	 * @param deleteFromFileSystem
	 *            if there is an option to toggle deletion of the resource from
	 *            file system, true delete it from file system, false will keep
	 *            the resource
	 * @return {@link Shell} if Delete Resources shell is available or null
	 */
	protected Shell handleDeleteResourcesShell(boolean deleteFromFileSystem) {
		DefaultShell sDeleteResources = null;
		try {
			sDeleteResources = new DefaultShell(new WithTextMatcher(new RegexMatcher("Delete.*")));
			if (new WidgetIsFound(Button.class, sDeleteResources.getControl(), 0, new WithStyleMatcher(SWT.CHECK)).test()) {
				new CheckBox().toggle(deleteFromFileSystem);
			}
		} catch (SWTLayerException swtle) {
			sDeleteResources = null;
		}
		return sDeleteResources;
	}

	@Override
	public String[] getDecoratedParts() {
		// no need to activate when retrieving text
		return treeViewerHandler.getStyledTexts(treeItem);
	}

	@Override
	public String getName() {
		// no need to activate when retrieving text
		return treeViewerHandler.getNonStyledText(treeItem);
	}

	@Override
	public String getTitleOfWrappingView() {
		return WorkbenchPartHandler.getInstance().getTitleOfWorkbenchPartWithWidget(treeItem.getSWTWidget());
	}

	@Override
	public void activateWrappingView() {
		WorkbenchPartHandler.getInstance().activateWorkbenchPartWithWidget(treeItem.getSWTWidget());
	}
	
	
}
