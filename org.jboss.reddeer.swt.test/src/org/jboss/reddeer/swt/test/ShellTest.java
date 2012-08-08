package org.jboss.reddeer.swt.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.jboss.reddeeer.swt.regex.Regex;
import org.jboss.reddeer.swt.api.Menu;
import org.jboss.reddeer.swt.api.Shell;
import org.jboss.reddeer.swt.exception.MultipleMatchException;
import org.jboss.reddeer.swt.exception.WidgetNotAvailableException;
import org.jboss.reddeer.swt.impl.menu.WorkbenchMenu;
import org.jboss.reddeer.swt.impl.shell.ActiveShell;
import org.jboss.reddeer.swt.impl.shell.WorkbenchShell;
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
	public void foundRegexShellTest() {
		new ActiveShell();
		Menu m = new WorkbenchMenu("Window","Preferences");
		m.select();
		Shell s = new ActiveShell("Preferences");
		Regex regex = new Regex("Prefer.*");
		Shell shell = new ActiveShell(regex);
		assertNotNull(shell);
		s.close();
	}
	
	@Test
	public void unavailableRegexShellTest() {
		try {
			Regex regex = new Regex("XYZ.*");
			new ActiveShell(regex);
			fail("Widget not available should be thrown");
		}
		catch (WidgetNotAvailableException e) {

		}
	}

	@Test
	public void multipleShell() {
		new ActiveShell();
		Menu m = new WorkbenchMenu("Window","Preferences");
		m.select();
		Shell s = new ActiveShell("Preferences");
		try {		
			Regex regex = new Regex(".*");
			new ActiveShell(regex);
			fail("Multiple match exception should be thrown");
		}			
		catch (MultipleMatchException e) {
			
		}		
		s.close();
	}

}
