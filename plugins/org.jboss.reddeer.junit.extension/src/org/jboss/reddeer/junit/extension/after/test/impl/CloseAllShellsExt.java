package org.jboss.reddeer.junit.extension.after.test.impl;

import org.jboss.reddeer.junit.extensionpoint.IAfterTest;
import org.jboss.reddeer.swt.impl.shell.WorkbenchShell;

/**
 * 
 * @author Andrej Podhradsky
 *
 */
public class CloseAllShellsExt implements IAfterTest {

	@Override
	public void runAfterTest() {
		new WorkbenchShell().closeAllShells();
	}

	@Override
	public boolean hasToRun() {
		return true;
	}

}
