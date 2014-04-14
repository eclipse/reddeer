package org.jboss.reddeer.junit.extension.after.test.impl;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.widgets.Shell;
import org.jboss.reddeer.junit.extensionpoint.IAfterTest;
import org.jboss.reddeer.junit.internal.screenshot.CaptureScreenshot;
import org.jboss.reddeer.junit.internal.screenshot.CaptureScreenshotException;
import org.jboss.reddeer.swt.handler.WidgetHandler;
import org.jboss.reddeer.swt.impl.shell.WorkbenchShell;
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
			new WorkbenchShellExt().closeAllShells();
		}
	}

	/**
	 * See {@link IAfterTest}
	 */
	@Override
	public boolean hasToRun() {
		return CLOSE_ALL_SHELLS;
	}

	private class WorkbenchShellExt extends WorkbenchShell {

		private List<String> shellTitles;

		public WorkbenchShellExt() {
			super();
			shellTitles = new ArrayList<String>();
		}

		@Override
		public void closeAllShells() {
			super.closeAllShells();
			if (shellTitles.size() > 0) {
				Assert.fail("The following shells remained open " + shellTitles);
			}
		}

		@Override
		protected void beforeShellIsClosed(Shell shell) {
			super.beforeShellIsClosed(shell);
			String shellTitle = WidgetHandler.getInstance().getText(shell); 
			shellTitles.add(shellTitle);
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

	}

}
