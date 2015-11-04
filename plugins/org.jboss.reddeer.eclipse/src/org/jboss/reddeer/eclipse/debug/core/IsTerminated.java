package org.jboss.reddeer.eclipse.debug.core;

import org.jboss.reddeer.common.condition.WaitCondition;

/**
 * A wait condition which detects whether a debugging is terminated. It returns
 * true if the Terminate button is disabled.
 * 
 * @author Andrej Podhradsky
 *
 */
public class IsTerminated implements WaitCondition {

	private TerminateButton terminateButton;

	@Override
	public boolean test() {
		if (terminateButton == null) {
			terminateButton = new TerminateButton();
		}
		return !terminateButton.isEnabled();
	}

	@Override
	public String description() {
		return "Tool item with tooltip 'Terminate' is still enabled";
	}

}
