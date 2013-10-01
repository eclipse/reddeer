package org.jboss.reddeer.swt.test.impl.browser;

import static org.junit.Assert.assertTrue;

import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.jboss.reddeer.swt.api.Browser;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.swt.impl.browser.InternalBrowser;
import org.jboss.reddeer.swt.test.RedDeerTest;
import org.jboss.reddeer.swt.util.Display;
import org.junit.After;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
public class BrowserTest extends RedDeerTest{
	private static final String BROWSER_LABEL = "Test browser:";
	@Override
	public void setUp() {
		super.setUp();
		Display.syncExec(new Runnable() {
			@Override
			public void run() {
				Shell shell = new Shell( Display.getDisplay());
				shell.setText("Testing shell");
				createControls(shell);
				shell.open();
				shell.setFocus();
			}
		});
	}
	
	private void createControls(Shell shell){
		shell.setLayout(new FillLayout());
		Label label = new Label(shell, SWT.NONE);
		label.setText(BrowserTest.BROWSER_LABEL);
		new org.eclipse.swt.browser.Browser(shell, SWT.NONE);
	}

	@After
	public void cleanup() {
		Display.syncExec(new Runnable() {
			@Override
			public void run() {
				for (Shell shell : org.jboss.reddeer.swt.
						util.Display.getDisplay().getShells()) {
					if (shell.getText().equals("Testing shell")) {
						shell.dispose();
						break;
					}
				}
			}
		});
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
