package org.jboss.reddeer.swt.test.impl.browser;

import static org.junit.Assert.assertTrue;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;
import org.jboss.reddeer.swt.api.Browser;
import org.jboss.reddeer.core.exception.SWTLayerException;
import org.jboss.reddeer.swt.impl.browser.InternalBrowser;
import org.jboss.reddeer.swt.test.SWTLayerTestCase;
import org.jboss.reddeer.swt.test.utils.LabelTestUtils;
import org.junit.Ignore;
import org.junit.Test;

public class BrowserTest extends SWTLayerTestCase{
	
	private static final String BROWSER_LABEL = "Test browser:";
	
	@Override
	protected void createControls(Shell shell){
		LabelTestUtils.createLabel(shell, BROWSER_LABEL);
		org.eclipse.swt.browser.Browser browser = new org.eclipse.swt.browser.Browser(shell, SWT.NONE);
		browser.setSize(400, 400);
	}

	@Test @Ignore
	public void findBrowserByLabel(){
		new InternalBrowser(BrowserTest.BROWSER_LABEL);
	}
	
	@Test
	public void findBrowserByIndex(){
		new InternalBrowser(0);
	}
	
	@Test(expected=SWTLayerException.class)
	public void findNonExistingBrowserByLabel(){
		new InternalBrowser("@#NON_EXISITNG_LABEL%$");
	}
	
	@Test(expected=SWTLayerException.class)
	public void findNonExistingBrowserByIndex(){
		new InternalBrowser(1);
	}
	
	@Test
	public void navigate(){
		Browser browser = new InternalBrowser(0);
		browser.setURL("http://www.eclipse.org/swt/snippets/");
		final String snippetsPageContent = "<title>SWT Snippets</title>";
		assertTrue("Browser has to contain text '" + snippetsPageContent + "' but it doesn't",
			browser.getText().contains(snippetsPageContent));
		browser.setURL("http://www.eclipse.org/swt/widgets/");
		final String widgetsPageContent = "<title>SWT Widgets</title>";
		assertTrue("Browser has to contain text '" + widgetsPageContent + "' but it doesn't",
			browser.getText().contains(widgetsPageContent));
		browser.back();
		assertTrue("Browser has to contain text '" + snippetsPageContent + "' but it doesn't",
				browser.getText().contains(snippetsPageContent));
		browser.forward();
		assertTrue("Browser has to contain text '" + widgetsPageContent + "' but it doesn't",
				browser.getText().contains(widgetsPageContent));	}
}
