package org.jboss.reddeer.eclipse.mylyn.tasks.ui.view;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.jboss.reddeer.eclipse.exception.EclipseLayerException;

//import org.jboss.reddeer.eclipse.ui.ide.NewRepositoryWizard;  
import org.jboss.reddeer.eclipse.mylyn.tasks.ui.wizards.NewRepositoryWizard;

//import org.jboss.reddeer.eclipse.wst.server.ui.wizard.NewServerWizardDialog;
import org.jboss.reddeer.swt.api.Tree;
import org.jboss.reddeer.swt.api.TreeItem;
//import org.jboss.reddeer.swt.condition.ShellWithTextIsActive;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.swt.impl.menu.ContextMenu;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.swt.impl.tree.DefaultTree;
//import org.jboss.reddeer.swt.wait.TimePeriod;
//import org.jboss.reddeer.swt.wait.WaitUntil;
import org.jboss.reddeer.workbench.view.View;


/**
 * Represents the TaskRepositories view. 
 *  
 * @author 
 *
 */
public class TaskRepositoriesView extends View {
	
	public static final String TITLE = "Task Repositories";

	private static final Logger log = Logger.getLogger(TaskRepositoriesView.class);
	
	public TaskRepositoriesView() {
		super(TITLE);
	}

	public NewRepositoryWizard newTaskRepositories(){
		log.info("Creating new repository");
		open();
		new ContextMenu("New","Add Task Repository...").select();
		new DefaultShell("Add Task Repository...");
		return new NewRepositoryWizard();
	}

	public List<TaskRepository> getTaskRepositories(){
		List<TaskRepository> repositories = new ArrayList<TaskRepository>();

		Tree tree;
		try {
			tree = getRepositoriesTree();
		} catch (SWTLayerException e){
			return new ArrayList<TaskRepository>();
		}
		for (TreeItem item : tree.getItems()){
			repositories.add(new TaskRepository(item));
		}
		return repositories;
	}

	public TaskRepository getTaskRepository(String name){
		for (TaskRepository repository : getTaskRepositories()){
			if (repository.getName().equals(name)){
				return repository;
			}
		}
		throw new EclipseLayerException("There is no repository with name " + name);
	}

	protected Tree getRepositoriesTree(){
		open();
		return new DefaultTree();
	}
}
