package org.jboss.reddeer.eclipse.jdt.ui.packageexplorer;

import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.eclipse.exception.EclipseLayerException;
import org.jboss.reddeer.eclipse.utils.DeleteUtils;
import org.jboss.reddeer.swt.api.Shell;
import org.jboss.reddeer.swt.api.TreeItem;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.swt.impl.button.CheckBox;
import org.jboss.reddeer.swt.impl.button.PushButton;
import org.jboss.reddeer.swt.impl.menu.ContextMenu;
import org.jboss.reddeer.swt.impl.menu.ShellMenu;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.swt.wait.TimePeriod;

/**
 * Represents a project inside Project explorer, Package explorer or Resource
 * Navigator.
 * 
 * @author Vlado Pakan, mlabuda@redhat.com
 * 
 */
public class Project extends AbstractExplorerItem {

	protected final Logger log = Logger.getLogger(Project.class);

	/**
	 * Creates a project represented by specified {@link TreeItem}.
	 * 
	 * @param treeItem
	 *            encapsulated tree item
	 */
	public Project(TreeItem treeItem) {
		super(treeItem);
	}

	/**
	 * Deletes project from Project Explorer.
	 * 
	 * @param deleteFromFileSystem
	 *            whether project should be deleted from file system or not
	 */
	public void delete(boolean deleteFromFileSystem) {
		refresh();

		log.debug("Delete project " + getName() + " via Explorer");

		// delete via context menu
		select();
		new ContextMenu("Delete").select();
		Shell sDeleteResources = handleDeleteResourcesShell(deleteFromFileSystem);
		// delete via workbench menu
		if (sDeleteResources == null && treeItem != null
				&& !treeItem.isDisposed()) {
			log.debug("Delete project " + getName() + " via Workbench menu");
			treeItem.select();
			new ShellMenu("Edit", "Delete").select();
			sDeleteResources = handleDeleteResourcesShell(deleteFromFileSystem);
		}
		if (sDeleteResources != null) {
			new PushButton("OK").click();
			DeleteUtils.handleDeletion(sDeleteResources, TimePeriod.VERY_LONG);
		} else {
			throw new EclipseLayerException("Unable to delete project "
					+ getName() + " via UI calls");
		}
	}

	@Override
	public void select() {
		treeItem.select();
	}

	/**
	 * Handles waiting for Delete Resources shell Toggle check box Delete from
	 * file system based on deleteFromFileSystem parameter
	 * 
	 * @param deleteFromFileSystem
	 * @return {@link Shell} if Delete Resources shell is available or null
	 */
	private Shell handleDeleteResourcesShell(boolean deleteFromFileSystem) {
		Shell sDeleteResources = null;
		try {
			new DefaultShell("Delete Resources");
			new CheckBox().toggle(deleteFromFileSystem);
			sDeleteResources = new DefaultShell();
		} catch (SWTLayerException swtle) {
			sDeleteResources = null;
		}
		return sDeleteResources;
	}
}
