package org.jboss.reddeer.eclipse.test.ui.console;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;

import org.jboss.reddeer.eclipse.ui.console.ConsoleView;
import org.jboss.reddeer.workbench.view.ActiveView;
import org.junit.Test;

public class ConsoleViewTest {

	private ConsoleView consoleView;
	
	@Test
	public void open() {
		consoleView = new ConsoleView();
		consoleView.open();
		
		assertThat(new ActiveView().getText(), is(consoleView.getText()));
	}
	/**
	 * TODO test Console View specific operations
	 */
}
