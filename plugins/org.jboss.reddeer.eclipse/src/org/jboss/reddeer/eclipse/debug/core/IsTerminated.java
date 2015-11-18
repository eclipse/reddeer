package org.jboss.reddeer.eclipse.debug.core;

import org.jboss.reddeer.common.condition.AbstractWaitCondition;

/**
 * A wait condition which detects whether a debugging is terminated. It returns
 * true if the Terminate button is disabled.
 * 
 * @author Andrej Podhradsky
 *
 */
public class IsTerminated extends AbstractWaitCondition {

	private TerminateButton terminateButton;

	/* (non-Javadoc)
	 * @see org.jboss.reddeer.common.condition.WaitCondition#test()
	 */
	@Override
	public boolean test() {
		if (terminateButton == null) {
			terminateButton = new TerminateButton();
		}
		return !terminateButton.isEnabled();
	}

	/* (non-Javadoc)
	 * @see org.jboss.reddeer.common.condition.AbstractWaitCondition#description()
	 */
	@Override
	public String description() {
		return "Tool item with tooltip 'Terminate' is still enabled";
	}

}
