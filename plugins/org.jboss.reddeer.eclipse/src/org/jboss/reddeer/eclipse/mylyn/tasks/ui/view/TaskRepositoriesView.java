package org.jboss.reddeer.eclipse.mylyn.tasks.ui.view;

import java.util.ArrayList;
import java.util.List;

import org.jboss.reddeer.eclipse.exception.EclipseLayerException;
import org.jboss.reddeer.eclipse.mylyn.tasks.ui.wizards.NewRepositoryWizard;
import org.jboss.reddeer.swt.api.Tree;
import org.jboss.reddeer.swt.api.TreeItem;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.swt.impl.button.PushButton;
import org.jboss.reddeer.swt.impl.menu.ContextMenu;
import org.jboss.reddeer.swt.impl.menu.ShellMenu;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.swt.impl.text.DefaultText;
import org.jboss.reddeer.swt.impl.tree.DefaultTree;
import org.jboss.reddeer.swt.impl.tree.DefaultTreeItem;
import org.jboss.reddeer.workbench.impl.view.WorkbenchView;


/**
 * Represents the TaskRepositories view. 
 *  
 * @author 
 *
 */
public class TaskRepositoriesView extends WorkbenchView {
	
	public static final String TITLE = "Task Repositories";
	
	public TaskRepositoriesView() {
		super(TITLE);
	}

	public void saveTask () {
		new ShellMenu("File", "Save").select(); 
	}
	
	/* */
	public void createLocalTask (List<TreeItem> repoItems, ArrayList<String> repoList) {
		
		int elementIndex = repoList.indexOf("Local");
		log.info("Found Local Task Repo: " + repoItems.get(elementIndex).getText());	
		repoItems.get(elementIndex).select();	
		
		new ShellMenu("File", "New", "Other...").select();  
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
	
	public void activateTask (String taskName) {
		new ShellMenu("Navigate", "Activate Task...").select(); 
		new DefaultText().setText(taskName);
		new PushButton("OK").click();	
	}
	
	public void openTask (String taskName) { 
		new ShellMenu("Navigate", "Open Task...").select();    
		new DefaultText().setText(taskName);
		new PushButton("OK").click();	
	}
	
	public void deactivateTask () {
		new ShellMenu("Navigate", "Deactivate Task").select();  
	}
	
	public void deleteTask () {
		new ShellMenu("Edit", "Delete").select();  
		new PushButton("Yes").click();	
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
