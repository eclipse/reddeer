package org.jboss.reddeer.eclipse.debug.core;

import org.jboss.reddeer.common.condition.WaitCondition;
import org.jboss.reddeer.common.wait.AbstractWait;
import org.jboss.reddeer.common.wait.TimePeriod;

/**
 * A wait condition which detects whether a debugging is suspended. It returns
 * true if the Resume button is enabled.
 * 
 * @author Andrej Podhradsky
 *
 */
public class IsSuspended implements WaitCondition {

	private ResumeButton resumeButton;

	@Override
	public boolean test() {
		if (resumeButton == null) {
			resumeButton = new ResumeButton();
		}
		if (resumeButton.isEnabled()) {
			AbstractWait.sleep(TimePeriod.SHORT);
			return true;
		}
		return false;
	}

	@Override
	public String description() {
		return "Debugger didn't suspend";
	}

}
