package org.jboss.reddeer.eclipse.jdt.ui.packageexplorer;

import java.util.ArrayList;
import java.util.List;

import org.jboss.reddeer.junit.logging.Logger;
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

	/**
	 * Constructs a project item with a given tree item, project and path.
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
	 * Deletes the project. The project is refreshed before deleting.
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
	 * Selects the project.
	 */
	public void select() {
		TreeItem item = project.getTreeItem();
		int index = 0;
		while (index < path.length){
			item = item.getItem(path[index]);
			index++;
		}
		item.select();
	}
	
	/**
	 * Refreshes the project.
	 */
	public void refresh() {
		select();
        new ContextMenu("Refresh").select();
        new WaitWhile(new JobIsRunning(), TimePeriod.LONG);
	}
	
	/**
	 * Returns whether the project is selected.
	 * 
	 * @return whether the project is selected
	 */
	public boolean isSelected() {
		return treeItem.isSelected();
	}
	
	/**
	 * Returns a project's child with a given text.
	 * 
	 * @param text Child's text
	 * @return Child
	 */
	public ProjectItem getChild(String text) {
		String[] childPath = new String[path.length + 1];
		System.arraycopy(path, 0, childPath, 0, path.length);
		childPath[childPath.length - 1] = text;
		return new ProjectItem(treeItem.getItem(text), project, childPath);
	}
	
	/**
	 * Returns a text of the project item.
	 * 
	 * @return Text
	 */
	public String getText() {
		return treeItem.getText();
	}
	
	/**
	 * Returns a project of the project item.
	 * 
	 * @return Project of the project item
	 */
	public Project getProject() {
		return project;
	}
	
	/**
	 * Returns list of children of the project item.
	 * 
	 * @return List of children of the project item
	 */
	public List<ProjectItem> getChildren() {
		List<ProjectItem> childrens = new ArrayList<ProjectItem>();
		for(TreeItem ti : treeItem.getItems()) {
			childrens.add(getChild(ti.getText()));
		}
		return childrens;
	}
}
