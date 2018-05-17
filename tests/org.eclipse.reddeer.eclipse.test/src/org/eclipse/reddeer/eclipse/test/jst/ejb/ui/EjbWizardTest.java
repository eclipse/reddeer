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
package org.eclipse.reddeer.eclipse.test.jst.ejb.ui;

import static org.junit.Assert.assertTrue;

import org.eclipse.reddeer.eclipse.core.resources.DefaultProject;
import org.eclipse.reddeer.eclipse.jdt.ui.packageview.PackageExplorerPart;
import org.eclipse.reddeer.eclipse.jst.ejb.ui.project.facet.EjbProjectFirstPage;
import org.eclipse.reddeer.eclipse.jst.ejb.ui.project.facet.EjbProjectWizard;
import org.eclipse.reddeer.eclipse.ui.perspectives.JavaEEPerspective;
import org.eclipse.reddeer.eclipse.utils.DeleteUtils;
import org.eclipse.reddeer.junit.runner.RedDeerSuite;
import org.eclipse.reddeer.requirements.openperspective.OpenPerspectiveRequirement.OpenPerspective;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
@OpenPerspective(JavaEEPerspective.class)
public class EjbWizardTest {
	
	@After
	public void delete(){
		PackageExplorerPart pe = new PackageExplorerPart();
		pe.open();
		for(DefaultProject p: pe.getProjects()){
			DeleteUtils.forceProjectDeletion(p,true);
		}
	}
    
    @Test
    public void createEJBProject(){
        EjbProjectWizard ejb = new EjbProjectWizard();
        ejb.open();
        EjbProjectFirstPage firstPage = new EjbProjectFirstPage(ejb);
        firstPage.setProjectName("ejbProject");
        ejb.finish();
        PackageExplorerPart pe = new PackageExplorerPart();
        pe.open();
        assertTrue(pe.containsProject("ejbProject"));
    }

}
