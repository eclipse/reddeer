package org.eclipse.reddeer.eclipse.test.condition;

import org.eclipse.reddeer.common.wait.WaitUntil;
import org.eclipse.reddeer.eclipse.condition.ConsoleHasLabel;
import org.eclipse.reddeer.eclipse.ui.console.ConsoleView;
import org.eclipse.reddeer.junit.runner.RedDeerSuite;
import org.eclipse.reddeer.swt.impl.menu.ToolItemMenuItem;
import org.eclipse.reddeer.swt.impl.toolbar.DefaultToolItem;
import org.hamcrest.Matcher;
import org.hamcrest.core.StringEndsWith;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
public class ConsoleHasLabelTest {

	@Test
	public void testReinitiatingCondition() {
		Matcher<String> textMatcher = StringEndsWith.endsWith("Java Stack Trace Console");

		Matcher<String> textMatcher2 = StringEndsWith.endsWith("CVS");

		
		ConsoleHasLabel condition = new ConsoleHasLabel(textMatcher);
		// First run
		ConsoleView console1 = new ConsoleView();
		console1.open();
		var menu = new ToolItemMenuItem(new DefaultToolItem(console1.getCTabItem().getFolder(), "Open Console"), textMatcher);
		menu.select();
		new WaitUntil(condition);
		console1.close();

		// Second run
		ConsoleView console2 = new ConsoleView();
		console2.open();
		var menu2 = new ToolItemMenuItem(new DefaultToolItem(console2.getCTabItem().getFolder(), "Open Console"), textMatcher2);
		menu2.select();
		new WaitUntil(new ConsoleHasLabel(textMatcher2)); // Will fail because ConsoleHasLabel still refers to console1
	}

}
