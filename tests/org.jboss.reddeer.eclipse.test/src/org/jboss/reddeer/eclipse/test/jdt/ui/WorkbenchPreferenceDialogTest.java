package org.jboss.reddeer.eclipse.test.jdt.ui;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import static org.junit.Assert.assertEquals;

import org.jboss.reddeer.common.wait.TimePeriod;
import org.jboss.reddeer.eclipse.jdt.ui.ProjectExplorer;
import org.jboss.reddeer.eclipse.jdt.ui.preferences.FoldingPreferencePage;
import org.jboss.reddeer.eclipse.m2e.core.ui.preferences.MavenSettingsPreferencePage;
import org.jboss.reddeer.eclipse.m2e.core.ui.wizard.MavenProjectWizard;
import org.jboss.reddeer.eclipse.m2e.core.ui.wizard.MavenProjectWizardArtifactPage;
import org.jboss.reddeer.eclipse.m2e.core.ui.wizard.MavenProjectWizardPage;
import org.jboss.reddeer.jface.preference.PreferencePage;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.jboss.reddeer.swt.api.Shell;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.workbench.ui.dialogs.WorkbenchPreferenceDialog;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
public class WorkbenchPreferenceDialogTest {
	
	private static final String DIALOG_TITLE = "Preferences";

	private static final String PAGE_NAME = TestingPreferencePage.TITLE;

	private WorkbenchPreferenceDialog dialog;
	
	private PreferencePage preferencePage;
	
	private  WorkbenchPreferenceDialog wp = new WorkbenchPreferenceDialog();
	private MavenSettingsPreferencePage mp = new MavenSettingsPreferencePage();
	private static String originalSettingsLocation;
	
	@After
	public void cleanWorkbenchPreferences(){
		wp.open();
		wp.select(mp);
		mp.setUserSettingsLocation(originalSettingsLocation);
		wp.ok();
	}
	
	@AfterClass
	public static void clean(){
		ProjectExplorer pe= new ProjectExplorer();
		pe.open();
		pe.deleteAllProjects();
	}
	
	@BeforeClass
	public static void getSettingsLocation(){
		WorkbenchPreferenceDialog wp = new WorkbenchPreferenceDialog();
		MavenSettingsPreferencePage mp = new MavenSettingsPreferencePage();
		wp.open();
		wp.select(mp);
		originalSettingsLocation = mp.getUserSettingsLocation();
		wp.ok();
		MavenProjectWizard mw = new MavenProjectWizard();
		mw.open();
		MavenProjectWizardPage mp1 = new MavenProjectWizardPage();
		mp1.createSimpleProject(true);
		mw.next();
		MavenProjectWizardArtifactPage ap = new MavenProjectWizardArtifactPage();
		ap.setArtifactId("artifact");
		ap.setGroupId("group");
		mw.finish(TimePeriod.VERY_LONG);
	}

	@Before
	public void setup(){
		dialog = new WorkbenchPreferenceDialog();
		preferencePage = new PreferencePageImpl();
	}

	@After
	public void cleanup(){
		Shell shell = null;
		try {
			shell = new DefaultShell(DIALOG_TITLE);
		} catch (SWTLayerException e){
			// not found, no action needed
			return;
		}
		shell.close();
	}

	@Test
	public void openAndSelect(){
		dialog.open();
		
		Shell shell = new DefaultShell();
		assertThat(shell.getText(), is(DIALOG_TITLE));
		
		dialog.select(preferencePage);
		assertThat(dialog.getPageName(), is(PAGE_NAME));
	}
	
	@Test
	public void openAndSelectByPath(){
		dialog.open();
		
		Shell shell = new DefaultShell();
		assertThat(shell.getText(), is(DIALOG_TITLE));
		
		dialog.select(TestingPreferencePage.TestTopCategory.TOP_CATEGORY,
				TestingPreferencePage.TestCategory.CATEGORY, PAGE_NAME);
		assertThat(dialog.getPageName(), is(PAGE_NAME));
	}
	
	@Test
	public void ok(){
		dialog.open();
		dialog.select(preferencePage);
		dialog.ok();

		Shell shell = new DefaultShell();
		assertThat(shell.getText(), is(not(DIALOG_TITLE)));
		assertTrue(TestingPreferencePage.performOkCalled);
	}

	@Test
	public void cancel(){
		dialog.open();
		dialog.select(preferencePage);
		dialog.cancel();

		Shell shell = new DefaultShell();
		assertThat(shell.getText(), is(not(DIALOG_TITLE)));
		assertTrue(TestingPreferencePage.performCancelCalled);
	}

	@Test
	public void apply(){
		dialog.open();
		dialog.select(preferencePage);
		preferencePage.apply();

		Shell shell = new DefaultShell();
		assertThat(shell.getText(), is(DIALOG_TITLE));
		assertThat(preferencePage.getName(), is(PAGE_NAME));
		assertTrue(TestingPreferencePage.performApplyCalled);
	}

	@Test
	public void restoreDefaults(){
		dialog.open();
		dialog.select(preferencePage);
		preferencePage.restoreDefaults();

		Shell shell = new DefaultShell();
		assertThat(shell.getText(), is(DIALOG_TITLE));
		assertThat(preferencePage.getName(), is(PAGE_NAME));
		assertTrue(TestingPreferencePage.performDefaultsCalled);
	}

	class PreferencePageImpl extends PreferencePage {

		public PreferencePageImpl() {
			super(new String[]{TestingPreferencePage.TestTopCategory.TOP_CATEGORY,
					TestingPreferencePage.TestCategory.CATEGORY, PAGE_NAME});
		}
	}
	
	@Test
	public void testPageChangeHandlersAfterOK(){
		wp.open();
		wp.select(mp);
		mp.setUserSettingsLocation("/testLocation");
		wp.ok();
		wp.open();
		wp.select(mp);
		assertEquals("/testLocation", mp.getUserSettingsLocation());
		wp.ok();
	}
	
	@Test
	public void testPageChangeHandlersAfterPageChange(){
		wp.open();
		wp.select(mp);
		mp.setUserSettingsLocation("/testLocation1");
		wp.select(new FoldingPreferencePage());
		wp.ok();
		wp.open();
		wp.select(mp);
		assertEquals("/testLocation1", mp.getUserSettingsLocation());
		wp.ok();
	}
	
	@Test
	public void testPageChangeHandlersAfterManualPageChange(){
		wp.open();
		wp.select(mp);
		mp.setUserSettingsLocation("/testLocation1");
		wp.select("Maven","User Interface");
		wp.ok();
		wp.open();
		wp.select(mp);
		assertEquals("/testLocation1", mp.getUserSettingsLocation());
		wp.ok();
	}
}
