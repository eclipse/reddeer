package org.jboss.reddeer.eclipse.condition;

import org.jboss.reddeer.eclipse.ui.console.ConsoleView;
import org.jboss.reddeer.common.condition.WaitCondition;

/**
 * Returns true if the console is displaying results from
 * launch process 
 * 
 * @author vlado pakan
 * 
 */
public class ConsoleHasLaunch implements WaitCondition {

	private ConsoleView consoleView = new ConsoleView();

	/**
	 * Construct the condition.
	 * 
	 * @param text Text
	 */
	public ConsoleHasLaunch() {
	}

	@Override
	public boolean test() {
		consoleView.open();
		return consoleView.consoleHasLaunch();
	}

	@Override
	public String description() {
		return "console contains launch";
	}

}
