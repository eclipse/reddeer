package org.jboss.reddeer.eclipse.test.ui.browser;

import org.jboss.reddeer.eclipse.ui.browser.BrowserView;
import org.jboss.reddeer.swt.impl.browser.InternalBrowser;
import org.junit.Before;
import org.junit.Ignore;

@Ignore	// https://github.com/jboss-reddeer/reddeer/issues/219
public class BrowserViewWithInternalTest extends BrowserViewTest {
	@Before
	@Override
	public void openBrowser() {
		/* Open standard browser - InternalBrowser needs existing widget,
		 * and this one is just fine. */
		BrowserView unusedOne = new BrowserView();
		unusedOne.open();

		/* Create BrowserView using already existing InternalBrowser
		 * - any other instance could be used */
		BrowserViewTest.browserView = new BrowserView(new InternalBrowser());
	}
}
