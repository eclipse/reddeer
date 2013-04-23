package org.jboss.reddeer.jface.test.preference;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.jboss.reddeer.eclipse.jface.preference.PreferencePage;
import org.jboss.reddeer.swt.test.RedDeerTest;
import org.jboss.reddeer.swt.api.Shell;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.junit.Test;

public class PreferencePageTest extends RedDeerTest{

	private static final String PAGE_NAME = TestingPreferencePage.TITLE;
	private static final String JBOSS_TOOLS_CATEGORY = "JBoss Tools"; 
	
	private PreferencePage preferencePage;

	@Override
	protected void setUp(){
	  super.setUp();
		preferencePage = new PreferencePageImpl(
				JBOSS_TOOLS_CATEGORY, 
				TestingPreferencePage.TestTopCategory.TOP_CATEGORY, 
				TestingPreferencePage.TestCategory.CATEGORY, PAGE_NAME);
	}

	@Test
	public void open(){
		preferencePage.open();
		
		Shell shell = new DefaultShell();		
		assertThat(shell.getText(), is(PreferencePage.DIALOG_TITLE));
		assertThat(preferencePage.getName(), is(PAGE_NAME));
	}
	
	@Test
	public void open_preferenceOpen(){
		preferencePage.open();
		preferencePage.open();
		Shell shell = new DefaultShell();
		
		assertThat(shell.getText(), is(PreferencePage.DIALOG_TITLE));
		assertThat(preferencePage.getName(), is(PAGE_NAME));
	}

	@Test
	public void ok(){
		preferencePage.open();
		preferencePage.ok();
		
		Shell shell = new DefaultShell();
		assertThat(shell.getText(), is(not(PreferencePage.DIALOG_TITLE)));
		assertTrue(TestingPreferencePage.performOkCalled);
	}

	@Test
	public void cancel(){
		preferencePage.open();
		preferencePage.cancel();
		
		Shell shell = new DefaultShell();
		assertThat(shell.getText(), is(not(PreferencePage.DIALOG_TITLE)));
		assertTrue(TestingPreferencePage.performCancelCalled);
	}
	
	@Test
	public void apply(){
		preferencePage.open();
		preferencePage.apply();

		Shell shell = new DefaultShell();
		assertThat(shell.getText(), is(PreferencePage.DIALOG_TITLE));
		assertThat(preferencePage.getName(), is(PAGE_NAME));
		assertTrue(TestingPreferencePage.performApplyCalled);
	}

	@Test
	public void restoreDefaults(){
		preferencePage.open();
		preferencePage.restoreDefaults();
		
		Shell shell = new DefaultShell();
		assertThat(shell.getText(), is(PreferencePage.DIALOG_TITLE));
		assertThat(preferencePage.getName(), is(PAGE_NAME));
		assertTrue(TestingPreferencePage.performDefaultsCalled);
	}
	
	@Override
	protected void tearDown(){
		Shell shell = null;
		try {
			shell = new DefaultShell(PreferencePage.DIALOG_TITLE);
		} catch (SWTLayerException e){
			// not found, no action needed
			return;
		}
		shell.close();
		super.tearDown();
	}
	
	class PreferencePageImpl extends PreferencePage {

		public PreferencePageImpl(String... path) {
			super(path);
		}
	}
}
