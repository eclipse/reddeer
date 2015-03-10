package org.jboss.reddeer.eclipse.ui.problems.matcher;

import org.hamcrest.Matcher;
import org.jboss.reddeer.eclipse.ui.problems.ProblemsView.Column;

/**
 * Problem matcher for column ID of a problem.
 * 
 * @author mlabuda@redhat.com
 * @since 0.7
 */
public class ProblemsIDMatcher extends AbstractProblemMatcher {

	/**
	 * Creates a new problem matcher matching to whole text of ID column.
	 * 
	 * @param text whole ID column text of a problem to match
	 */
	public ProblemsIDMatcher(String text) {
		super(text);
	}
	
	/**
	 * Creates a new problem matcher matching with matcher for ID column.
	 * 
	 * @param matcher matcher to match ID column of a problem
	 */
	public ProblemsIDMatcher(Matcher<String> matcher) {
		super(matcher);
	}

	@Override
	public Column getColumn() {
		return Column.ID;
	}
}
