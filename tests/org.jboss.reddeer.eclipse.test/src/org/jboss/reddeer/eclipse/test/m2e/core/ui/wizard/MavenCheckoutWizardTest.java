package org.jboss.reddeer.eclipse.test.m2e.core.ui.wizard;


import static org.junit.Assert.*;

import org.jboss.reddeer.eclipse.m2e.core.ui.wizard.MavenCheckoutWizard;
import org.jboss.reddeer.eclipse.m2e.scm.wizard.MavenCheckoutLocationPage;
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
