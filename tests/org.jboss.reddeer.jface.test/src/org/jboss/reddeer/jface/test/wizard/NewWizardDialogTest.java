package org.jboss.reddeer.jface.test.wizard;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.jboss.reddeer.eclipse.jface.wizard.NewWizardDialog;
import org.jboss.reddeer.eclipse.jface.wizard.WizardPage;
import org.jboss.reddeer.swt.test.RedDeerTest;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.junit.Test;

public class NewWizardDialogTest extends RedDeerTest {

	private NewWizardDialogImpl newWizardDialog = new NewWizardDialogImpl();

	@Test
	public void open() {
		newWizardDialog.open();

		String wizardDialogText = new DefaultShell().getText();
		assertThat(wizardDialogText, is(TestingNewWizard.NAME));
	}

	@Override
	protected void tearDown(){
		newWizardDialog.cancel();
		super.tearDown();
	}
	
	private class NewWizardDialogImpl extends NewWizardDialog {

		public NewWizardDialogImpl() {
			super(TestingNewWizard.CATEGORY, TestingNewWizard.NAME);
		}
		
		@Override
		public WizardPage getFirstPage() {
			return null;
		}
	}
}
