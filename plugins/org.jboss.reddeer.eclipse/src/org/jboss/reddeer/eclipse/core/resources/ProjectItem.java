package org.jboss.reddeer.eclipse.core.resources;

import org.hamcrest.Matcher;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.common.matcher.RegexMatcher;
import org.jboss.reddeer.common.wait.TimePeriod;
import org.jboss.reddeer.common.wait.WaitUntil;
import org.jboss.reddeer.common.wait.WaitWhile;
import org.jboss.reddeer.core.condition.ShellWithTextIsActive;
import org.jboss.reddeer.core.matcher.WithMnemonicTextMatcher;
import org.jboss.reddeer.core.matcher.WithTextMatcher;
import org.jboss.reddeer.direct.preferences.Preferences;
import org.jboss.reddeer.eclipse.exception.EclipseLayerException;
import org.jboss.reddeer.eclipse.jdt.ui.junit.JUnitHasFinished;
import org.jboss.reddeer.eclipse.utils.DeleteUtils;
import org.jboss.reddeer.swt.api.Shell;
import org.jboss.reddeer.swt.api.TreeItem;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.swt.impl.button.OkButton;
import org.jboss.reddeer.swt.impl.button.PushButton;
import org.jboss.reddeer.swt.impl.menu.ContextMenu;
import org.jboss.reddeer.swt.impl.menu.ShellMenu;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.swt.impl.tree.DefaultTreeItem;

/**
 * Represents a project item of {@link Project}.
 * 
 * @author Vlado Pakan, mlabuda@redhat.com
 * 
 */
public class ProjectItem extends ExplorerItem {

	protected final Logger log = Logger.getLogger(ProjectItem.class);

	private Project project;
	private String[] path;

	/**
	 * Constructs project item with a specified tree item.
	 * 
	 * @param treeItem
	 *            item representing project item
	 */
	public ProjectItem(TreeItem treeItem) {
		super(treeItem);
		project = new Project(new DefaultTreeItem(treeItem.getPath()[0]));
		path = treeItem.getPath();
	}

	/**
	 * Opens the project item with the specified editor.
	 *
	 * @param editor the editor
	 */
	public void openWith(String editor) {
		select();
		new ContextMenu("Open With", "Other...").select();;
		new WaitUntil(new ShellWithTextIsActive("Editor Selection"));
		new DefaultTreeItem(editor).select();
		new OkButton().click();
		new WaitWhile(new ShellWithTextIsActive("Editor Selection"));
	}
	
	/**
	 * Runs project with the specified launcher.
	 * 
	 * @param launcher
	 *            Launcher
	 */
	@SuppressWarnings("unchecked")
	public void runAs(String launcher) {
		select();

		Matcher<String> launcherMatcher = new WithMnemonicTextMatcher(new RegexMatcher("[0-9]* " + launcher));
		new ContextMenu(new WithMnemonicTextMatcher("Run As"), new WithMnemonicTextMatcher(launcherMatcher)).select();
	}

	/**
	 * Runs project as JUnit Test and waits until it is finished. Before it is
	 * run settings for activating a console are turned off. At the end the
	 * settings are set back as they was.
	 */
	public void runAsJUnitTest() {
		runAsJUnitTest(TimePeriod.LONG);
	}

	/**
	 * Runs project as JUnit Test and waits until it is finished in a given
	 * timeout. Before it is run settings for activating a console are turned
	 * off. At the end the settings are set back as they was.
	 * 
	 * @param timeout
	 *            Timeout
	 */
	public void runAsJUnitTest(TimePeriod timeout) {
		// turn off activating console view
		String consoleOpenOnErr = Preferences.get("org.eclipse.debug.ui", "DEBUG.consoleOpenOnErr");
		String consoleOpenOnOut = Preferences.get("org.eclipse.debug.ui", "DEBUG.consoleOpenOnOut");
		Preferences.set("org.eclipse.debug.ui", "DEBUG.consoleOpenOnErr", "false");
		Preferences.set("org.eclipse.debug.ui", "DEBUG.consoleOpenOnOut", "false");

		runAs("JUnit Test");
		new WaitWhile(new JUnitHasFinished(), timeout);
		new WaitUntil(new JUnitHasFinished(), timeout);

		// set the settings back
		Preferences.set("org.eclipse.debug.ui", "DEBUG.consoleOpenOnErr", consoleOpenOnErr);
		Preferences.set("org.eclipse.debug.ui", "DEBUG.consoleOpenOnOut", consoleOpenOnOut);
	}
	
	/**
	 * Debugs the project item with the specified launcher.
	 * 
	 * @param launcher
	 *            Launcher
	 */
	@SuppressWarnings("unchecked")
	public void debugAs(String launcher) {
		select();

		Matcher<String> launcherMatcher = new WithMnemonicTextMatcher(new RegexMatcher("[0-9]* " + launcher));
		new ContextMenu(new WithMnemonicTextMatcher("Debug As"), new WithMnemonicTextMatcher(launcherMatcher)).select();
	}

	/**
	 * Deletes the project item. The project item is refreshed before deleting.
	 */
	public void delete() {
		// no need to activate, it is activated in refresh() method 
		refresh();

		log.debug("Delete project item '" + treeItem.getText() + "'.");

		// delete via context menu
		select();
		new ContextMenu("Delete").select();
		Shell sDeleteResources = handleDeleteResourcesShell();
		
		// delete via workbench menu
		if (sDeleteResources == null && treeItem != null
				&& !treeItem.isDisposed()) {
			log.debug("Delete project item '" + treeItem.getText() + "' via Workbench menu.");
			treeItem.select();
			new ShellMenu("Edit", "Delete").select();
			sDeleteResources = handleDeleteResourcesShell();
		}
		if (sDeleteResources != null) {
			new PushButton("OK").click();
			DeleteUtils.handleDeletion(sDeleteResources, TimePeriod.VERY_LONG);
		} else {
			throw new EclipseLayerException("Unable to delete project "
					+ getName() + " via UI calls");
		}
	}

	/**
	 * Gets project of the project item where this item belong to.
	 * 
	 * @return project of the project item
	 */
	public Project getProject() {
		return project;
	}

	
	/**
	 * Handles waiting for Delete Resources
	 * 
	 * @return {@link Shell} if Delete Resources shell is available or null
	 */
	private Shell handleDeleteResourcesShell() {
		Shell sDeleteResources = null;
		try {
			new DefaultShell(new WithTextMatcher(new RegexMatcher("Delete.*")));
			sDeleteResources = new DefaultShell();
		} catch (SWTLayerException swtle) {
			sDeleteResources = null;
		}
		return sDeleteResources;
	}
	
	/* (non-Javadoc)
	 * @see org.jboss.reddeer.eclipse.core.resources.ExplorerItem#select()
	 */
	@Override
	public void select() {
		activateWrappingView();
		String[] itemPath = new String[path.length - 1];
		System.arraycopy(path, 1, itemPath, 0, path.length - 1);
		project.getProjectItem(itemPath).treeItem.select();
	}
}
