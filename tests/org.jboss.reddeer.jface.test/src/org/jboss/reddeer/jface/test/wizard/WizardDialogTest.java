package org.jboss.reddeer.jface.test.wizard;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.jboss.reddeer.jface.wizard.WizardDialog;
import org.jboss.reddeer.jface.wizard.WizardPage;
import org.jboss.reddeer.jface.wizard.WizardPageProperty;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.jboss.reddeer.swt.api.CLabel;
import org.jboss.reddeer.swt.api.Shell;
import org.jboss.reddeer.core.condition.ShellWithTextIsActive;
import org.jboss.reddeer.common.exception.WaitTimeoutExpiredException;
import org.jboss.reddeer.swt.impl.clabel.DefaultCLabel;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.workbench.impl.shell.WorkbenchShell;
import org.jboss.reddeer.swt.impl.text.LabeledText;
import org.jboss.reddeer.core.util.Display;
import org.jboss.reddeer.common.wait.WaitUntil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
public class WizardDialogTest {

	private org.eclipse.swt.widgets.Shell shell;

	private TestingWizard wizard;
	
	private WizardDialog wizardDialog;
	
	@Before
	public void setUp(){
		org.jboss.reddeer.core.util.Display.asyncExec(new Runnable() {

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

	@Test(expected=WaitTimeoutExpiredException.class)
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

	@Test(expected=WaitTimeoutExpiredException.class)
	public void back_notEnabled() {
		wizardDialog.back();
	}

	@Test
	public void finish() {
		wizardDialog.finish();
		
		org.jboss.reddeer.swt.api.Shell shell = new DefaultShell();
		assertTrue(shell.getText().equals(new WorkbenchShell().getText()));
	}

	@Test(expected=WaitTimeoutExpiredException.class)
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
		((NameWizardPage) wizardDialog.getCurrentWizardPage()).setName("name");
		wizardDialog.setProperty("name", "name");
		// on next page you should see a text box for age 
		wizardDialog.next();
		((AgeWizardPage) wizardDialog.getCurrentWizardPage()).setAge("100");
	}
	
	@Test
	public void multiPageWizardTest2() {
		// don't fill name
		// on next page you should see a text box for name again
		wizardDialog.next();
		((NameWizardPage) wizardDialog.getCurrentWizardPage()).setName("name");
	}
	
	@Test
	public void getWizardPageTest(){
		NameWizardPage page = (NameWizardPage) wizardDialog.getWizardPage(1);
		assertThat(page, instanceOf(NameWizardPage.class));
		page.setName("name");
		wizardDialog.setProperty("name", "name");
		assertThat(wizardDialog.getWizardPage(0), instanceOf(NameWizardPage.class));
		assertThat(wizardDialog.getWizardPage(1), instanceOf(AgeWizardPage.class));
	}

	@After
	public void tearDown(){
		if (!shell.isDisposed()){
			Display.syncExec(new Runnable() {

				@Override
				public void run() {
					shell.close();
				}
			});
		}
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
		}
	}
	
	private class AgeWizardPage extends WizardPage {

		public void setAge(String age) {
			new LabeledText("Age:").setText(age);
		}
	}
}
