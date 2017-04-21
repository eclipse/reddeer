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
package org.eclipse.reddeer.requirements.closeeditors;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.eclipse.ui.PlatformUI;
import org.eclipse.reddeer.common.util.Display;
import org.eclipse.reddeer.junit.requirement.Requirement;
import org.eclipse.reddeer.junit.requirement.RequirementException;
import org.eclipse.reddeer.requirements.closeeditors.CloseAllEditorsRequirement.CloseAllEditors;

/**
 * Closes all open editors
 * 
 * @author Lucia Jelinkova
 *
 */
public class CloseAllEditorsRequirement implements Requirement<CloseAllEditors> {

	@Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
	public @interface CloseAllEditors {
		
		/**
		 * Save.
		 *
		 * @return true, if successful
		 */
		boolean save() default true;
	}
	
	private CloseAllEditors declaration;
	
	/* (non-Javadoc)
	 * @see org.eclipse.reddeer.junit.requirement.Requirement#canFulfill()
	 */
	@Override
	public boolean canFulfill() {
		return true;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.reddeer.junit.requirement.Requirement#fulfill()
	 */
	@Override
	public void fulfill() {
		Display.syncExec(new Runnable() {

			@Override
			public void run() {
				boolean result = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
				.getActivePage().closeAllEditors(declaration.save());
				
				if (!result){
					throw new RequirementException("Some editors remained open");
				}
			}
		});
	}

	/* (non-Javadoc)
	 * @see org.eclipse.reddeer.junit.requirement.Requirement#setDeclaration(java.lang.annotation.Annotation)
	 */
	@Override
	public void setDeclaration(CloseAllEditors declaration) {
		this.declaration = declaration;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.reddeer.junit.requirement.Requirement#cleanUp()
	 */
	@Override
	public void cleanUp() {
		// nothing to do
	}
}
