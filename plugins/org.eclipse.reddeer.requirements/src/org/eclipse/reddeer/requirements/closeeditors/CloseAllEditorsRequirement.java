/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Red Hat, Inc - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.requirements.closeeditors;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.eclipse.reddeer.common.util.Display;
import org.eclipse.reddeer.junit.requirement.AbstractRequirement;
import org.eclipse.reddeer.junit.requirement.RequirementException;
import org.eclipse.reddeer.requirements.closeeditors.CloseAllEditorsRequirement.CloseAllEditors;
import org.eclipse.ui.PlatformUI;

/**
 * Closes all open editors
 * 
 * @author Lucia Jelinkova
 *
 */
public class CloseAllEditorsRequirement extends AbstractRequirement<CloseAllEditors> {

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
	
	@Override
	public void fulfill() {
		Display.syncExec(new Runnable() {

			@Override
			public void run() {
				boolean result = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
				.getActivePage().closeAllEditors(annotation.save());
				
				if (!result){
					throw new RequirementException("Some editors remained open");
				}
			}
		});
	}

	@Override
	public void cleanUp() {
		// nothing to do
	}
}
