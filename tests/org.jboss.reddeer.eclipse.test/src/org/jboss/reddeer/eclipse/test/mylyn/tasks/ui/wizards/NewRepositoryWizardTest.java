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
