package org.jboss.reddeer.eclipse.jdt.ui.packageexplorer;

import java.util.ArrayList;
import java.util.List;

import org.jboss.reddeer.jface.viewer.handler.TreeViewerHandler;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.swt.api.TreeItem;
import org.jboss.reddeer.swt.condition.JobIsRunning;
import org.jboss.reddeer.swt.condition.ShellWithTextIsActive;
import org.jboss.reddeer.swt.impl.button.PushButton;
import org.jboss.reddeer.swt.impl.menu.ContextMenu;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.swt.wait.TimePeriod;
import org.jboss.reddeer.swt.wait.WaitWhile;

/**
 * Represents a project item of {@link Project}.
 * 
 * @author Vlado Pakan
 * 
 */
public class ProjectItem {
	protected final Logger log = Logger.getLogger(ProjectItem.class);
	
	protected TreeItem treeItem;
	private Project project;
	private String[] path;
	
	private TreeViewerHandler treeViewerHandler = TreeViewerHandler.getInstance();

	/**
	 * Constructs project item with a given tree item, project and path. For 
	 * information how path could look like see {@link Project#getProjectItem}.
	 * 
	 * @param treeItem Tree item
	 * @param project Project
	 * @param path Path
	 */
	public ProjectItem(TreeItem treeItem, Project project, String... path) {
		this.treeItem = treeItem;
		this.path = path;
		this.project = project;
	}

	/**
	 * Opens the project item.
	 */
	public void open() {
		select();
		treeItem.doubleClick();
	}

	/**
	 * Deletes the project item. The project item is refreshed before deleting.
	 */
	public void delete() {
		refresh();
        log.debug("Delete project item " + treeItem.getText() + " via Package Explorer");
	    new ContextMenu("Delete").select();
	    new DefaultShell("Delete");
		new PushButton("OK").click();
		new WaitWhile(new ShellWithTextIsActive("Delete"),TimePeriod.LONG);
		new WaitWhile(new JobIsRunning(), TimePeriod.LONG);
	}

	/**
	 * Selects the project item.
	 */
	public void select() {
		project.getProjectItem(path).treeItem.select();
	}
	
	/**
	 * Refreshes the project item.
	 */
	public void refresh() {
		select();
        new ContextMenu("Refresh").select();
        new WaitWhile(new JobIsRunning(), TimePeriod.LONG);
	}
	
	/**
	 * Finds out whether the project item is selected or not.
	 * 
	 * @return true if the project item is selected, false otherwise
	 */
	public boolean isSelected() {
		return treeItem.isSelected();
	}
	
	/**
	 * Gets child of the project item. At first the whole text of the child is
	 * checked, then only non-decorated part.
	 * 
	 * @param text text of the project item
	 * @return project item with the specified <var>text</var>
	 */
	public ProjectItem getChild(String text) {
		String[] childPath = new String[path.length + 1];
		System.arraycopy(path, 0, childPath, 0, path.length);
		childPath[childPath.length - 1] = text;
		return project.getProjectItem(childPath);
	}
	
	/**
	 * Gets whole text of the project item.
	 * 
	 * @return text of the project item
	 */
	public String getText() {
		return treeItem.getText();
	}
	
	/**
	 * Gets name of the project item without decorators.
	 * 
	 * @return name of the project item without decorators
	 */
	public String getName() {
		return treeViewerHandler.getNonStyledText(treeItem);
	}
	
	/**
	 * Gets decorators of the project item. 
	 * 
	 * @return String array of decorators of the project item
	 */
	public String[] getDecorators() {
		return treeViewerHandler.getStyledTexts(treeItem);
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
	 * Gets list of children of the project item. 
	 * 
	 * @return list of children of the project item
	 */
	public List<ProjectItem> getChildren() {
		List<ProjectItem> children = new ArrayList<ProjectItem>();
		
		for (TreeItem item: treeItem.getItems()) {
			String name = item.getText();
			String[] childPath = new String[path.length + 1];
			System.arraycopy(path, 0, childPath, 0, path.length);
			childPath[childPath.length - 1] = name;
			children.add(new ProjectItem(item, project, childPath));
		}
		
		return children;
	}
}
