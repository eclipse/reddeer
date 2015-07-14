package org.jboss.reddeer.junit.extension.after.test.impl;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.widgets.Shell;
import org.jboss.reddeer.common.properties.RedDeerProperties;
import org.jboss.reddeer.junit.TestInfo;
import org.jboss.reddeer.junit.extensionpoint.IAfterTest;
import org.jboss.reddeer.junit.screenshot.CaptureScreenshotException;
import org.jboss.reddeer.junit.screenshot.ScreenshotCapturer;
import org.jboss.reddeer.core.handler.IBeforeShellIsClosed;
import org.jboss.reddeer.core.handler.ShellHandler;
import org.jboss.reddeer.core.handler.WidgetHandler;
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

	public static final boolean CLOSE_ALL_SHELLS = RedDeerProperties.CLOSE_ALL_SHELLS.getBooleanValue();

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
				String fileName;
				String config = null;
				if (target == null) {
        			fileName = "UnknownTestClass@CloseAllShellsExt_closing" + shellTitle;
        		} if (target instanceof TestInfo){
        			TestInfo testInfo = (TestInfo)target;
        			config = testInfo.getConfig();
        			fileName = ScreenshotCapturer.getScreenshotFileName(
        				testInfo.getTestObjectClass(),
        				testInfo.getMethodName(),
        				"CloseAllShellsExt_closing_" + shellTitle);
				}else {
        			fileName = ScreenshotCapturer.getScreenshotFileName(
        				target.getClass(),
        				null,
        				"CloseAllShellsExt_closing_" + shellTitle);
        		}
        		ScreenshotCapturer.getInstance().captureScreenshotOnFailure(config, fileName);
			} catch (CaptureScreenshotException e) {
				e.printStackTrace();
			}
		}
		
		public List<String> getClosedShellsTitles(){
			return this.closedShellsTitles;
		}
	}

}
