/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Red Hat Inc. - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.eclipse.mylyn.tasks.ui.views;

import static org.eclipse.reddeer.common.wait.WaitProvider.waitUntil;
import static org.eclipse.reddeer.common.wait.WaitProvider.waitWhile;

import org.eclipse.reddeer.common.condition.AbstractWaitCondition;
import org.eclipse.reddeer.common.logging.Logger;
import org.eclipse.reddeer.common.wait.GroupWait;
import org.eclipse.reddeer.common.wait.TimePeriod;
import org.eclipse.reddeer.swt.api.TreeItem;
import org.eclipse.reddeer.swt.impl.button.PushButton;
import org.eclipse.reddeer.swt.impl.menu.ContextMenu;
import org.eclipse.reddeer.workbench.core.condition.JobIsRunning;

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

	/**
	 * Instantiates a new task list.
	 *
	 * @param treeItem the tree item
	 */
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
		new GroupWait(TIMEOUT, waitUntil(new TreeItemIsDisposed(treeItem)), waitWhile(new JobIsRunning()));
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

	private class TreeItemIsDisposed extends AbstractWaitCondition {

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
