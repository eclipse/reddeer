package org.jboss.reddeer.eclipse.core.resources;

import java.util.ArrayList;
import java.util.List;

import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.eclipse.exception.EclipseLayerException;
import org.jboss.reddeer.jface.exception.JFaceLayerException;
import org.jboss.reddeer.jface.viewer.handler.TreeViewerHandler;
import org.jboss.reddeer.swt.api.TreeItem;
import org.jboss.reddeer.swt.condition.JobIsRunning;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.swt.impl.menu.ContextMenu;
import org.jboss.reddeer.swt.wait.TimePeriod;
import org.jboss.reddeer.swt.wait.WaitWhile;
import org.jboss.reddeer.workbench.handler.WorkbenchPartHandler;

/**
 * 
 * Class AbstractExplorerItem is common ancestor for Project, ProjectItem and
 * other items.
 * 
 * @author mlabuda@redhat.com
 * @since 0.7.0
 */
public abstract class AbstractExplorerItem {

	protected Logger logger = new Logger(AbstractExplorerItem.class);
	
	protected TreeItem treeItem;

	protected TreeViewerHandler treeViewerHandler = TreeViewerHandler
			.getInstance();

	public AbstractExplorerItem(TreeItem treeItem) {
		if (treeItem == null) {
			throw new IllegalArgumentException("Tree item cannot be null.");
		}
		this.treeItem = treeItem;
	}

	/**
	 * Activates View containing explorer item.
	 */
	public void activateWrappingView() {
		WorkbenchPartHandler.getInstance().activateWorkbenchPartWithWidget(
				treeItem);
	}

	/**
	 * Finds out whether explorer item contains item specified by the path or
	 * not.
	 * 
	 * @param path
	 *            path to the tree item
	 * @return true if explorer item contains tree item specified by the path,
	 *         false otherwise
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
	 * Gets child of the explorer item. At first the whole text of the child is
	 * checked, then only non-decorated part.
	 * 
	 * @param text
	 *            text of the project item
	 * @return project item with the specified <var>text</var>
	 */
	public ProjectItem getChild(String text) {
		String[] childPath = new String[treeItem.getPath().length + 1];
		System.arraycopy(treeItem.getPath(), 0, childPath, 0,
				treeItem.getPath().length);
		childPath[childPath.length - 1] = text;
		return getProjectItem(text);
	}

	/**
	 * Gets list of children of the project item.
	 * 
	 * @return list of children of the project item
	 */
	public List<ProjectItem> getChildren() {
		List<ProjectItem> children = new ArrayList<ProjectItem>();

		for (TreeItem item : treeItem.getItems()) {
			String name = item.getText();
			String[] childPath = new String[treeItem.getPath().length + 1];
			System.arraycopy(treeItem.getPath(), 0, childPath, 0,
					treeItem.getPath().length);
			childPath[childPath.length - 1] = name;
			children.add(new ProjectItem(item));
		}

		return children;
	}

	/**
	 * Gets decorated parts of explorer item labels. Such parts could be for
	 * example remote and branch on a git project or tracking decorator ">"
	 * before project name in a label etc.
	 * 
	 * @return String[] of decorated texts on a explorer item
	 */
	public String[] getDecoratedParts() {
		return treeViewerHandler.getStyledTexts(treeItem);
	}

	/**
	 * Gets name of the explorer item without decorators.
	 * 
	 * @return name of the explorer item without decorators
	 */
	public String getName() {
		return treeViewerHandler.getNonStyledText(treeItem);
	}

	/**
	 * Gets explorer item specified by path from current explorer item. Method 
	 * go through whole hierarchy and on each layer at first try to find item 
	 * specified by part of the path as it is (whole text). If there is no item 
	 * with whole text represented by the part of the path, then item is looked 
	 * up by non-decorated text representing this item. If there are more than 
	 * two items in this step containing same non-styled text, then 
	 * EclipseLayerException is thrown.
	 *
	 * @param path
	 *            path to the specific explorer item
	 * @return tree item specified by the path
	 */
	public ProjectItem getProjectItem(String... path) {
		TreeItem item = treeItem;
		for (int i = 0; i < path.length; i++) {
			String pathSegment = path[i];
			try {
				item = item.getItem(pathSegment);
			} catch (SWTLayerException ex) {
				// there is no item with specific path segment, time to use name
				// without decorators
				try {
					item = treeViewerHandler.getTreeItem(item, pathSegment);
				} catch (JFaceLayerException exception) {
					// non existing item
					logger.debug("Obtaining direct children on the current level");
					List<TreeItem> items = item.getItems();
					logger.debug("Item \"" + pathSegment + "\" was not found. Available items on the current level:");
					for (TreeItem treeItem: items) {
						logger.debug("\"" + treeItem.getText() + "\"");
					}
					throw new EclipseLayerException(
							"Cannot get project item specified by path."
									+ "Project item either does not exist or solution is ambiguous because "
									+ "of existence of more items on the path with same name without decorators");
				}
			}
		}
		return new ProjectItem(item);
	}

	/**
	 * Gets whole text of the explorer item.
	 * 
	 * @return text of the explorer item
	 */
	public String getText() {
		return treeItem.getText();
	}

	/**
	 * Returns title of view containing explorer item.
	 * 
	 * @return title of view containing explorer item
	 */
	public String getTitleOfWrappingView() {
		return WorkbenchPartHandler.getInstance()
				.getTitleOfWorkbenchPartWithWidget(treeItem);
	}

	/**
	 * Gets encapsulated {@link TreeItem} representing the explorer item.
	 * 
	 * @return encapsulated tree item
	 */
	public TreeItem getTreeItem() {
		return treeItem;
	}

	/**
	 * Finds out whether the explorer item is selected or not.
	 * 
	 * @return true if the explorer item is selected, false otherwise
	 */
	public boolean isSelected() {
		return treeItem.isSelected();
	}

	/**
	 * Opens the project item.
	 */
	public void open() {
		select();
		treeItem.doubleClick();
	}

	/**
	 * Refreshes the explorer item.
	 */
	public void refresh() {
		select();
		new ContextMenu("Refresh").select();
		new WaitWhile(new JobIsRunning(), TimePeriod.LONG);
	}
	
	/**
	 * Selects the explorer item.
	 */
	public void select() {
		treeItem.select();
	}
	
	/**
	 * Collapses the explorer item.
	 */
	public void collapse() {
		treeItem.collapse();
	}
}
