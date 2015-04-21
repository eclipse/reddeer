package org.jboss.reddeer.jface.test.preference;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.jboss.reddeer.eclipse.jdt.ui.WorkbenchPreferenceDialog;
import org.jboss.reddeer.jface.preference.PreferencePage;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.jboss.reddeer.swt.api.Menu;
import org.jboss.reddeer.swt.api.Shell;
import org.jboss.reddeer.swt.api.TreeItem;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.swt.impl.menu.ShellMenu;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.swt.impl.tree.DefaultTreeItem;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
public class PreferencePageTest {
	
	private static final String DIALOG_TITLE = "Preferences";

	private static final String PAGE_NAME = TestingPreferencePage.TITLE;

	private PreferencePage preferencePage;

	@Before
	public void setup(){
		Menu menu = new ShellMenu("Window", "Preferences");
		menu.select();

		TreeItem t = new DefaultTreeItem(TestingPreferencePage.TestTopCategory.TOP_CATEGORY, 
				TestingPreferencePage.TestCategory.CATEGORY, PAGE_NAME);
		t.select();

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
	public void ok(){
		new WorkbenchPreferenceDialog().ok();

		Shell shell = new DefaultShell();
		assertThat(shell.getText(), is(not(DIALOG_TITLE)));
		assertTrue(TestingPreferencePage.performOkCalled);
	}

	@Test
	public void cancel(){
		new WorkbenchPreferenceDialog().cancel();

		Shell shell = new DefaultShell();
		assertThat(shell.getText(), is(not(DIALOG_TITLE)));
		assertTrue(TestingPreferencePage.performCancelCalled);
	}

	@Test
	public void apply(){
		preferencePage.apply();

		Shell shell = new DefaultShell();
		assertThat(shell.getText(), is(DIALOG_TITLE));
		assertThat(preferencePage.getName(), is(PAGE_NAME));
		assertTrue(TestingPreferencePage.performApplyCalled);
	}

	@Test
	public void restoreDefaults(){
		preferencePage.restoreDefaults();

		Shell shell = new DefaultShell();
		assertThat(shell.getText(), is(DIALOG_TITLE));
		assertThat(preferencePage.getName(), is(PAGE_NAME));
		assertTrue(TestingPreferencePage.performDefaultsCalled);
	}

	class PreferencePageImpl extends PreferencePage {

		public PreferencePageImpl() {
			super();
		}
	}
}
