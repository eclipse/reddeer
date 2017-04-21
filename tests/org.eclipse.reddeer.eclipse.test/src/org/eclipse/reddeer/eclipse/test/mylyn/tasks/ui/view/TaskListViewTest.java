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
package org.eclipse.reddeer.eclipse.test.mylyn.tasks.ui.view;

import static org.junit.Assert.assertTrue;

import org.eclipse.reddeer.eclipse.mylyn.tasks.ui.views.TaskListView;
import org.eclipse.reddeer.junit.runner.RedDeerSuite;
import org.eclipse.reddeer.swt.api.TreeItem;
import org.eclipse.reddeer.swt.impl.tree.DefaultTreeItem;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * 
 * @author ldimaggi
 *
 */
@RunWith(RedDeerSuite.class)
public class TaskListViewTest {

	@Test
	public void getRepoTest() {
		
		TaskListView listView = new TaskListView();	
		listView.open();
		listView.createLocalTaskTest();		
		listView.open();
				
		TreeItem retTask = (DefaultTreeItem)listView.getTask ("Uncategorized", "New Task");
		assertTrue ("task was found", (retTask.getText().equals("New Task")));
	}	
}


