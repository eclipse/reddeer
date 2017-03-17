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
package org.jboss.reddeer.eclipse.test.m2e.core.ui.wizard;


import static org.junit.Assert.*;

import org.jboss.reddeer.eclipse.m2e.scm.wizards.MavenCheckoutLocationPage;
import org.jboss.reddeer.eclipse.m2e.scm.wizards.MavenCheckoutWizard;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
public class MavenCheckoutWizardTest {
	
	@Test
	public void openMavenSCMWizard(){
		MavenCheckoutWizard mc = new MavenCheckoutWizard();
		mc.open();
		MavenCheckoutLocationPage ml = new MavenCheckoutLocationPage();
		assertTrue(ml.isCheckoutAllProjects());
		assertTrue(ml.isCheckoutHeadRevision());
		mc.cancel();
		
	}

}
