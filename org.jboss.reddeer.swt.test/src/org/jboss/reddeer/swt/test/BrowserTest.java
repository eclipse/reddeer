package org.jboss.reddeer.swt.test;

import static org.junit.Assert.fail;

import org.jboss.reddeer.junit.logging.Logger;
import org.jboss.reddeer.swt.api.Browser;
import org.jboss.reddeer.swt.api.Menu;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.swt.impl.browser.InternalBrowser;
import org.jboss.reddeer.swt.impl.button.PushButton;
import org.jboss.reddeer.swt.impl.menu.ShellMenu;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.swt.impl.tree.DefaultTreeItem;
import org.jboss.reddeer.swt.matcher.RegexMatchers;
import org.jboss.reddeer.swt.wait.AbstractWait;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Test for Browser implementations
 * @author Jiri Peterka
 *
 */
@Ignore	// https://github.com/jboss-reddeer/reddeer/issues/219
public class BrowserTest extends RedDeerTest {

	protected final Logger log = Logger.getLogger(this.getClass());

	@Override
	public void setUp() {
	  super.setUp();
	// Open Internal Web Browser
	// TODO: should be replaced with Internal Web Browses view once it's implemented
    new DefaultShell();
    RegexMatchers m = new RegexMatchers("Window.*", "Show View.*",
        "Other...*");
    Menu menu = new ShellMenu(m.getMatchers());
    menu.select();
    new DefaultShell("Show View");
    new DefaultTreeItem("General", "Internal Web Browser").select();
    new PushButton("OK").click();
	}
	
	@Test
	public void testInternalBrowser() {
		try {
			Browser b = new InternalBrowser();
			b.setURL("http://www.google.com");
			final int limit = 100; // 100 cycles maximum
			int counter = 0;
			while (!b.isPageLoaded() && counter < limit) {
				counter++;
				AbstractWait.sleep(100);
			}
		}
		catch (SWTLayerException e) {
			fail("Browser should be available");
		}
	}
}
