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

	@SuppressWarnings("unchecked")
	@Test
	public void testConsole() {
		
	    Matcher<String> textMatcher = StringEndsWith.endsWith("Java Stack Trace Console");

	    Matcher<String> textMatcher2 = StringEndsWith.endsWith("Maven Console");

	    // First run
	    ConsoleView console1 = new ConsoleView();
	    console1.open();
	    var menu = new ToolItemMenuItem(new DefaultToolItem(console1.getCTabItem().getFolder(), "Open Console"), textMatcher);
	    menu.select();
	    new WaitUntil(new ConsoleHasLabel(textMatcher));
	    new DefaultToolItem(console1.getCTabItem().getFolder(), "Close Console").click();
	    console1.close();

	    // Second run
	    ConsoleView console2 = new ConsoleView();
	    console2.open();
	    var menu2 = new ToolItemMenuItem(new DefaultToolItem(console2.getCTabItem().getFolder(), "Open Console"), textMatcher2);
	    menu2.select();
	    // we need to change condition to adjust to the new expected label
	    new WaitUntil(new ConsoleHasLabel(textMatcher2));
		
	}

	
}
