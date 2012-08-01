package org.jboss.reddeer.swt.test;

import static org.junit.Assert.*;

import org.jboss.reddeer.swt.api.Shell;
import org.jboss.reddeer.swt.impl.shell.ActiveShell;
import org.jboss.reddeer.swt.impl.shell.WorkbenchShell;
import org.jboss.reddeer.swt.lookup.impl.Lookup;
import org.junit.Test;

public class ShellTest {

	@Test
	public void workbenchShellTest() {
		Shell shell = new WorkbenchShell();
		assertFalse(shell.getText().equals(""));
	}
	
	@Test
	public void activeShellTest() {
		Shell shell = new ActiveShell();
		assertFalse(shell.getText().equals(""));
	}
	
	@Test
	public void regexLookupTest() {
		Shell shell = Lookup.shell.byRegex("Eclipse.*");		
		Shell[] shells = Lookup.shells.byRegex("Eclipse.*");
		assertNull(shell);
		assertNull(shells);
		
	}
	

}
