package org.jboss.reddeer.eclipse.mylyn.tasks.ui.view;

import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.swt.api.TreeItem;
import org.jboss.reddeer.swt.condition.JobIsRunning;
import org.jboss.reddeer.swt.condition.WaitCondition;
import org.jboss.reddeer.swt.impl.button.PushButton;
import org.jboss.reddeer.swt.impl.menu.ContextMenu;
import org.jboss.reddeer.swt.wait.TimePeriod;
import org.jboss.reddeer.swt.wait.WaitUntil;
import org.jboss.reddeer.swt.wait.WaitWhile;

/**
 * Represents a TaskList on {@link TaskListView}.
 * 
 * @author ldimaggi
 * 
 */
public class TaskList {

	private static final TimePeriod TIMEOUT = TimePeriod.VERY_LONG;

	protected final Logger log = Logger.getLogger(this.getClass());

	private TreeItem treeItem;

	public TaskList(TreeItem treeItem) {
		this.treeItem = treeItem;
	}

	/**
	 * Deletes the task list.
	 */
	public void delete() {
		delete(false);
	}

	/**
	 * Deletes the task list. The stop first option not yet implemented!
	 * 
	 * @param stopFirst Whether to stop first
	 */
	public void delete(boolean stopFirst) {
		log.info("Deleting Repository");
		select();
		new ContextMenu("Delete").select();
		new PushButton("OK").click();
		new WaitUntil(new TreeItemIsDisposed(treeItem), TIMEOUT);
		new WaitWhile(new JobIsRunning(), TIMEOUT);
	}

	/**
	 * Selects the task list.
	 */
	protected void select() {
		treeItem.select();
	}

	/**
	 * Returns a name.
	 * 
	 * @return Name
	 */
	public String getName() {
		return treeItem.getText();
	}

	private class TreeItemIsDisposed implements WaitCondition {

		private TreeItem treeItem;

		public TreeItemIsDisposed(TreeItem treeItem) {
			this.treeItem = treeItem;
		}

		@Override
		public boolean test() {
			return treeItem.isDisposed();
		}

		@Override
		public String description() {
			return "repository tree item is disposed";
		}
	}
}
