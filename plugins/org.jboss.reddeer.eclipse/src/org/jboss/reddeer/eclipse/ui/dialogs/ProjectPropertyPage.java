package org.jboss.reddeer.eclipse.ui.dialogs;

import org.jboss.reddeer.eclipse.jdt.ui.packageexplorer.Project;

/**
 * Represents a general property page of a given project. Subclasses
 * should represent the concrete property page.
 * 
 * @author Lucia Jelinkova
 * 
 */
public abstract class ProjectPropertyPage extends PropertyPage {

	private Project project;
	
	public ProjectPropertyPage(Project project, String... path) {
		super(path);
		this.project = project;
	}

	@Override
	protected String getResourceName() {
		return project.getName();
	}
	
	@Override
	public void open() {
		project.select();
		super.open();
	}
}
