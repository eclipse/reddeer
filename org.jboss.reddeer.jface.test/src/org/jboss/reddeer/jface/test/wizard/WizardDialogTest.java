package org.jboss.reddeer.jface.test.wizard;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.jboss.reddeer.eclipse.jface.exception.JFaceLayerException;
import org.jboss.reddeer.eclipse.jface.wizard.WizardDialog;
import org.jboss.reddeer.eclipse.jface.wizard.WizardPage;
import org.jboss.reddeer.eclipse.jface.wizard.WizardPageProperty;
import org.jboss.reddeer.swt.api.CLabel;
import org.jboss.reddeer.swt.api.Shell;
import org.jboss.reddeer.swt.condition.ShellWithTextIsActive;
import org.jboss.reddeer.swt.impl.clabel.DefaultCLabel;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.swt.impl.shell.WorkbenchShell;
import org.jboss.reddeer.swt.impl.text.LabeledText;
import org.jboss.reddeer.swt.test.RedDeerTest;
import org.jboss.reddeer.swt.util.Display;
import org.jboss.reddeer.swt.wait.WaitUntil;
import org.junit.Test;

public class WizardDialogTest extends RedDeerTest {

	private org.eclipse.swt.widgets.Shell shell;

	private TestingWizard wizard;
	
	private WizardDialog wizardDialog;
	
	@Override
	protected void setUp(){
	  super.setUp();
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
		wizardDialog = new MultiPageWizardDialog();
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
	
	@Test
	public void multiPageWizardTest() {
		// fill name
		wizardDialog.getWizardPage().fillWizardPage("name");
		// on next page you should see a text box for age 
		wizardDialog.next();
		wizardDialog.getWizardPage().fillWizardPage("100");
	}
	
	@Test
	public void multiPageWizardTest2() {
		// don't fill name
		// on next page you should see a text box for name again
		wizardDialog.next();
		wizardDialog.getWizardPage().fillWizardPage("name");
	}

	@Override
	protected void tearDown(){
		if (!shell.isDisposed()){
			Display.syncExec(new Runnable() {

				@Override
				public void run() {
					shell.close();
				}
			});
		}
		super.tearDown();
	}
	
	private class MultiPageWizardDialog extends WizardDialog {
		
		public MultiPageWizardDialog() {
			// the first page
			addWizardPage(new NameWizardPage(), 0);
			// if name is null
			addWizardPage(new NameWizardPage(), 1, new WizardPageProperty("name", null));
			// if name was specified
			addWizardPage(new AgeWizardPage(), 1, new WizardPageProperty("name", "name"));
		}
		
	}
	
	private class NameWizardPage extends WizardPage {

		public void setName(String name) {
			new LabeledText("Name:").setText(name);
			getWizardDialog().setProperty("name", name);
		}
		
		@Override
		public void fillWizardPage(Object... obj) {
			if(obj.length > 0 && obj[0] instanceof String) {
				setName((String) obj[0]);
			}
		}
		
	}
	
	private class AgeWizardPage extends WizardPage {

		@Override
		public void fillWizardPage(Object... obj) {
			if(obj.length > 0 && obj[0] instanceof String) {
				new LabeledText("Age:").setText((String) obj[0]);
			}
		}
		
	}
}
