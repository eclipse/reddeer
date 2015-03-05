package org.jboss.reddeer.eclipse.core.resources;

import java.util.List;

import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.common.wait.TimePeriod;
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

/**
 * Abstract Project used as parent of specific projects (e.g. Java, Maven, General...).
 * 
 * @author mlabuda@redhat.com
 *
 */
public abstract class AbstractProject extends ExplorerItem {

	protected final Logger log = Logger.getLogger(Project.class);
	
	/**
	 * Creates {@link AbstractProject}
	 * @param item
	 */
	public AbstractProject(TreeItem item) {
		super(item);
		if (!natureMatches(treeViewerHandler.getNonStyledText(item), getNatureIds())) {
			throw new EclipseLayerException("Project nature IDs of chosen project does not "
					+ "match required project nature IDs");
		}
	}
	
	/**
	 * Gets nature ids of specific project type.
	 * 
	 * @return nature ids of specific project type
	 */
	
	abstract public String[] getNatureIds();
	
	private boolean natureMatches(String projectName, String[] requiredNatureIds) {
		List<String> projectNatureIds = org.jboss.reddeer.direct.project.Project.getProjectNatureIds(projectName);
		if (requiredNatureIds != null) {
			for (String requiredNatureId: requiredNatureIds) {
				if (!projectNatureIds.contains(requiredNatureId)) {
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * Deletes project from Project Explorer.
	 * 
	 * @param deleteFromFileSystem
	 *            whether project should be deleted from file system or not
	 */
	public void delete(boolean deleteFromFileSystem) {
		// no need to activate, it is activated in refresh() method 
		refresh();

		log.debug("Delete project '" + getName() + "' via Explorer");

		// delete via context menu
		select();
		new ContextMenu("Delete").select();
		Shell sDeleteResources = handleDeleteResourcesShell(deleteFromFileSystem);
		// delete via workbench menu
		if (sDeleteResources == null && treeItem != null
				&& !treeItem.isDisposed()) {
			log.debug("Delete project '" + getName() + "' via Workbench menu");
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
