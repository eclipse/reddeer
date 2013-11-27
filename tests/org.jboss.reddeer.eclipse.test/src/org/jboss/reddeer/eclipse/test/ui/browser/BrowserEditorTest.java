package org.jboss.reddeer.eclipse.test.ui.browser;

import static org.junit.Assert.*;

import java.net.MalformedURLException;
import java.net.URL;

import org.jboss.reddeer.eclipse.ui.browser.BrowserEditor;
import org.jboss.reddeer.swt.test.RedDeerTest;
import org.jboss.reddeer.swt.util.Display;
import org.jboss.reddeer.workbench.editor.DefaultEditor;
import org.junit.Before;
import org.junit.Test;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.browser.IWorkbenchBrowserSupport;

public class BrowserEditorTest extends RedDeerTest{
	
	@Before
	public void openBrowser(){
		Display.syncExec(new Runnable() {

			@Override
			public void run() {
				try {
					PlatformUI.getWorkbench().getBrowserSupport().createBrowser(IWorkbenchBrowserSupport.AS_EDITOR  & IWorkbenchBrowserSupport.NAVIGATION_BAR & IWorkbenchBrowserSupport.LOCATION_BAR,null,"a","b").openURL(new URL("http://www.redhat.com"));
				} catch (PartInitException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}
	
	@Test
	public void testEditor() {
		new DefaultEditor("a");
		assertEquals("b",new BrowserEditor("a").getTitleToolTip());
	}

}
