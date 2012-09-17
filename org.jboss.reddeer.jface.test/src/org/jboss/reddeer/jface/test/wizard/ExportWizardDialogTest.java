package org.jboss.reddeer.jface.test.wizard;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.jboss.reddeer.eclipse.jface.wizard.ExportWizardDialog;
import org.jboss.reddeer.eclipse.jface.wizard.WizardPage;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.junit.After;
import org.junit.Test;

public class ExportWizardDialogTest {

	private ExportWizardDialogImpl exportWizardDialog = new ExportWizardDialogImpl();

	@Test
	public void open() {
		exportWizardDialog.open();

		String wizardDialogText = new DefaultShell().getText();
		assertThat(wizardDialogText, is(TestingExportWizard.NAME));
	}

	@After
	public void cleanup(){
		exportWizardDialog.cancel();
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
