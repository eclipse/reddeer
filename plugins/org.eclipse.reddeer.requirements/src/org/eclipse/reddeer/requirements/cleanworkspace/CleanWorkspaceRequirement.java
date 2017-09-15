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
package org.eclipse.reddeer.requirements.cleanworkspace;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.eclipse.reddeer.common.exception.RedDeerException;
import org.eclipse.reddeer.common.logging.Logger;
import org.eclipse.reddeer.eclipse.core.resources.DefaultProject;
import org.eclipse.reddeer.eclipse.ui.navigator.resources.ProjectExplorer;
import org.eclipse.reddeer.junit.requirement.AbstractRequirement;
import org.eclipse.reddeer.junit.requirement.Requirement;
import org.eclipse.reddeer.requirements.cleanworkspace.CleanWorkspaceRequirement.CleanWorkspace;
import org.eclipse.reddeer.workbench.handler.EditorHandler;

/**
 * Clean workspace requirement<br><br>
 * 
 * This {@link Requirement} ensures, that all projects are deleted from workspace
 * (aka. workspace is clean).<br><br>
 * 
 * Annotate test class with {@link CleanWorkspace} annotation to have clean
 * workspace before the test cases are executed.<br><br>
 * 
 * Example:<br>
 * <pre>
 * {@code @CleanWorkspace
 * public class TestClass {
 *    // workspace will be cleaned before tests execution
 * }
 * }
 * </pre>
 * 
 * @author rhopp
 * 
 */
public class CleanWorkspaceRequirement extends AbstractRequirement<CleanWorkspace> {
	
	private static final Logger log = Logger.getLogger(CleanWorkspaceRequirement.class);
	
	/**
	 * Marks test class, which requires clean workspace before test cases are executed.
	 */
	@Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
	@Documented
	public @interface CleanWorkspace {
		
	}
	
	/**
	 * Save all editors and delete all projects from workspace.
	 */
	@Override
	public void fulfill() {	
		EditorHandler.getInstance().closeAll(true);
		ProjectExplorer pe = new ProjectExplorer();
		pe.open();
		try{
			pe.deleteAllProjects();
		} catch (RedDeerException ex){
			log.debug("Exception was thrown:");
			ex.printStackTrace();
			log.debug("Delete projects via Eclipse API ");
			for (DefaultProject project : pe.getProjects()){
				org.eclipse.reddeer.direct.project.Project.delete(project.getName(), true, true);
			}
		}
		pe.activate();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.reddeer.junit.requirement.Requirement#cleanUp()
	 */
	@Override
	public void cleanUp() {

	}
}
