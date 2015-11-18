package org.jboss.reddeer.eclipse.condition;

import org.hamcrest.Matcher;
import org.hamcrest.core.IsEqual;
import org.jboss.reddeer.common.condition.AbstractWaitCondition;
import org.jboss.reddeer.eclipse.ui.console.ConsoleView;

/**
 * Returns true if the console label matches a given String matcher
 * or given text
 * 
 * @author vlado pakan
 * 
 */
public class ConsoleHasLabel extends AbstractWaitCondition {

	private Matcher<String> matcher;
	private static ConsoleView consoleView = null;

	/**
	 * Construct the condition with a given text.
	 * 
	 * @param text Text
	 */
	public ConsoleHasLabel(String text) {
		this.matcher = new IsEqual<String>(text);
	}
	
	/**
	 * Instantiates a new console has label.
	 *
	 * @param matcher the matcher
	 */
	public ConsoleHasLabel(Matcher<String> matcher) {
		this.matcher = matcher;
	}
	

	/* (non-Javadoc)
	 * @see org.jboss.reddeer.common.condition.WaitCondition#test()
	 */
	@Override
	public boolean test() {
		String consoleLabel = ConsoleHasLabel.getConsoleLabel();
		return matcher.matches(consoleLabel);
	}

	/* (non-Javadoc)
	 * @see org.jboss.reddeer.common.condition.AbstractWaitCondition#description()
	 */
	@Override
	public String description() {
		return "console label matches '" + matcher ;
	}

	private static String getConsoleLabel() {
		if (consoleView == null){
			consoleView = new ConsoleView();
		}
		consoleView.open();
		return consoleView.getConsoleLabel();
	}

}
