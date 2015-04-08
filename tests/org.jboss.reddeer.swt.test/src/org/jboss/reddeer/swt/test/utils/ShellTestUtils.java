package org.jboss.reddeer.swt.test.utils;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Shell;
import org.jboss.reddeer.core.condition.ShellWithTextIsActive;
import org.jboss.reddeer.common.wait.WaitWhile;

public class ShellTestUtils {

	public static Shell createShell(String title){
		Shell shell = new Shell(org.eclipse.swt.widgets.Display.getDefault());
		shell.setText(title);
		shell.setLayout(new RowLayout(SWT.VERTICAL));
		shell.setSize(500, 500);
		shell.open();
		shell.setFocus();
		return shell;
	}

	public static void closeShell(String title){
		for (Shell shell : org.jboss.reddeer.core.util.Display.getDisplay().getShells()) {
			if (shell.getText().equals(title)) {
				shell.dispose();
				break;
			}
		}
		new WaitWhile(new ShellWithTextIsActive(title));
	}
}
