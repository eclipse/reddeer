/******************************************************************************* 
 * Copyright (c) 2016 Red Hat, Inc. 
 * Distributed under license by Red Hat, Inc. All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 ******************************************************************************/ 
package org.jboss.reddeer.requirements.cleanworkspace;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.eclipse.core.resources.Project;
import org.jboss.reddeer.eclipse.jdt.ui.ProjectExplorer;
import org.jboss.reddeer.junit.requirement.Requirement;
import org.jboss.reddeer.requirements.cleanworkspace.CleanWorkspaceRequirement.CleanWorkspace;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.workbench.handler.EditorHandler;

/**
 * Clean workspace requirement<br/><br/>
 * 
 * This {@link Requirement} ensures, that all projects are deleted from workspace
 * (aka. workspace is clean).<br/><br>
 * 
 * Annotate test class with {@link CleanWorkspace} annotation to have clean
 * workspace before the test cases are executed.<br/><br/>
 * 
 * Example:<br/>
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
public class CleanWorkspaceRequirement implements Requirement<CleanWorkspace> {
	
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
	 * Always returns true because cleaning workspace should be possible every time.
	 * 
	 * @return true
	 */
	@Override
	public boolean canFulfill() {
		return true; 
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
		} catch (SWTLayerException ex){
			log.debug("Delete projects via Eclipse API ");
			for (Project project : pe.getProjects()){
				org.jboss.reddeer.direct.project.Project.delete(project.getName(), true, true);
			}
		}
		pe.activate();
	}

	/**
	 * This method is empty because annotation {@link CleanWorkspace} has no elements.
	 * However, it is one of methods of {@link Requirement} interface so it has to be
	 * overridden.
	 *
	 * @param declaration the new declaration
	 */
	@Override
	public void setDeclaration(CleanWorkspace declaration) {
		// nothing to do here
	}

	/* (non-Javadoc)
	 * @see org.jboss.reddeer.junit.requirement.Requirement#cleanUp()
	 */
	@Override
	public void cleanUp() {

	}

}
