package org.jboss.reddeer.eclipse.test.ui.console;

import static org.junit.Assert.assertThat;

import org.hamcrest.core.IsNull;
import org.jboss.reddeer.eclipse.ui.console.ConsoleView;
import org.junit.Test;

public class ConsoleViewTest {

	private ConsoleView consoleView;
	
	@Test
	public void open() {
		consoleView = new ConsoleView();
		consoleView.open();
	}
	
	@Test
	public void getText() {
		String text = new ConsoleView().getConsoleText();
		assertThat(text, IsNull.nullValue());
	}
	
	@Test
	public void clearText() {
		new ConsoleView().clearConsole();
	}
}
