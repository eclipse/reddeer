package org.jboss.reddeer.swt.test.impl.menu;

import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.jboss.reddeer.core.condition.ProgressInformationShellIsActive;
import org.jboss.reddeer.core.condition.ShellWithTextIsAvailable;
import org.jboss.reddeer.swt.impl.button.PushButton;
import org.jboss.reddeer.swt.impl.menu.ShellMenu;
import org.jboss.reddeer.swt.impl.menu.ToolbarMenu;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.swt.impl.tree.DefaultTreeItem;
import org.jboss.reddeer.common.wait.WaitWhile;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
public class ToolBarMenuTest {
	
	@Test
	public void testErrorLogMenu(){
		new ShellMenu("Window","Show View","Other...").select();
		new DefaultTreeItem("General","Error Log").select();
		new PushButton("OK").click();
		new ToolbarMenu("View Menu","Filters...").select();
		new DefaultShell("Log Filters");
		new PushButton("OK").click();
		new WaitWhile(new ShellWithTextIsAvailable("Log Filters"));
		new WaitWhile(new ProgressInformationShellIsActive());
	}

}
