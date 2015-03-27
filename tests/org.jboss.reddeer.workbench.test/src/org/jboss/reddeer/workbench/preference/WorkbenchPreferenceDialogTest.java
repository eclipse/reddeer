package org.jboss.reddeer.workbench.preference;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.jboss.reddeer.eclipse.jdt.ui.WorkbenchPreferenceDialog;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.jboss.reddeer.swt.api.Shell;
import org.jboss.reddeer.core.exception.SWTLayerException;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
public class WorkbenchPreferenceDialogTest {
	
	private WorkbenchPreferenceDialog preferenceDialog;

	@Before
	public void setup(){
	}

	@Test
	public void open(){
		preferenceDialog = new WorkbenchPreferenceDialog();
		preferenceDialog.open();
		
		
		Shell shell = new DefaultShell();		
		assertThat(shell.getText(), is(WorkbenchPreferenceDialog.DIALOG_TITLE));
	}
	
	@Test
	public void selectPageByPath(){
		preferenceDialog = new WorkbenchPreferenceDialog();
		preferenceDialog.open();
		preferenceDialog.select("General","Workspace");
		
		assertThat(preferenceDialog.getPageName(), is("Workspace"));
	}
	
	@Test
	public void selectPage(){
		WorkbenchPreferencePage page = new WorkbenchPreferencePage("General", "Workspace");
		preferenceDialog = new WorkbenchPreferenceDialog();
		preferenceDialog.open();
		preferenceDialog.select(page);
		
		assertThat(preferenceDialog.getPageName(), is("Workspace"));
	}
	
	@Test
	public void testPreferenceDialogCancel() {
		testPreferenceDialogCancel(true);
	}
	
	@Test
	public void testPreferenceDialogOk() {
		testPreferenceDialogCancel(false);
	}

	private void testPreferenceDialogCancel(boolean cancel){
		preferenceDialog = new WorkbenchPreferenceDialog();
		preferenceDialog.open();
		Shell shell = new DefaultShell();
		
		assertThat(shell.getText(), is(WorkbenchPreferenceDialog.DIALOG_TITLE));
		assertThat(preferenceDialog.isOpen(), is(true));
		if (cancel) {
			preferenceDialog.cancel();
		} else {
			preferenceDialog.ok();
		}
		assertThat(preferenceDialog.isOpen(), is(false));
	}
	
	@After
	public void cleanup(){
		Shell shell = null;
		try {
			shell = new DefaultShell(WorkbenchPreferenceDialog.DIALOG_TITLE);
		} catch (SWTLayerException e){
			// not found, no action needed
			return;
		}
		shell.close();
	}
	
}
