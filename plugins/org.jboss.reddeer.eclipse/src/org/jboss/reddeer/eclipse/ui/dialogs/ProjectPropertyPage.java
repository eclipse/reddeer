package org.jboss.reddeer.eclipse.ui.dialogs;

import org.jboss.reddeer.eclipse.core.resources.Project;

/**
 * Represents a general property page of a given project. Subclasses
 * should represent the concrete property page.
 * 
 * @deprecated since 0.8.0. Use {@link ExplorerItemPropertyDialog} instead.
 * @author Lucia Jelinkova
 * 
 */
@Deprecated
public abstract class ProjectPropertyPage extends PropertyPage {

	private Project project;
	
	/**
	 * @deprecated Use {@link #ProjectPropertyPage(String...)}
	 * @param project
	 * @param path
	 */
	@Deprecated 
	public ProjectPropertyPage(Project project, String... path) {
		super(path);
		this.project = project;
	}
	
	public ProjectPropertyPage(String... path) {
		super(path);
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
