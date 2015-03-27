package org.jboss.reddeer.eclipse.condition;

import org.jboss.reddeer.eclipse.ui.problems.ProblemsView;
import org.jboss.reddeer.eclipse.ui.problems.ProblemsView.ProblemType;
import org.jboss.reddeer.eclipse.ui.problems.matcher.AbstractProblemMatcher;
import org.jboss.reddeer.common.condition.WaitCondition;

/**
 * Wait condition for existence of a problem. Condition wait for existence of a specific problem or problem type. 
 * If such problem or problem type is available in Problems view. condition is met.
 * @author mlabuda@redhat.com
 * @since 0.7
 */
public class ProblemExists implements WaitCondition {

	private ProblemType problemType;
	private AbstractProblemMatcher[] matchers;
	private ProblemsView problemsView;
	
	/**
	 * Creates a new ProblemExists condition. If provided array of matchers is empty the wait condition is 
	 * met once there is a problem of specified problem type. If there are any matchers, a problem of 
	 * specified problem type has to match to those matchers.
	 * 
	 * @param problemType type of a problem (warning or error)
	 * @param matchers matchers for a problem
	 */
	public ProblemExists(ProblemType problemType, AbstractProblemMatcher... matchers) {
		this.problemType = problemType;
		this.matchers = matchers;
		problemsView = new ProblemsView();
		problemsView.open();
	}
	
	@Override
	public boolean test() {
		problemsView.activate();
		return !problemsView.getProblems(problemType, matchers).isEmpty();
	}

	@Override
	public String description() {
		return " problem of type " + problemType + " exists in Problems view.";
	}

}
