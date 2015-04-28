package org.jboss.reddeer.eclipse.test.ui.dialogs;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;

import org.jboss.reddeer.eclipse.core.resources.Project;
import org.jboss.reddeer.eclipse.jdt.ui.ide.NewJavaProjectWizardDialog;
import org.jboss.reddeer.eclipse.jdt.ui.ide.NewJavaProjectWizardPage;
import org.jboss.reddeer.eclipse.jdt.ui.packageexplorer.PackageExplorer;
import org.jboss.reddeer.eclipse.ui.dialogs.ExplorerItemPropertyDialog;
import org.jboss.reddeer.eclipse.ui.dialogs.PropertyPage;
import org.jboss.reddeer.eclipse.utils.DeleteUtils;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.jboss.reddeer.swt.api.Shell;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
public class ExplorerItemPropertyDialogTest {

	private static final String PROJECT_NAME = "Property test project";

	private static Project project;

	private ExplorerItemPropertyDialog dialog;

	private PropertyPage page;

	@BeforeClass
	public static void createProject(){
		NewJavaProjectWizardDialog wizardDialog = new NewJavaProjectWizardDialog();
		wizardDialog.open();
		NewJavaProjectWizardPage page1 = new NewJavaProjectWizardPage();
		page1.setProjectName(PROJECT_NAME);
		wizardDialog.finish();		
		
		PackageExplorer explorer = new PackageExplorer();
		explorer.open();
		project = explorer.getProject(PROJECT_NAME);
	}

	@Before
	public void setup() {
		dialog = new ExplorerItemPropertyDialog(project);
		page = new TestPropertyPageRedDeer();
	}

	@After 
	public void cleanup(){
		Shell shell = null;
		try {
			shell = new DefaultShell(dialog.getTitle());
			shell.close();
		} catch (SWTLayerException e){
			// not found, no action needed
		}
	}

	@AfterClass
	public static void deleteProject(){
		DeleteUtils.forceProjectDeletion(project,true);		
	}
	
	@Test
	public void openAndSelect(){
		dialog.open();

		Shell shell = new DefaultShell();
		assertThat(shell.getText(), is(dialog.getTitle()));

		dialog.select(page);
		assertThat(dialog.getPageName(), is(TestPropertyPage.PAGE_TITLE));
	}

	@Test
	public void openAndSelectByPath(){
		dialog.open();

		Shell shell = new DefaultShell();
		assertThat(shell.getText(), is(dialog.getTitle()));

		dialog.select(TestPropertyPage.PAGE_TITLE);
		assertThat(dialog.getPageName(), is(TestPropertyPage.PAGE_TITLE));
	}

	@Test
	public void ok(){
		dialog.open();
		dialog.select(page);
		dialog.ok();

		Shell shell = new DefaultShell();
		assertThat(shell.getText(), is(not(dialog.getTitle())));
		assertTrue(TestPropertyPage.performOkCalled);
	}

	@Test
	public void cancel(){
		dialog.open();
		dialog.select(page);
		dialog.cancel();

		Shell shell = new DefaultShell();
		assertThat(shell.getText(), is(not(dialog.getTitle())));
		assertTrue(TestPropertyPage.performCancelCalled);
	}

	@Test
	public void apply(){
		dialog.open();
		dialog.select(page);
		page.apply();

		Shell shell = new DefaultShell();
		assertThat(shell.getText(), is(dialog.getTitle()));
		assertThat(page.getName(), is(TestPropertyPage.PAGE_TITLE));
		assertTrue(TestPropertyPage.performApplyCalled);
	}

	@Test
	public void restoreDefaults(){
		dialog.open();
		dialog.select(page);
		page.restoreDefaults();

		Shell shell = new DefaultShell();
		assertThat(shell.getText(), is(dialog.getTitle()));
		assertThat(page.getName(), is(TestPropertyPage.PAGE_TITLE));
		assertTrue(TestPropertyPage.performDefaultsCalled);
	}

	private class TestPropertyPageRedDeer extends PropertyPage {
		public TestPropertyPageRedDeer() {
			super(TestPropertyPage.PAGE_TITLE);
		}
	}
}
