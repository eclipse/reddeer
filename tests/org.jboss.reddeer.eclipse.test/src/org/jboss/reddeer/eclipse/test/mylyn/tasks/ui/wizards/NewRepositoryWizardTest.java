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
package org.jboss.reddeer.eclipse.test.mylyn.tasks.ui.wizards;

import org.jboss.reddeer.eclipse.mylyn.tasks.ui.wizards.NewRepositoryWizard;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * 
 * @author ldimaggi
 * 
 */
@RunWith(RedDeerSuite.class)
public class NewRepositoryWizardTest {

	@Test
	public void getWizardTest() {
		
		NewRepositoryWizard theWizard = new NewRepositoryWizard();
		theWizard.open();
		theWizard.cancel();
	
	}
}
