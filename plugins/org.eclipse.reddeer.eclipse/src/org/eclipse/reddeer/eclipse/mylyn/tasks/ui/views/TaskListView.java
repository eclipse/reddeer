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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.reddeer.common.wait.TimePeriod;
import org.eclipse.reddeer.common.wait.WaitUntil;
import org.eclipse.reddeer.eclipse.exception.EclipseLayerException;
import org.eclipse.reddeer.swt.api.Tree;
import org.eclipse.reddeer.swt.api.TreeItem;
import org.eclipse.reddeer.swt.condition.TreeItemHasMinChildren;
import org.eclipse.reddeer.swt.exception.SWTLayerException;
import org.eclipse.reddeer.swt.impl.button.PushButton;
import org.eclipse.reddeer.swt.impl.menu.ShellMenuItem;
import org.eclipse.reddeer.swt.impl.tree.DefaultTree;
import org.eclipse.reddeer.swt.impl.tree.DefaultTreeItem;
import org.eclipse.reddeer.workbench.impl.view.WorkbenchView;

/**
 * Represents the Task List view - to support Mylyn automated tests. 
 *  
 * @author ldimaggi
 *
 */
public class TaskListView extends WorkbenchView {
	
	public static final String TITLE = "Task List";
	
	/**
	 * Constructs the view with {@value #TITLE}.
	 */
	public TaskListView() {
		super(TITLE);
	}

	/**
	 * Returns list of task lists.
	 * 
	 * @return List of task lists
	 */
	public List<TaskList> getTaskLists(){		
		List<TaskList> theTaskLists = new ArrayList<TaskList>();
		Tree tree;
		
		activate();
		try {
			tree = new DefaultTree();
		} catch (SWTLayerException e){
			return new ArrayList<TaskList>();
		}
		for (TreeItem item : tree.getItems()){
			theTaskLists.add(new TaskList(item));
		}
		return theTaskLists;
	}

	/**
	 * Returns a task list with a given name.
	 * 
	 * @param name Name
	 * @return Task list
	 */
	public TaskList getTaskList(String name){
		activate();
		for (TaskList repository : getTaskLists()){
			if (repository.getName().equals(name)){
				return repository;
			}
		}
		throw new EclipseLayerException("There is no repository with name " + name);
	}

	/**
	 * Gets the repositories tree.
	 *
	 * @return the repositories tree
	 */
	protected Tree getRepositoriesTree(){
		activate();
		return new DefaultTree();
	}
	
	/**
	 * Returns a task with a given name and a category. The task is selected and returned as a tree item.
	 * 
	 * @param taskCategory Category
	 * @param taskName Name
	 * @return Task as a tree item
	 */
	public TreeItem getTask (String taskCategory, String taskName) {
		activate();
		new DefaultTree();
		
		DefaultTreeItem theCategory = new DefaultTreeItem (taskCategory);		
		new WaitUntil(new TreeItemHasMinChildren(theCategory, 1), TimePeriod.getCustom(60l)); 
		
		DefaultTreeItem theTask = new DefaultTreeItem (taskCategory, taskName);
		theTask.select();
		return theTask;
	}
	
	/**
	 * Creates the local task test.
	 */
	/* For use in the Task List View */
	public void createLocalTaskTest () {
				
		activate();
		new ShellMenuItem("File", "New", "Other...").select();  
		new DefaultTree();
		DefaultTreeItem theNewTask = new DefaultTreeItem ("Tasks", "Task");
		theNewTask.select();	
		new PushButton("Next >").click();
		
		/* Specify that the new task will be created in the Local repo */
		new DefaultTree();
		DefaultTreeItem theLocalRepo = new DefaultTreeItem ("Local");
		theLocalRepo.select();	
		new PushButton("Finish").click();	
	}
	
}
