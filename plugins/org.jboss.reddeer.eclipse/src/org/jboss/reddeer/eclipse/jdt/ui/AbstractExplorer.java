package org.jboss.reddeer.eclipse.jdt.ui;

import java.util.ArrayList;
import java.util.List;

import org.jboss.reddeer.eclipse.exception.EclipseLayerException;
import org.jboss.reddeer.eclipse.jdt.ui.packageexplorer.Project;
import org.jboss.reddeer.swt.api.TreeItem;
import org.jboss.reddeer.swt.impl.tree.DefaultTree;
import org.jboss.reddeer.workbench.view.impl.WorkbenchView;

/**
 * Common ancestor for Package and Project Explorer
 * Contains routines common for both explorers
 * @author Jiri Peterka
 *
 */
public class AbstractExplorer extends WorkbenchView {

	public AbstractExplorer(String viewTitle) {
		super(viewTitle);
	}

	/**
	 * Select given project
	 * @deprecated use getProject().select():
	 * @param projectName
	 * @return
	 */
	public Project selectProject (String projectName){
		Project project = getProject(projectName);
		project.select();
		return project;
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

		for (TreeItem item : getTree().getItems()){
			projects.add(new Project(item));
		}
		return projects;
	}

	/**
	 * @deprecated should not be used 
	 * @return explorer
	 */
	public DefaultTree getPackageExplorerTree(){
		return getTree();
	}
	
	private DefaultTree getTree(){
		open();
		return new DefaultTree();
	}
		
	/**
	 * Get project form explorer 
	 * @param projectName given project name
	 * @return project instance
	 */
	public Project getProject(String projectName){
		for (Project project : getProjects()){
			if (project.getName().equals(projectName)){
				return project;
			}
		}
		throw new EclipseLayerException("There is no project with name " + projectName);
	}	
	
}
