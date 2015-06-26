package org.jboss.reddeer.core.test.condition;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.jboss.reddeer.core.condition.ViewWithTitleIsActive;
import org.jboss.reddeer.eclipse.jdt.ui.ProjectExplorer;
import org.jboss.reddeer.eclipse.ui.console.ConsoleView;
import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
@Ignore
public class ViewWithTitleIsActiveTest {

	private ConsoleView consoleView = new ConsoleView();
	private ProjectExplorer projectExplorer = new ProjectExplorer();
	
	private boolean consoleWasOpened;
	private boolean projectExplorerWasOpened;
	
	@Before
	public void openViews() {
		consoleWasOpened = consoleView.isOpened();
		consoleView.open();
		
		projectExplorerWasOpened = projectExplorer.isOpened();
		projectExplorer.open();
	}
	
	@After
	public void closeOpenedViews() {
		if (consoleWasOpened) {
			consoleView.close();
		}
		if (projectExplorerWasOpened) {
			projectExplorer.close();
		}
	}
	
	@Test(expected=UnsupportedOperationException.class)
	public void testConsoleView() {
		ViewWithTitleIsActive condition = new ViewWithTitleIsActive(consoleView.getTitle());
		consoleView.activate();
		assertTrue("Console view is active but condition failed.", condition.test());
		projectExplorer.activate();
		assertFalse("Console view is not active but condition passed", condition.test());
	}
}
