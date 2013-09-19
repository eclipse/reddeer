package org.jboss.reddeer.swt.test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.apache.log4j.Level;
import org.jboss.reddeer.junit.logging.Logger;
import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotView;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.jboss.reddeer.swt.api.Menu;
import org.jboss.reddeer.swt.api.Shell;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.swt.impl.menu.ContextMenu;
import org.jboss.reddeer.swt.impl.menu.ShellMenu;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.swt.matcher.RegexMatcher;
import org.jboss.reddeer.swt.matcher.RegexMatchers;
import org.jboss.reddeer.swt.matcher.WithMnemonicMatcher;
import org.jboss.reddeer.workbench.view.View;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * 
 * @author Jiri Peterka
 *
 */
@RunWith(RedDeerSuite.class)
public class MenuTest extends RedDeerTest {

	protected final Logger log = Logger.getLogger(this.getClass());
	private ProjectExplorer explorer = new ProjectExplorer();
	private static int limit = 20;

	@Override
	protected void setUp() {
		super.setUp();
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
		Menu m = new ShellMenu(new WithMnemonicMatcher("Help"),
			new RegexMatcher("About.*"));
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
			RegexMatchers m = new RegexMatchers("Win.*", "Pref.*");
			new ShellMenu(m.getMatchers());
		} catch (SWTLayerException e) {
			fail("there should be no exception");
		}

	}

	@Test
	public void unavailableMenuTest() {
		log.info("unavailable regex menu test");
		try {
			RegexMatchers m = new RegexMatchers("Win.*", "Prefz.*");
			new ShellMenu(m.getMatchers());
			fail("exception should be thrown");
		} catch (SWTLayerException e) { // do nothing

		}
	}
	
	@Test 
	public void contextMenuTest() {
		
		SWTWorkbenchBot bot = new SWTWorkbenchBot();
		SWTBotView v = bot.viewByTitle("Project Explorer");
		v.setFocus();				
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
		SWTWorkbenchBot bot = new SWTWorkbenchBot();
		SWTBotView v = bot.viewByTitle("Project Explorer");
		v.setFocus();
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
	
	private class ProjectExplorer extends View {
		public ProjectExplorer() {
			super("General","Project Explorer");
		}
	}
}
