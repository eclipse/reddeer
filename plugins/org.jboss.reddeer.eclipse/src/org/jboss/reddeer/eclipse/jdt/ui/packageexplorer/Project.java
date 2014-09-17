package org.jboss.reddeer.eclipse.jdt.ui.packageexplorer;

import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.eclipse.exception.EclipseLayerException;
import org.jboss.reddeer.eclipse.utils.DeleteUtils;
import org.jboss.reddeer.jface.exception.JFaceLayerException;
import org.jboss.reddeer.jface.viewer.handler.TreeViewerHandler;
import org.jboss.reddeer.swt.api.Shell;
import org.jboss.reddeer.swt.api.TreeItem;
import org.jboss.reddeer.swt.condition.JobIsRunning;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.swt.impl.button.CheckBox;
import org.jboss.reddeer.swt.impl.button.PushButton;
import org.jboss.reddeer.swt.impl.menu.ContextMenu;
import org.jboss.reddeer.swt.impl.menu.ShellMenu;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.swt.wait.TimePeriod;
import org.jboss.reddeer.swt.wait.WaitWhile;

/**
 * Represents a project on {@link PackageExplorer}.
 * 
 * @author Vlado Pakan
 * 
 */
public class Project {
	protected final Logger log = Logger.getLogger(Project.class);

	private TreeItem treeItem;
	
	private TreeViewerHandler treeViewerHandler = TreeViewerHandler.getInstance();

	/**
	 * Creates a project represented by specified {@link TreeItem}.
	 * 
	 * @param treeItem encapsulated tree item
	 */
	public Project(TreeItem treeItem) {
		this.treeItem = treeItem;
	}

	/**
	 * Deletes project from Project Explorer.
	 * 
	 * @param deleteFromFileSystem whether project should be deleted from file 
	 * system or not
	 */
	public void delete(boolean deleteFromFileSystem) {
		select();
		log.debug("Delete project " + getName() + " via Explorer");
		new ContextMenu("Refresh").select();
		new WaitWhile(new JobIsRunning(), TimePeriod.VERY_LONG);
		// delete via context menu
		new ContextMenu("Delete").select();
		Shell sDeleteResources = handleDeleteResourcesShell(deleteFromFileSystem);
		// delete via workbench menu
		if (sDeleteResources == null && treeItem != null && !treeItem.isDisposed()){
			log.debug("Delete project " + getName() + " via Workbench menu");
			treeItem.select();
			new ShellMenu ("Edit", "Delete").select();
			sDeleteResources = handleDeleteResourcesShell(deleteFromFileSystem);
		}	
		if (sDeleteResources != null){
			String deleteShellText = sDeleteResources.getText();
			new PushButton("OK").click();
			DeleteUtils.handleDeletion(deleteShellText);
		}
		else{
			throw new EclipseLayerException("Unable to delete project " + getName() + " via UI calls");			
		}
	}

	/**
	 * Selects the project.
	 */
	public void select() {
		treeItem.select();
	}

	/**
	 * Gets name of the project without decorators.
	 * 
	 * @return name of the project without decorators
	 */
	public String getName() {
		return treeViewerHandler.getNonStyledText(treeItem);
	}

	/**
	 * Gets decorated parts of project labels. Such parts could be for
	 * example remote and branch on a git project or tracking decorator ">"
	 * before project name in a label.
	 * 
	 * @return String[] of decorated texts on a whole project label
	 */
	public String[] getDecoratedParts() {
		return treeViewerHandler.getStyledTexts(treeItem);
	}

	/**
	 * Gets encapsulated {@link TreeItem} representing the project.
	 * 
	 * @return encapsulated tree item
	 */
	public TreeItem getTreeItem() {
		return treeItem;
	}

	/**
	 * Finds out whether project contain item specified by the path or not.
	 * 
	 * @param path path to the tree item
	 * @return true if project contains tree item specified by the path, 
	 * 	false otherwise
	 */
	public boolean containsItem(String... path) {
		boolean result = false;
		try {
			ProjectItem item = getProjectItem(path);
			result = item != null;
		} catch (EclipseLayerException jfaceException) {
			result = false;
		}
		return result;
	}

	/**
	 * Gets {@link ProjectItem} specified by path. Method go through whole project 
	 * hierarchy and on each layer at first try to find item specified by part of the
	 * path as it is (whole text). If there is no item with whole text represented by 
	 * the part of the path, then item is looked up by non-decorated text representing
	 * this item. If there are more than two items in this step containing same non-styled
	 * text, then EclipseLayerException is thrown. 
	 * 
	 * 
	 * @param path path to the tree item
	 * @return tree item specified by the path
	 */
	public ProjectItem getProjectItem(String... path) {
		TreeItem item = treeItem;
		for (int i = 0; i < path.length; i++) {
			String pathSegment = path[i];
			try {
				item = item.getItem(pathSegment);
			} catch (SWTLayerException ex) {
				// there is no item with specific path segment, time to use name without decorators
				try {
					item = treeViewerHandler.getTreeItem(item, pathSegment);
				} catch (JFaceLayerException exception) {
					// non existing item
					throw new EclipseLayerException("Cannot get project item specified by path."
							+ "Project item either does not exist or solution is ambiguous because "
							+ "of existence of more items on the path with same name without decorators");
				}
			} 
		}
		
		return new ProjectItem(item, this, path);
	}

	/**
	 * Finds out whether the project is selected or not.
	 * 
	 * @return true if project is selected, false otherwise
	 */
	public boolean isSelected() {
		return treeItem.isSelected();
	}

	/**
	 * Gets whole text of the project displayed in Project Explorer
	 * as it is (decorators are included).
	 * 
	 * @return whole label of the project
	 */
	public String getText() {
		return treeItem.getText();
	}
	/**
	 * Handles waiting for Delete Resources shell
	 * Toggle check box Delete from file system based on deleteFromFileSystem parameter
	 * @param deleteFromFileSystem
	 * @return {@link Shell} if Delete Resources shell is available or null
	 */
	private Shell handleDeleteResourcesShell (boolean deleteFromFileSystem) {
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
