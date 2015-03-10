package org.jboss.reddeer.eclipse.ui.problems.matcher;

import org.hamcrest.Matcher;
import org.jboss.reddeer.eclipse.ui.problems.ProblemsView.Column;

/**
 * Problem matcher for column Creation Time of a problem.
 * 
 * @author mlabuda@redhat.com
 * @since 0.7
 */
public class ProblemsCreationTimeMatcher extends AbstractProblemMatcher {

	/**
	 * Creates a new problem matcher matching to whole text of Creation Time column.
	 * 
	 * @param text whole Creation Time column text of a problem to match
	 */
	public ProblemsCreationTimeMatcher(String text) {
		super(text);
	}
	
	/**
	 * Creates a new problem matcher matching with matcher for Creation Time column.
	 * 
	 * @param matcher matcher to match Creation Time column of a problem
	 */
	public ProblemsCreationTimeMatcher(Matcher<String> matcher) {
		super(matcher);
	}
	
	@Override
	public Column getColumn() {
		return Column.CREATION_TIME;
	}
}
