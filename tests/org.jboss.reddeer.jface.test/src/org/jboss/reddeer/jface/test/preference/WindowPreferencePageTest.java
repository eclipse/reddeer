package org.jboss.reddeer.jface.test.preference;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.jboss.reddeer.eclipse.jface.preference.WindowPreferencePage;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.jboss.reddeer.swt.api.Shell;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
public class WindowPreferencePageTest {

	private static final String PAGE_NAME = TestingPreferencePage.TITLE;
	
	private WindowPreferencePage preferencePage;

	@Before
	public void setup(){
		preferencePage = new PreferencePageImpl( 
				TestingPreferencePage.TestTopCategory.TOP_CATEGORY, 
				TestingPreferencePage.TestCategory.CATEGORY, PAGE_NAME);
	}

	@Test
	public void open(){
		preferencePage.open();
		
		Shell shell = new DefaultShell();		
		assertThat(shell.getText(), is(WindowPreferencePage.DIALOG_TITLE));
		assertThat(preferencePage.getName(), is(PAGE_NAME));
	}
	
	@Test
	public void open_preferenceOpen(){
		preferencePage.open();
		preferencePage.open();
		Shell shell = new DefaultShell();
		
		assertThat(shell.getText(), is(WindowPreferencePage.DIALOG_TITLE));
		assertThat(preferencePage.getName(), is(PAGE_NAME));
	}
	
	@After
	public void cleanup(){
		Shell shell = null;
		try {
			shell = new DefaultShell(WindowPreferencePage.DIALOG_TITLE);
		} catch (SWTLayerException e){
			// not found, no action needed
			return;
		}
		shell.close();
	}
	
	class PreferencePageImpl extends WindowPreferencePage {

		public PreferencePageImpl(String... path) {
			super(path);
		}
	}
}
