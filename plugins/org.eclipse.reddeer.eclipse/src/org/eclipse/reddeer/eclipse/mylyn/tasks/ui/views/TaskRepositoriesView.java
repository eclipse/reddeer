/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Red Hat, Inc - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.eclipse.mylyn.tasks.ui.views;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.reddeer.common.logging.Logger;
import org.eclipse.reddeer.common.wait.TimePeriod;
import org.eclipse.reddeer.common.wait.WaitWhile;
import org.eclipse.reddeer.eclipse.exception.EclipseLayerException;
import org.eclipse.reddeer.eclipse.mylyn.tasks.ui.wizards.NewRepositoryWizard;
import org.eclipse.reddeer.swt.api.Tree;
import org.eclipse.reddeer.swt.api.TreeItem;
import org.eclipse.reddeer.swt.condition.ShellIsAvailable;
import org.eclipse.reddeer.swt.exception.SWTLayerException;
import org.eclipse.reddeer.swt.impl.button.PushButton;
import org.eclipse.reddeer.swt.impl.menu.ContextMenuItem;
import org.eclipse.reddeer.swt.impl.menu.ShellMenuItem;
import org.eclipse.reddeer.swt.impl.shell.DefaultShell;
import org.eclipse.reddeer.swt.impl.text.DefaultText;
import org.eclipse.reddeer.swt.impl.tree.DefaultTree;
import org.eclipse.reddeer.swt.impl.tree.DefaultTreeItem;
import org.eclipse.reddeer.workbench.impl.view.WorkbenchView;


/**
 * Represents the TaskRepositories view. 
 *
 */
public class TaskRepositoriesView extends WorkbenchView {
	
	private static final Logger log = Logger.getLogger(TaskRepositoriesView.class);
	
	public static final String TITLE = "Task Repositories";
	
	/**
	 * Construct thw view with {@value #TITLE}.
	 */
	public TaskRepositoriesView() {
		super(TITLE);
	}

	/**
	 * Saves the task.
	 */
	public void saveTask () {
		new ShellMenuItem("File", "Save").select(); 
	}
	
	/**
	 * Creates new local task.
	 *
	 * @param repoItems the repo items
	 * @param repoList the repo list
	 */
	public void createLocalTask (List<TreeItem> repoItems, ArrayList<String> repoList) {				
		int elementIndex = repoList.indexOf("Local");
		log.info("Found Local Task Repo: '" + repoItems.get(elementIndex).getText() + "'");	
		repoItems.get(elementIndex).select();	
		
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
	
	/**
	 * Activates a task with a given name.
	 * 
	 * @param taskName Task name
	 */
	public void activateTask (String taskName) {		
		new ShellMenuItem("Navigate", "Activate Task...").select(); 
		DefaultShell shellThatTakesLongTimeToClose = new DefaultShell("Activate Task");
		new DefaultText().setText(taskName);
		new PushButton("OK").click();	
		new WaitWhile(new ShellIsAvailable(shellThatTakesLongTimeToClose), TimePeriod.LONG);
	}
	
	/**
	 * Opens a task with a given name.
	 * 
	 * @param taskName Task name
	 */
	public void openTask (String taskName) {		
		new ShellMenuItem("Navigate", "Open Task...").select();  
		DefaultShell shellThatTakesLongTimeToClose = new DefaultShell("Open Task");
		new DefaultText().setText(taskName);
		new PushButton("OK").click();	
		new WaitWhile(new ShellIsAvailable(shellThatTakesLongTimeToClose), TimePeriod.LONG);
	}
	
	/**
	 * Deactivates the task.
	 */
	public void deactivateTask () {		
		new ShellMenuItem("Navigate", "Deactivate Task").select();  
	}
	
	/**
	 * Deletes the task.
	 */
	public void deleteTask () {		
		new ShellMenuItem("Edit", "Delete").select();  
		new PushButton("Yes").click();	
	}
	
	/**
	 * New task repositories.
	 *
	 * @return the new repository wizard
	 */
	public NewRepositoryWizard newTaskRepositories(){		
		log.info("Creating new repository");
		new ContextMenuItem("New","Add Task Repository...").select();
		new DefaultShell("Add Task Repository...");
		return new NewRepositoryWizard();
	}

	/**
	 * Gets the task repositories.
	 *
	 * @return the task repositories
	 */
	public List<TaskRepository> getTaskRepositories(){
		List<TaskRepository> repositories = new ArrayList<TaskRepository>();

		Tree tree;
		try {
			tree = getRepositoriesTree();
		} catch (SWTLayerException e){
			log.debug("No Task repository is defined");
			return new ArrayList<TaskRepository>();
		}
		for (TreeItem item : tree.getAllItems()){
			log.debug("Found Task repository: "+item.getText());
			repositories.add(new TaskRepository(item));
		}
		return repositories;
	}

	/**
	 * Gets the task repository.
	 *
	 * @param name the name
	 * @return the task repository
	 */
	public TaskRepository getTaskRepository(String name){
		for (TaskRepository repository : getTaskRepositories()){
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
		return new DefaultTree();
	}
}
