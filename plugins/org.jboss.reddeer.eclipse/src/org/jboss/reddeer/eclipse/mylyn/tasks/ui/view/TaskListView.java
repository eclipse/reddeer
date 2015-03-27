package org.jboss.reddeer.eclipse.mylyn.tasks.ui.view;

import java.util.ArrayList;
import java.util.List;

import org.jboss.reddeer.eclipse.exception.EclipseLayerException;
import org.jboss.reddeer.swt.api.Tree;
import org.jboss.reddeer.swt.api.TreeItem;
import org.jboss.reddeer.swt.condition.TreeItemHasMinChildren;
import org.jboss.reddeer.core.exception.SWTLayerException;
import org.jboss.reddeer.swt.impl.button.PushButton;
import org.jboss.reddeer.swt.impl.menu.ShellMenu;
import org.jboss.reddeer.swt.impl.tree.DefaultTree;
import org.jboss.reddeer.swt.impl.tree.DefaultTreeItem;
import org.jboss.reddeer.common.wait.TimePeriod;
import org.jboss.reddeer.common.wait.WaitUntil;
import org.jboss.reddeer.workbench.impl.view.WorkbenchView;

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
	
	/* For use in the Task List View */
	public void createLocalTaskTest () {
				
		activate();
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
	
}
