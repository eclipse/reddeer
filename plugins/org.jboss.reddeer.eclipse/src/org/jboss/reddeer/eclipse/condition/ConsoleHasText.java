package org.jboss.reddeer.eclipse.condition;

import org.jboss.reddeer.common.condition.AbstractWaitCondition;
import org.jboss.reddeer.eclipse.ui.console.ConsoleView;

/**
 * Returns true if the console contains a given text
 * 
 * @author apodhrad
 * 
 */
public class ConsoleHasText extends AbstractWaitCondition {

	private String text;

	/**
	 * Construct the condition with a given text.
	 * 
	 * @param text Text
	 */
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
