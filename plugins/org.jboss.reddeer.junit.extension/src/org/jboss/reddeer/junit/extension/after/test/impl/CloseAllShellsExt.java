package org.jboss.reddeer.junit.extension.after.test.impl;

import java.util.ArrayList;
import java.util.List;

import org.jboss.reddeer.junit.extensionpoint.IAfterTest;
import org.jboss.reddeer.junit.logging.Logger;
import org.jboss.reddeer.swt.handler.WidgetHandler;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.swt.impl.shell.WorkbenchShell;
import org.jboss.reddeer.swt.lookup.ShellLookup;
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

	public static final boolean CLOSE_ALL_SHELLS = System.getProperty(
			"reddeer.close.shells", "true").equalsIgnoreCase("true");

	private Logger log = Logger.getLogger(CloseAllShellsExt.class);

	/**
	 * See {@link IAfterTest}
	 */
	@Override
	public void runAfterTest() {
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

		@Override
		public void closeAllShells() {
			log.info("Closing all shells...");
			List<String> shellTitles = new ArrayList<String>();
			org.eclipse.swt.widgets.Shell[] shell = ShellLookup.getInstance()
					.getShells();
			for (int i = 0; i < shell.length; i++) {
				WidgetHandler widgetHandler = WidgetHandler.getInstance();
				if (shell[i] != null && shell[i] != swtShell) {
					if (widgetHandler.isVisible(shell[i])) {
						String shellTitle = widgetHandler.getText(shell[i]);
						shellTitles.add(shellTitle);
						new DefaultShell(shellTitle).close();
					}
				}
			}
			if (shellTitles.size() > 0) {
				Assert.fail("The following shells remained open " + shellTitles);
			}
		}

	}

}
