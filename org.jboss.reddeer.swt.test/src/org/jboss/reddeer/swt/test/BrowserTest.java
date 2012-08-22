package org.jboss.reddeer.swt.test;

import static org.junit.Assert.*;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.jboss.reddeer.swt.api.Browser;
import org.jboss.reddeer.swt.exception.WidgetNotAvailableException;
import org.jboss.reddeer.swt.impl.browser.InternalBrowser;
import org.jboss.reddeer.swt.util.Bot;
import org.junit.Before;
import org.junit.Test;

/**
 * Test for Browser implementations
 * @author Jiri Peterka
 *
 */
public class BrowserTest {

	protected final Logger log = Logger.getLogger(this.getClass());

	@Before
	public void before() {
		Logger.getRootLogger().setLevel(Level.DEBUG);
	}
	
	@Test
	public void testInternalBrowser() {
		try {
			Browser b = new InternalBrowser();
			b.setURL("http://www.google.com");
			while (!b.isPageLoaded()) {
				Bot.get().sleep(100);				
			}
		}
		catch (WidgetNotAvailableException e) {
			fail("Browser should be available");
		}
	}
}
