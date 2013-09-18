package org.jboss.reddeer.jface.test.wizard;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.jboss.reddeer.eclipse.jface.wizard.ExportWizardDialog;
import org.jboss.reddeer.eclipse.jface.wizard.WizardPage;
import org.jboss.reddeer.swt.test.RedDeerTest;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.junit.Test;

public class ExportWizardDialogTest extends RedDeerTest {

	private ExportWizardDialogImpl exportWizardDialog = new ExportWizardDialogImpl();

	@Test
	public void open() {
		exportWizardDialog.open();

		String wizardDialogText = new DefaultShell().getText();
		assertThat(wizardDialogText, is(TestingExportWizard.NAME));
	}

	@Override
	protected void tearDown(){
		exportWizardDialog.cancel();
		super.tearDown();
	}
	
	private class ExportWizardDialogImpl extends ExportWizardDialog {

		public ExportWizardDialogImpl() {
			super(TestingExportWizard.CATEGORY, TestingExportWizard.NAME);
		}
		
		@Override
		public WizardPage getFirstPage() {
			return null;
		}
	}
}
