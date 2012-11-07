package org.jboss.reddeer.eclipse.jdt.ui.packageexplorer;

import java.util.ArrayList;
import java.util.List;

import org.jboss.reddeer.eclipse.exception.EclipseLayerException;
import org.jboss.reddeer.swt.api.TreeItem;
import org.jboss.reddeer.swt.impl.tree.ViewTree;
import org.jboss.reddeer.swt.impl.tree.ViewTreeItem;
import org.jboss.reddeer.workbench.view.impl.WorkbenchView;

/**
 * Represents Package Explorer in Eclipse
 * 
 * @author Vlado Pakan
 *
 */
public class PackageExplorer extends WorkbenchView {
    
	public PackageExplorer() {
		super("Package Explorer");
	}
	
	public Project selectProject (String projectName){
		Project project = getProject(projectName);
		project.select();
		return project;
	}
	
	public void selectProjects(String... projectName){
		for(String pname: projectName){
			getProject(pname); //check if project exists
		}
		new ViewTreeItem(projectName).select();
	}
	
	public boolean containsProject (String projectName){
	  boolean result = false;
	  try{
		getProject(projectName);;
	    result = true;
	  } catch (EclipseLayerException ele){
	    result = false;
	  }
	  return result;
	}
	
	public List<Project> getProjects(){
		List<Project> projects = new ArrayList<Project>();

		for (TreeItem item : getPackageExplorerTree().getItems()){
			projects.add(new Project(item));
		}
		return projects;
	}
	
	public ViewTree getPackageExplorerTree(){
		open();
		return new ViewTree();
	}
	
	public Project getProject(String projectName){
		for (Project project : getProjects()){
			if (project.getName().equals(projectName)){
				return project;
			}
		}
		throw new EclipseLayerException("There is no project with name " + projectName);
	}	

}
