package org.jboss.reddeer.eclipse.condition;

import org.jboss.reddeer.eclipse.ui.console.ConsoleView;
import org.jboss.reddeer.common.condition.WaitCondition;

/**
 * Returns true if console is terminated
 * 
 * @author vlado pakan
 * 
 */
public class ConsoleIsTerminated implements WaitCondition {

	private ConsoleView consoleView = new ConsoleView();

	/**
	 * Construct the condition.
	 * 
	 * @param text Text
	 */
	public ConsoleIsTerminated() {
	}

	@Override
	public boolean test() {
		consoleView.open();
		return consoleView.consoleIsTerminated();
	}

	@Override
	public String description() {
		return "console is terminated";
	}

}
