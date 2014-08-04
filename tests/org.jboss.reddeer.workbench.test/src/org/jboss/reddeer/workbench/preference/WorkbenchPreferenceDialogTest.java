package org.jboss.reddeer.workbench.preference;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.jboss.reddeer.eclipse.jdt.ui.WorkbenchPreferenceDialog;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.jboss.reddeer.swt.api.Shell;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.swt.impl.tree.DefaultTreeItem;
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
		assertThat(shell.getText(), is(WorkbenchPreferencePage.DIALOG_TITLE));
	}
	
	@Test
	public void openPage(){
		preferenceDialog = new WorkbenchPreferenceDialog();
		preferenceDialog.open("General","Workspace");
		
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
		
		assertThat(shell.getText(), is(WorkbenchPreferencePage.DIALOG_TITLE));
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
			shell = new DefaultShell(WorkbenchPreferencePage.DIALOG_TITLE);
		} catch (SWTLayerException e){
			// not found, no action needed
			return;
		}
		shell.close();
	}
	
}
