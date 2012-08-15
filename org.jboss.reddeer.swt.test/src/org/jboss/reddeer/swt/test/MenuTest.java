package org.jboss.reddeer.swt.test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotView;
import org.jboss.reddeer.swt.api.Menu;
import org.jboss.reddeer.swt.api.Shell;
import org.jboss.reddeer.swt.exception.WidgetNotAvailableException;
import org.jboss.reddeer.swt.impl.menu.ContextMenu;
import org.jboss.reddeer.swt.impl.menu.ShellMenu;
import org.jboss.reddeer.swt.impl.shell.ActiveShell;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.swt.matcher.RegexMatchers;
import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author Jiri Peterka
 *
 */
public class MenuTest {

	protected final Logger log = Logger.getLogger(this.getClass());

	@Before
	public void before() {
		Logger.getRootLogger().setLevel(Level.DEBUG);
	}
	
	@Test
	public void menuTest() {
		log.info("menu test");
		new ActiveShell();
		Menu m = new ShellMenu("Window", "Preferences");
		m.select();
		Shell s = new ActiveShell("Preferences");
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
		} catch (WidgetNotAvailableException e) {
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
		} catch (WidgetNotAvailableException e) { // do nothing

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
		for (int i = 0; i < 100; i++) {
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
		new ActiveShell();
		Menu menu = new ShellMenu("Window", "Preferences");
		assertTrue("Menuitem text not expected to be empty", !menu.getText().equals(""));
	}
}
