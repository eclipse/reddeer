package org.jboss.reddeer.jface.test.wizard;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.jboss.reddeer.eclipse.jface.wizard.ImportWizardDialog;
import org.jboss.reddeer.eclipse.jface.wizard.WizardPage;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
public class ImportWizardDialogTest {

	private ImportWizardDialogImpl importWizardDialog = new ImportWizardDialogImpl();

	@Test
	public void open() {
		importWizardDialog.open();

		String wizardDialogText = new DefaultShell().getText();
		assertThat(wizardDialogText, is(TestingImportWizard.NAME));
	}

	@After
	public void tearDown(){
		importWizardDialog.cancel();
	}
	
	private class ImportWizardDialogImpl extends ImportWizardDialog {

		public ImportWizardDialogImpl() {
			super(TestingImportWizard.CATEGORY, TestingImportWizard.NAME);
		}
		
		@Override
		public WizardPage getFirstPage() {
			return null;
		}
	}
}
