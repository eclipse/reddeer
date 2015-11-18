package org.jboss.reddeer.eclipse.ui.problems.matcher;

import org.hamcrest.Matcher;
import org.jboss.reddeer.eclipse.ui.problems.ProblemsView.Column;

/**
 * Problem matcher for column Location of a problem.
 * 
 * @author mlabuda@redhat.com
 * @since 0.7
 */
public class ProblemsLocationMatcher extends AbstractProblemMatcher {

	/**
	 * Creates a new problem matcher matching to whole text of Location column.
	 * 
	 * @param text whole Location column text of a problem to match
	 */
	public ProblemsLocationMatcher(String text) {
		super(text);
	}
	
	/**
	 * Creates a new problem matcher matching with matcher for Location column.
	 * 
	 * @param matcher matcher to match Location column of a problem
	 */
	public ProblemsLocationMatcher(Matcher<String> matcher) {
		super(matcher);
	}
	
	/* (non-Javadoc)
	 * @see org.jboss.reddeer.eclipse.ui.problems.matcher.AbstractProblemMatcher#getColumn()
	 */
	@Override
	public Column getColumn() {
		return Column.LOCATION;
	}
}
