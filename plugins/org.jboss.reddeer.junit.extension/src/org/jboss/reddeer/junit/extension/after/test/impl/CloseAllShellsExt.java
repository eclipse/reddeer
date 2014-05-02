package org.jboss.reddeer.junit.extension.after.test.impl;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.widgets.Shell;
import org.jboss.reddeer.junit.extensionpoint.IAfterTest;
import org.jboss.reddeer.junit.internal.screenshot.CaptureScreenshot;
import org.jboss.reddeer.junit.internal.screenshot.CaptureScreenshotException;
import org.jboss.reddeer.swt.handler.IBeforeShellIsClosed;
import org.jboss.reddeer.swt.handler.ShellHandler;
import org.jboss.reddeer.swt.handler.WidgetHandler;
import org.junit.Assert;

/**
 * Extension for Extension point org.jboss.reddeer.junit.after.test. It closes
 * all shells after each test. If there is an open shell the test will fail.<br>
 * Use this system property to enable/disable it:
 * 
 * - reddeer.close.shells=[true|false] (default=true)
 * 
 * @author Andrej Podhradsky
 *
 */
public class CloseAllShellsExt implements IAfterTest {

	public static final boolean CLOSE_ALL_SHELLS = System.getProperty("reddeer.close.shells", "true").equalsIgnoreCase(
			"true");

	private Object target;
	/**
	 * See {@link IAfterTest}
	 */
	@Override
	public void runAfterTest(Object target) {
		this.target = target;
		if (hasToRun()) {
			BeforeShellIsClosedAdapter beforeShellIsClosedAdapter = 
				new BeforeShellIsClosedAdapter(new ArrayList<String>());
			ShellHandler.getInstance().closeAllNonWorbenchShells(beforeShellIsClosedAdapter);
			if (beforeShellIsClosedAdapter.getClosedShellsTitles().size() > 0) {
				Assert.fail("The following shells remained open " + beforeShellIsClosedAdapter.getClosedShellsTitles());
			}
		}
	}

	/**
	 * See {@link IAfterTest}
	 */
	@Override
	public boolean hasToRun() {
		return CLOSE_ALL_SHELLS;
	}
	/**
	 * See {@link IBeforeShellIsClosed}
	 */
	private class BeforeShellIsClosedAdapter implements IBeforeShellIsClosed{
		
		private List<String> closedShellsTitles;
		
		public BeforeShellIsClosedAdapter (List<String> closedShellsTitles){
			this.closedShellsTitles = closedShellsTitles;
		}
		
		public void runBeforeShellIsClosed(Shell shell) {
			String shellTitle = WidgetHandler.getInstance().getText(shell); 
			
			closedShellsTitles.add(shellTitle);
			try {
				String canonicalName = null;
				if (target != null) {
					canonicalName = target.getClass().getCanonicalName();
				}
				new CaptureScreenshot().captureScreenshot(canonicalName + "-" + shellTitle);
			} catch (CaptureScreenshotException e) {
				e.printStackTrace();
			}
		}
		
		public List<String> getClosedShellsTitles(){
			return this.closedShellsTitles;
		}
	}

}
