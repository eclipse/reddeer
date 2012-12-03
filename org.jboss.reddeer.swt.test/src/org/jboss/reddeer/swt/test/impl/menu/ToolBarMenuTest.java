package org.jboss.reddeer.swt.test.impl.menu;

import org.jboss.reddeer.swt.condition.ShellWithTextIsActive;
import org.jboss.reddeer.swt.impl.button.PushButton;
import org.jboss.reddeer.swt.impl.menu.ShellMenu;
import org.jboss.reddeer.swt.impl.menu.ToolbarMenu;
import org.jboss.reddeer.swt.impl.tree.ShellTreeItem;
import org.jboss.reddeer.swt.wait.TimePeriod;
import org.jboss.reddeer.swt.wait.WaitUntil;
import org.junit.Test;

public class ToolBarMenuTest {
	
	@Test
	public void testErrorLogMenu(){
		new ShellMenu("Window","Show View","Other...").select();
		new ShellTreeItem("General","Error Log").select();
		new PushButton("OK").click();
		new ToolbarMenu("View Menu","Filters...").select();
		new WaitUntil(new ShellWithTextIsActive("Log Filters"),TimePeriod.NORMAL);
		new PushButton("OK").click();

	}

}
