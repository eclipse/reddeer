package org.jboss.reddeer.swt.test.impl.menu;

import static org.junit.Assert.*;

import org.jboss.reddeer.common.condition.AbstractWaitCondition;
import org.jboss.reddeer.common.condition.WaitCondition;
import org.jboss.reddeer.common.wait.WaitUntil;
import org.jboss.reddeer.common.wait.WaitWhile;
import org.jboss.reddeer.core.condition.ProgressInformationShellIsActive;
import org.jboss.reddeer.core.condition.ShellWithTextIsAvailable;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.jboss.reddeer.swt.impl.button.PushButton;
import org.jboss.reddeer.swt.impl.menu.ShellMenu;
import org.jboss.reddeer.swt.impl.menu.ViewMenu;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.swt.impl.tree.DefaultTreeItem;
import org.jboss.reddeer.swt.test.handler.ParameterizedHandler;
import org.jboss.reddeer.swt.test.handler.ViewActionWithId;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
public class ViewMenuTest {
	
	@Test
	public void testErrorLogMenu(){
		new ShellMenu("Window","Show View","Other...").select();
		new DefaultTreeItem("General","Error Log").select();
		new PushButton("OK").click();
		new ViewMenu("View Menu","Filters...").select();
		new DefaultShell("Log Filters");
		new PushButton("OK").click();
		new WaitWhile(new ShellWithTextIsAvailable("Log Filters"));
		new WaitWhile(new ProgressInformationShellIsActive());
	}
	
	@Test
	public void parameterizedViewMenuItemTest(){
		//open View
		new ShellMenu("Window","Show View","Other...").select();
		new DefaultTreeItem("RedDeer SWT","RedDeer SWT").select();
		new PushButton("OK").click();
		
		//click menu item A
		new ViewMenu("submenu", "parameterizedMenuA").select();
		assertTrue(ParameterizedHandler.isToggledA());
		assertFalse(ParameterizedHandler.isToggledB());
		
		//click menu item B
		new ViewMenu("submenu", "parameterizedMenuB").select();
		assertTrue(ParameterizedHandler.isToggledA());
		assertTrue(ParameterizedHandler.isToggledB());
	}

	@Test
	public void actionWithIdViewMenuTest() {
		// open View
		new ShellMenu("Window", "Show View", "Other...").select();
		new DefaultTreeItem("RedDeer SWT", "RedDeer SWT").select();
		new PushButton("OK").click();
		
		// click Action With Id Menu
		assertFalse(ViewActionWithId.isToggled());
		new ViewMenu("View Action with ID").select();
		new WaitUntil(new ViewWithActionIdIsToggled());
		assertTrue(ViewActionWithId.isToggled());
	}

	private class ViewWithActionIdIsToggled extends AbstractWaitCondition{

		@Override
		public boolean test() {
			return ViewActionWithId.isToggled();
		}

		@Override
		public String description() {
			return "ViewWithActionIsToggled.";
		}
		
	}
	
}
