package org.jboss.reddeer.jface.test.preference;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import org.jboss.reddeer.eclipse.jface.preference.PreferencePage;
import org.jboss.reddeer.swt.api.Shell;
import org.jboss.reddeer.swt.exception.WidgetNotAvailableException;
import org.jboss.reddeer.swt.impl.shell.ActiveShell;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PreferencePageTest {

	private static final String PAGE_NAME = "Search";
	
	private PreferencePage preferencePage;

	@Before
	public void setup(){
		preferencePage = new PreferencePageImpl("General", PAGE_NAME);
	}

	@Test
	public void open(){
		preferencePage.open();
		Shell shell = new ActiveShell();		
		assertThat(shell.getText(), is(PreferencePage.DIALOG_TITLE));
		assertThat(preferencePage.getName(), is(PAGE_NAME));
	}
	
	@Test
	public void open_preferenceOpen(){
		preferencePage.open();
		preferencePage.open();
		Shell shell = new ActiveShell();
		assertThat(shell.getText(), is(PreferencePage.DIALOG_TITLE));
		assertThat(preferencePage.getName(), is(PAGE_NAME));
	}

	@Test
	public void ok(){
		preferencePage.open();
		preferencePage.ok();
		Shell shell = new ActiveShell();
		assertThat(shell.getText(), is(not(PreferencePage.DIALOG_TITLE)));
	}

	@Test
	public void cancel(){
		preferencePage.open();
		preferencePage.cancel();
		Shell shell = new ActiveShell();
		assertThat(shell.getText(), is(not(PreferencePage.DIALOG_TITLE)));
	}
	
	@Test
	public void apply(){
		preferencePage.open();
		preferencePage.apply();
		Shell shell = new ActiveShell();
		assertThat(shell.getText(), is(PreferencePage.DIALOG_TITLE));
		assertThat(preferencePage.getName(), is(PAGE_NAME));
	}

	@Test
	public void restoreDefaults(){
		preferencePage.open();
		preferencePage.restoreDefaults();
		Shell shell = new ActiveShell();
		assertThat(shell.getText(), is(PreferencePage.DIALOG_TITLE));
		assertThat(preferencePage.getName(), is(PAGE_NAME));
	}
	
	@After
	public void forceClose(){
		Shell shell = null;
		try {
			shell = new ActiveShell(PreferencePage.DIALOG_TITLE);
		} catch (WidgetNotAvailableException e){
			// not found, no action needed
			return;
		}
		shell.close();
	}
}
