package org.jboss.reddeer.eclipse.condition;

import java.util.ArrayList;
import java.util.List;

import org.jboss.reddeer.common.condition.WaitCondition;
import org.jboss.reddeer.eclipse.ui.problems.Problem;
import org.jboss.reddeer.eclipse.ui.problems.ProblemsView;
import org.jboss.reddeer.eclipse.ui.problems.ProblemsView.ProblemType;
import org.jboss.reddeer.eclipse.ui.problems.matcher.AbstractProblemMatcher;

/**
 * Wait condition expects existence of specific amount of problems in Problems view.
 * 
 * @author mlabuda@redhat.com
 */
public class ExactNumberOfProblemsExists implements WaitCondition {
	
	private AbstractProblemMatcher[] problemMatchers;
	private ProblemType problemType;
	private int expectedProblemsCount;
	
	private ProblemsView problemsView;
	
	private List<Problem> problems = new ArrayList<Problem>();
	
	/**
	 * Constructs the condition for the specified problem type and
	 * the specified count of the problems.
	 * 
	 * @param type type of the problems
	 * @param count number of the problems
	 */
	public ExactNumberOfProblemsExists(ProblemType type, int count) {
		this(type, count, new AbstractProblemMatcher[]{});
	}

	/**
	 * Constructs the condition for the specified problem type and
	 * the specified count of the problems matching specified matchers.
	 * 
	 * @param type type of the problems
	 * @param count number of the problems
	 * @param matchers problem matchers
	 */
	public ExactNumberOfProblemsExists(ProblemType type, int count, AbstractProblemMatcher... matchers) {
		problemType = type;
		expectedProblemsCount = count;
		problemMatchers = matchers;
		
		problemsView = new ProblemsView();
		problemsView.open();
	}

	@Override
	public boolean test() {
		problemsView.activate();

		problems = problemsView.getProblems(problemType, problemMatchers);
		
		return problems.size() == expectedProblemsCount;
	}

	@Override
	public String description() {
		return "number of problems in Problems view is " + problems.size() + ".\n"
				+ "Expected number of problems is: " + expectedProblemsCount;
	}
}
