/******************************************************************************* 
 * Copyright (c) 2016 Red Hat, Inc. 
 * Distributed under license by Red Hat, Inc. All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 ******************************************************************************/ 
package org.jboss.reddeer.eclipse.mylyn.tasks.ui.view;

import org.jboss.reddeer.common.condition.AbstractWaitCondition;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.common.wait.TimePeriod;
import org.jboss.reddeer.common.wait.WaitUntil;
import org.jboss.reddeer.common.wait.WaitWhile;
import org.jboss.reddeer.core.condition.JobIsRunning;
import org.jboss.reddeer.swt.api.TreeItem;
import org.jboss.reddeer.swt.impl.button.PushButton;
import org.jboss.reddeer.swt.impl.menu.ContextMenu;

	/**
	 * Represents a TaskRepository on {@link TaskRepositoriesView}. 
	 * 
	 * @author ldimaggi
	 * 
	 */
	public class TaskRepository {

		private static final TimePeriod TIMEOUT = TimePeriod.VERY_LONG;
		
		protected final Logger log = Logger.getLogger(this.getClass());
		
		private TreeItem treeItem;

		/**
		 * Instantiates a new task repository.
		 *
		 * @param treeItem the tree item
		 */
		public TaskRepository(TreeItem treeItem) {
			this.treeItem = treeItem;
		}

		/**
		 * Open.
		 */
		public void open() {
			throw new UnsupportedOperationException();
		}

		/**
		 * Delete.
		 */
		public void delete() {
			delete(false);
		}

		/**
		 * Delete.
		 *
		 * @param stopFirst the stop first
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
		 * Select.
		 */
		protected void select() {
			treeItem.select();
		}

		/**
		 * Gets the name.
		 *
		 * @return the name
		 */
		public String getName(){
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
