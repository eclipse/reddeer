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

package org.jboss.reddeer.ui.test.wizard;

import java.util.logging.Logger;

import org.jboss.reddeer.core.exception.CoreLayerException;
import org.jboss.reddeer.eclipse.topmenu.NewMenuWizard;
import org.jboss.reddeer.eclipse.ui.navigator.resources.ProjectExplorer;
import org.jboss.reddeer.eclipse.utils.DeleteUtils;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
public abstract class RedDeerWizardTestCase {

	private static final Logger LOG = Logger.getLogger(RedDeerWizardTestCase.class.getName());
	protected static NewMenuWizard wizard;
	protected static String projectName;
	
	@Before
	public void open() {
		wizard.open();
	}
	
	@After
	public void close() {
		try {
			new DefaultShell(getWizardText());
			wizard.cancel();
		} catch (CoreLayerException ex){
			LOG.info("Shell " + getWizardText() + " is not open");
		}
	}
	
	@AfterClass
	public static void deleteProject() {
		ProjectExplorer pe = new ProjectExplorer();
		pe.open();
		DeleteUtils.forceProjectDeletion(pe.getProject(projectName),true);
	}
	
	abstract String getWizardText();
}
