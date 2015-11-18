package org.jboss.reddeer.eclipse.ui.problems.matcher;

import org.hamcrest.Matcher;
import org.jboss.reddeer.eclipse.ui.problems.ProblemsView.Column;

/**
 * Problem matcher for column Path of a problem.
 * 
 * @author mlabuda@redhat.com
 * @since 0.7
 */
public class ProblemsPathMatcher extends AbstractProblemMatcher {
	
	/**
	 * Creates a new problem matcher matching to whole text of Path column.
	 * 
	 * @param text whole Path column text of a problem to match
	 */
	public ProblemsPathMatcher(String text) {
		super(text);
	}
	
	/**
	 * Creates a new problem matcher matching with matcher for Path column.
	 * 
	 * @param matcher matcher to match Path column of a problem
	 */
	public ProblemsPathMatcher(Matcher<String> matcher) {
		super(matcher);
	}
	
	/* (non-Javadoc)
	 * @see org.jboss.reddeer.eclipse.ui.problems.matcher.AbstractProblemMatcher#getColumn()
	 */
	@Override
	public Column getColumn() {
		return Column.PATH;
	}
}
