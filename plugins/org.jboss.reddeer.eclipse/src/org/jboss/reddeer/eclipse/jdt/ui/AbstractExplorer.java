package org.jboss.reddeer.eclipse.jdt.ui;

import java.util.ArrayList;
import java.util.List;

import org.jboss.reddeer.eclipse.core.resources.AbstractProject;
import org.jboss.reddeer.eclipse.core.resources.ExplorerItem;
import org.jboss.reddeer.eclipse.core.resources.Project;
import org.jboss.reddeer.eclipse.exception.EclipseLayerException;
import org.jboss.reddeer.eclipse.utils.DeleteUtils;
import org.jboss.reddeer.jface.viewer.handler.TreeViewerHandler;
import org.jboss.reddeer.swt.api.Shell;
import org.jboss.reddeer.swt.api.Tree;
import org.jboss.reddeer.swt.api.TreeItem;
import org.jboss.reddeer.swt.condition.JobIsRunning;
import org.jboss.reddeer.swt.impl.button.CheckBox;
import org.jboss.reddeer.swt.impl.button.PushButton;
import org.jboss.reddeer.swt.impl.menu.ContextMenu;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.swt.impl.tree.DefaultTree;
import org.jboss.reddeer.swt.wait.TimePeriod;
import org.jboss.reddeer.swt.wait.WaitWhile;
import org.jboss.reddeer.workbench.impl.view.WorkbenchView;

/**
 * Common ancestor for Package and Project Explorer and Resource Navigator and any similar ones.
 * Contains common operations for those explorers.
 * 
 * @author Jiri Peterka
 * @author mlabuda@redhat.com
 *
 */
public class AbstractExplorer extends WorkbenchView {

	public AbstractExplorer(String viewTitle) {
		super(viewTitle);
	}

	/**
	 * Select given project names
	 * @param projectName
	 */
	public void selectProjects(String... projectName){
		ArrayList<TreeItem> selectTreeItems = new ArrayList<TreeItem>();
		for(String pname: projectName){
			selectTreeItems.add(getProject(pname).getTreeItem()); //check if project exists
		}
		if (selectTreeItems.size() > 0){
			getTree().selectItems(selectTreeItems.toArray(new TreeItem[]{}));
		}
	}
	
	/**
	 * Select all projects
	 */
	public void selectAllProjects(){
		Tree projectsTree = getTree();
		List<TreeItem> projects = projectsTree.getItems();
		if(projects.size() > 0){
			projectsTree.selectItems(projects.toArray(new TreeItem[projects.size()]));
		}
	}
	
	/**
	 * Check if project exists in explorer
	 * @param projectName given project name to be checked
	 * @return true if project exists and false if not
	 */
	public boolean containsProject (String projectName){
	  boolean result = false;
	  try{
		getProject(projectName);
	    result = true;
	  } catch (EclipseLayerException ele){
	    result = false;
	  }
	  return result;
	}
	
	/**
	 * Provides list of projects in explorer
	 * @return list of projects
	 */
	public List<Project> getProjects(){
		List<Project> projects = new ArrayList<Project>();
		TreeViewerHandler treeViewerHandler = TreeViewerHandler.getInstance();
		
		for (TreeItem item : getTree().getItems()){			
			if (org.jboss.reddeer.direct.project.Project
					.isProject(treeViewerHandler.getNonStyledText(item))) {
				projects.add(new Project(item));
			}
		}
		return projects;
	}
	
	/**
	 * Provides list of all items in explorer
	 * @return list of explorer items
	 */
	public List<ExplorerItem> getExplorerItems() {
		List<ExplorerItem> items = new ArrayList<ExplorerItem>();
		
		for (TreeItem item : getTree().getItems()) {
			items.add(new ExplorerItem(item));
		}		
		return items;
	}
	
	/**
	 * Removes all projects from file system.
	 */
	public void deleteAllProjects(){
		deleteAllProjects(true);
	}
	
	/**
	 * Removes all projects.
	 * @param deleteFromFileSystem true if project should be deleted from file system, false otherwise
	 */
	public void deleteAllProjects(boolean deleteFromFileSystem){
		deleteAllProjects(deleteFromFileSystem, TimePeriod.VERY_LONG);
	}
	
	/**
	 * Removes all projects with custom timeout
	 * @param deleteFromFileSystem true if project should be deleted from file system, false otherwise
	 * @param timeout for waits
	 */
	public void deleteAllProjects(boolean deleteFromFileSystem, TimePeriod timeout){
		activate();
		if(getProjects().size() > 0){
			selectAllProjects();
			new ContextMenu("Refresh").select();
			new WaitWhile(new JobIsRunning(), timeout);
			new ContextMenu("Delete").select();
			Shell s = new DefaultShell("Delete Resources");
			new CheckBox().toggle(deleteFromFileSystem);
			new PushButton("OK").click();
			DeleteUtils.handleDeletion(s, timeout);
		}
	}

	private DefaultTree getTree(){
		activate();
		return new DefaultTree();
	}
		
	/**
	 * Get project form explorer 
	 * @param projectName given project name
	 * @return project instance
	 */
	public Project getProject(String projectName){
		activate();
		for (Project project : getProjects()){
			if (project.getName().equals(projectName)){
				return project;
			}
		}
		throw new EclipseLayerException("There is no project with name " + projectName);
	}	
	
	/**
	 * Gets project with specific project type defined by subclass of Abstract Project.
	 * 
	 * @param projectName name of project to get
	 * @param projectType type of project to get
	 * @return project of specific type with defined name
	 */
	public <T extends AbstractProject> T getProject(final String projectName, Class<T> projectType) {		
		for (TreeItem item : getTree().getItems()){
			try {
				String name =  projectType.getDeclaredConstructor(TreeItem.class).newInstance(item).getName();
				if (name.equals(projectName)) {
					return  projectType.getDeclaredConstructor(TreeItem.class).newInstance(item);
				}
			} catch (EclipseLayerException ex) {
				// Because there are attempts to create from all tree items projects of specific type but
				// not all of them can fit it.
			} catch (ReflectiveOperationException e) {
				// This should not happen.
			}
		}
		
		// There is no such project
		throw new EclipseLayerException("Required project does not exist. Make sure you are using correct project type"
				+ " and desired project exists.");
	}
}
