package org.jboss.reddeer.swt.test.impl.menu;

import static org.junit.Assert.assertEquals;

import org.jboss.reddeer.eclipse.ui.console.ConsoleView;
import org.jboss.reddeer.eclipse.ui.perspectives.JavaPerspective;
import org.jboss.reddeer.eclipse.ui.problems.ProblemsView;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.jboss.reddeer.requirements.openperspective.OpenPerspectiveRequirement.OpenPerspective;
import org.jboss.reddeer.swt.api.ToolItem;
import org.jboss.reddeer.swt.impl.menu.ToolItemMenu;
import org.jboss.reddeer.swt.impl.shell.WorkbenchShell;
import org.jboss.reddeer.swt.impl.toolbar.DefaultToolItem;
import org.jboss.reddeer.swt.impl.toolbar.ViewToolItem;
import org.jboss.reddeer.swt.matcher.RegexMatcher;
import org.jboss.reddeer.swt.matcher.WithTooltipTextMatcher;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
@OpenPerspective(JavaPerspective.class)
public class ToolItemMenuTest {

	@Test
	public void ViewToolItemMenuTest() {
		new ConsoleView().open();
		ToolItem item = new ViewToolItem("Open Console");
		ToolItemMenu menu = new ToolItemMenu(item, new RegexMatcher(
				".*Console View"));
		menu.select();
		new ProblemsView().open();
		menu.select();
	}

	@Test
	public void WorkbenchToolItemMultiLayeredMenuTest() {
		ToolItem ti = new DefaultToolItem(new WorkbenchShell(),
				new WithTooltipTextMatcher(new RegexMatcher("Run.*")));
		ToolItemMenu menu = new ToolItemMenu(ti, "Run As", "(none applicable)");
		assertEquals("(none applicable)", menu.getText());
	}
}