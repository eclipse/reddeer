package org.jboss.reddeer.swt.test;

import org.eclipse.swt.widgets.Shell;
import org.jboss.reddeer.core.util.Display;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.swt.test.utils.ShellTestUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
public abstract class SWTLayerTestCase {
	
	protected static final String SHELL_TITLE = "Testing shell";

	protected abstract void createControls(Shell shell);
	
	@Before
	public void setUp() {
		Display.syncExec(new Runnable() {
			@Override
			public void run() {
				Shell shell = ShellTestUtils.createShell(SHELL_TITLE);
				createControls(shell);
				shell.layout();
			}
		});
		new DefaultShell(SHELL_TITLE);
	}
	
	@After
	public void cleanup() {
		Display.syncExec(new Runnable() {
			@Override
			public void run() {
				ShellTestUtils.closeShell(SHELL_TITLE);
			}
		});
	}
}
