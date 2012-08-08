package org.jboss.reddeer.swt.test;

import static org.junit.Assert.fail;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.jboss.reddeeer.swt.regex.RegexSeq;
import org.jboss.reddeer.swt.api.Menu;
import org.jboss.reddeer.swt.api.Shell;
import org.jboss.reddeer.swt.exception.WidgetNotAvailableException;
import org.jboss.reddeer.swt.impl.menu.ShellMenu;
import org.jboss.reddeer.swt.impl.menu.WorkbenchMenu;
import org.jboss.reddeer.swt.impl.shell.ActiveShell;
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
		Menu m = new WorkbenchMenu("Window", "Preferences");
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
			RegexSeq regexSeq = new RegexSeq("Win.*", "Pref.*");
			new ShellMenu(regexSeq);
		} catch (WidgetNotAvailableException e) {
			fail("there should be no exception");
		}

	}

	@Test
	public void unavailableMenuTest() {
		log.info("unavailable regex menu test");
		try {
			RegexSeq regexSeq = new RegexSeq("Win.*", "Prefz.*");
			new ShellMenu(regexSeq);
			fail("exception should be thrown");
		} catch (WidgetNotAvailableException e) { // do nothing

		}
	}

}
