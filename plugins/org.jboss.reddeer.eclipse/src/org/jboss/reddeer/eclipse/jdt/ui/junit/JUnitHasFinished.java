package org.jboss.reddeer.eclipse.jdt.ui.junit;

import java.util.regex.Pattern;

import org.hamcrest.Matcher;
import org.hamcrest.core.StringContains;
import org.jboss.reddeer.common.condition.AbstractWaitCondition;
import org.jboss.reddeer.core.condition.JobIsRunning;

/**
 * Wait condition for detecting whether a JUnit run has finished.
 * 
 * An error may occurs if the run writes to the Console view. Make sure that you
 * have disabled activating the Console view.
 * 
 * @author apodhrad
 * 
 */
public class JUnitHasFinished extends AbstractWaitCondition {

	private JUnitView junitView;
	private JobIsRunning junitJobIsRunning;

	/**
	 * Construct the wait condition.
	 */
	public JUnitHasFinished() {
		junitView = new JUnitView();
		junitJobIsRunning = new JobIsRunning(new Matcher[] { StringContains.containsString("JUnit") }, null, false);
	}

	@Override
	public boolean test() {
		junitView.open();
		String status = junitView.getRunStatus();

		java.util.regex.Matcher statusMatcher = Pattern.compile("([0-9]+)/([0-9]+).*").matcher(status);
		if (statusMatcher.matches()) {
			int numberOfFinishedTests = Integer.valueOf(statusMatcher.group(1));
			int numberOfAllTests = Integer.valueOf(statusMatcher.group(2));
			if (numberOfFinishedTests > 0 && numberOfFinishedTests == numberOfAllTests) {
				return !junitJobIsRunning.test();
			}
		}

		return false;
	}

	@Override
	public String description() {
		return "JUnit test has not finished yet";
	}

}
