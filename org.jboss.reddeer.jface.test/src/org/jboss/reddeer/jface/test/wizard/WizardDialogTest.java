package org.jboss.reddeer.jface.test.wizard;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.jboss.reddeer.eclipse.jface.exception.JFaceLayerException;
import org.jboss.reddeer.eclipse.jface.wizard.WizardDialog;
import org.jboss.reddeer.eclipse.jface.wizard.WizardPage;
import org.jboss.reddeer.swt.api.CLabel;
import org.jboss.reddeer.swt.api.Shell;
import org.jboss.reddeer.swt.condition.ShellWithTextIsActive;
import org.jboss.reddeer.swt.impl.clabel.DefaultCLabel;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.swt.impl.shell.WorkbenchShell;
import org.jboss.reddeer.swt.util.Display;
import org.jboss.reddeer.swt.wait.WaitUntil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class WizardDialogTest {

	private org.eclipse.swt.widgets.Shell shell;

	private TestingWizard wizard;
	
	private WizardDialog wizardDialog;
	
	@Before
	public void openWizard(){
		org.jboss.reddeer.swt.util.Display.asyncExec(new Runnable() {

			@Override
			public void run() {
				shell = new org.eclipse.swt.widgets.Shell(org.eclipse.swt.widgets.Display.getDefault());
				wizard = new TestingWizard();
				
				org.eclipse.jface.wizard.WizardDialog swtWizardDialog = new org.eclipse.jface.wizard.WizardDialog(shell, wizard);
				swtWizardDialog.create();
				swtWizardDialog.open();
			}
		});
		new WaitUntil(new ShellWithTextIsActive(""));
		wizardDialog = new WizardDialogImpl();
	}

	@Test
	public void next() {
		wizardDialog.next();

		CLabel label = new DefaultCLabel();
		assertThat(label.getText(), is("B"));
	}

	@Test(expected=JFaceLayerException.class)
	public void next_notEnabled() {
		wizardDialog.next();
		wizardDialog.next();
	}

	@Test
	public void back() {
		wizardDialog.next();
		wizardDialog.back();

		CLabel label = new DefaultCLabel();
		assertThat(label.getText(), is("A"));
	}

	@Test(expected=JFaceLayerException.class)
	public void back_notEnabled() {
		wizardDialog.back();
	}

	@Test
	public void finish() {
		wizardDialog.finish();
		
		org.jboss.reddeer.swt.api.Shell shell = new DefaultShell();
		assertTrue(shell.getText().equals(new WorkbenchShell().getText()));
	}

	@Test(expected=JFaceLayerException.class)
	public void finish_notEnabled() {
		wizard.setFinishEnabled(false);
		
		wizardDialog.finish();
	}

	@Test
	public void cancel() {
		wizardDialog.cancel();
		
		Shell shell = new DefaultShell();
		assertTrue(shell.getText().equals(new WorkbenchShell().getText()));
	}

	@After
	public void cancelWizard(){
		if (!shell.isDisposed()){
			Display.syncExec(new Runnable() {

				@Override
				public void run() {
					shell.close();
				}
			});
		}
	}

	private class WizardDialogImpl extends WizardDialog {

		@Override
		public WizardPage getFirstPage() {
			return null;
		}
	}
}
