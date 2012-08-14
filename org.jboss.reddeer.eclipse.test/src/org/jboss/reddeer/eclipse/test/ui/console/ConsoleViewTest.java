package org.jboss.reddeer.eclipse.test.ui.console;

import org.jboss.reddeer.eclipse.ui.console.ConsoleView;
import org.junit.Test;

public class ConsoleViewTest {

	private ConsoleView consoleView;
	
	@Test
	public void open() {
		consoleView = new ConsoleView();
		consoleView.open();

	}
	/**
	 * TODO test Console View specific operations
	 */
}
