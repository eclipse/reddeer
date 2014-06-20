package org.jboss.reddeer.swt.test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.jboss.reddeer.junit.logging.Logger;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.jboss.reddeer.swt.api.Menu;
import org.jboss.reddeer.swt.api.Shell;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.swt.impl.menu.ContextMenu;
import org.jboss.reddeer.swt.impl.menu.ShellMenu;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.swt.matcher.RegexMatcher;
import org.jboss.reddeer.swt.matcher.WithMnemonicTextMatcher;
import org.jboss.reddeer.swt.matcher.WithTextMatcher;
import org.jboss.reddeer.swt.matcher.WithTextMatchers;
import org.jboss.reddeer.workbench.impl.view.WorkbenchView;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * 
 * @author Jiri Peterka
 *
 */
@RunWith(RedDeerSuite.class)
public class MenuTest {

	protected final Logger log = Logger.getLogger(this.getClass());
	private ProjectExplorer explorer = new ProjectExplorer();
	private static int limit = 20;

	@Before
	public void setUp() {
		explorer.open();
	}
	
	@Test
	public void preferencesMenuTest() {
		log.info("Preferences menu test");
		new DefaultShell();
		Menu m = new ShellMenu("Window", "Preferences");
		m.select();
		Shell s = new DefaultShell("Preferences");
		s.close();
	}
	
	@Test
	public void aboutMenuTest() {
		log.info("About menu test");
		new DefaultShell();
		@SuppressWarnings("unchecked")
		Menu m = new ShellMenu(new WithMnemonicTextMatcher("Help"),
			new WithTextMatcher(new RegexMatcher("About.*")));
		m.select();
		Shell s = new DefaultShell();
		s.close();
	}

	@Test
	public void logTest() {
		log.debug("debug");
		log.error("error");
		log.fatal("fatal");
		log.warn("info");
		log.info("info");
	}

	@Test
	public void regexMenuTest() {

		log.info("regex menu test");
		try {
			RegexMatcher[] regexMatchers = { 
					new RegexMatcher("Win.*"),
					new RegexMatcher("Pref.*") 
			};
			WithTextMatchers m = new WithTextMatchers(regexMatchers);
			new ShellMenu(m.getMatchers());
		} catch (SWTLayerException e) {
			fail("there should be no exception");
		}

	}

	@Test
	public void unavailableMenuTest() {
		log.info("unavailable regex menu test");
		try {
			RegexMatcher[] regexMatchers = { 
					new RegexMatcher("Win.*"),
					new RegexMatcher("Prefz.*") 
			};
			WithTextMatchers m = new WithTextMatchers(regexMatchers);
			new ShellMenu(m.getMatchers());
			fail("exception should be thrown");
		} catch (SWTLayerException e) { // do nothing

		}
	}
	
	@Test 
	public void contextMenuTest() {
		
		ProjectExplorer pe = new ProjectExplorer();
		pe.open();
			
		Menu menu = new ContextMenu("New","Project...");
		menu.select();
		Shell s = new DefaultShell("New Project");
		s.close();
	}
	
	@Test 
	public void hundertscontextMenuTest() {
		for (int i = 0; i < limit; i++) {
			contextMenuTest();
		}
	}	
	
	@Test 
	public void contextMenuItemTextTest() {
		//make sure shell is focused
		new DefaultShell();
		
		ProjectExplorer pe = new ProjectExplorer();
		pe.open();
		
		Menu menu = new ContextMenu("New","Project...");
		assertTrue("Menuitem text not expected to be empty", !menu.getText().equals(""));
	}
	
	
	@Test 
	public void shellMenuItemTextTest() {
		new DefaultShell();
		Menu menu = new ShellMenu("Window", "Preferences");
		assertTrue("Menuitem text not expected to be empty", !menu.getText().equals(""));
	}
	
	@Test
	public void menuWithMnemonicTest() {
		log.info("menu with mnemonic test");
		new DefaultShell();
		Menu m = new ShellMenu("File", "New" , "Other...");
		m.select();
		Shell s = new DefaultShell("New");
		s.close();
	}
	
	private class ProjectExplorer extends WorkbenchView {
		public ProjectExplorer() {
			super("General","Project Explorer");
		}
	}
}
