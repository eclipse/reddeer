package org.jboss.reddeer.eclipse.condition;

import org.jboss.reddeer.eclipse.ui.console.ConsoleView;
import org.jboss.reddeer.swt.condition.WaitCondition;

/**
 * Returns true if the console contains a given text
 * 
 * @author apodhrad
 * 
 */
public class ConsoleHasText implements WaitCondition {

	private String text;

	public ConsoleHasText(String text) {
		this.text = text;
	}

	@Override
	public boolean test() {
		String consoleText = getConsoleText();
		return consoleText.contains(text);
	}

	@Override
	public String description() {
		String consoleText = getConsoleText();
		return "console contains '" + text + "'\n" + consoleText;
	}

	private static String getConsoleText() {
		ConsoleView consoleView = new ConsoleView();
		consoleView.open();
		return consoleView.getConsoleText();
	}

}
