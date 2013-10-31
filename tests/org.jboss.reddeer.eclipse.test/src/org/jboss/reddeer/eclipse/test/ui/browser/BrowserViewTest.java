package org.jboss.reddeer.eclipse.test.ui.browser;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import org.jboss.reddeer.eclipse.ui.browser.BrowserView;
import org.jboss.reddeer.swt.test.RedDeerTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

@Ignore	// https://github.com/jboss-reddeer/reddeer/issues/219
public class BrowserViewTest extends RedDeerTest{
	protected static BrowserView browserView;

	protected static final String FIRST_PAGE = "http://www.redhat.com/";
	protected static final String SECOND_PAGE = "http://www.redhat.com/contact/";
	
	@Before
	public void openBrowser(){
		browserView = new BrowserView();
		browserView.open();
	}
	
	@After
	public void closeBrowser(){
		browserView.close();
	}
	
	@Test
	public void testOpenPages() {
		browserView.openPageURL(FIRST_PAGE);
		assertPageIsOpened(FIRST_PAGE);
		
		browserView.openPageURL(SECOND_PAGE);
		assertPageIsOpened(SECOND_PAGE);
	}

	@Test
	public void testBack() {
		browserView.openPageURL(FIRST_PAGE);
		browserView.openPageURL(SECOND_PAGE);
		
		browserView.back();
		assertPageIsOpened(FIRST_PAGE);
	}
	
	@Test
	public void testForward() {
		browserView.openPageURL(FIRST_PAGE);
		browserView.openPageURL(SECOND_PAGE);
		browserView.back();
		
		browserView.forward();
		assertPageIsOpened(SECOND_PAGE);
	}
	
	@Test
	public void testRefreshPage() {
		browserView.openPageURL(FIRST_PAGE);
		
		browserView.refreshPage();
		assertPageIsOpened(FIRST_PAGE);
	}
	
	private void assertPageIsOpened(String url) {
		assertThat(browserView.getPageURL(), is(url));
	}
	
}
