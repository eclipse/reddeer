package org.jboss.reddeer.eclipse.core.resources;

import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.eclipse.core.resources.Project;
import org.jboss.reddeer.swt.api.TreeItem;
import org.jboss.reddeer.core.condition.JobIsRunning;
import org.jboss.reddeer.core.condition.ShellWithTextIsActive;
import org.jboss.reddeer.swt.impl.button.PushButton;
import org.jboss.reddeer.swt.impl.menu.ContextMenu;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.swt.impl.tree.DefaultTreeItem;
import org.jboss.reddeer.common.matcher.RegexMatcher;
import org.jboss.reddeer.core.matcher.WithTextMatcher;
import org.jboss.reddeer.common.wait.TimePeriod;
import org.jboss.reddeer.common.wait.WaitWhile;

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
	 * Constructs project item with a given tree item, project and path. For
	 * information how path could look like see {@link Project#getProjectItem}.
	 * 
	 * @param treeItem
	 *            Tree item
	 * @param project
	 *            Project
	 * @param path
	 *            Path
	 */
	public ProjectItem(TreeItem treeItem, Project project, String... path) {
		super(treeItem);
		this.path = path;
		this.project = project;
	}

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
	 * Deletes the project item. The project item is refreshed before deleting.
	 */
	public void delete() {
		refresh();

		log.debug("Delete project item " + treeItem.getText() + ".");

		select();
		new ContextMenu("Delete").select();
		String deleteShellTitle = new DefaultShell(new WithTextMatcher(
				new RegexMatcher("Delete.*"))).getText();
		new PushButton("OK").click();
		new WaitWhile(new ShellWithTextIsActive(deleteShellTitle),
				TimePeriod.LONG);
		new WaitWhile(new JobIsRunning(), TimePeriod.LONG);
	}

	/**
	 * Gets decorators of the project item.
	 * 
	 * @return String array of decorators of the project item
	 * @deprecated use {@link AbstractExplorerItem#getDecoratedParts()} instead
	 */
	@Deprecated
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
}
