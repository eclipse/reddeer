package org.jboss.reddeer.eclipse.ui.problems.matcher;

import org.hamcrest.Matcher;
import org.jboss.reddeer.eclipse.ui.problems.ProblemsView.Column;

/**
 * Problem matcher for column Resource of a problem.
 * 
 * @author mlabuda@redhat.com
 * @since 0.7
 */
public class ProblemsResourceMatcher extends AbstractProblemMatcher {
	
	/**
	 * Creates a new problem matcher matching to whole text of Resource column.
	 * 
	 * @param text whole Resource column text of a problem to match
	 */
	public ProblemsResourceMatcher(String text) {
		super(text);
	}
	
	/**
	 * Creates a new problem matcher matching with matcher for Resource column.
	 * 
	 * @param matcher matcher to match Resource column of a problem
	 */
	public ProblemsResourceMatcher(Matcher<String> matcher) {
		super(matcher);
	}
	
	/* (non-Javadoc)
	 * @see org.jboss.reddeer.eclipse.ui.problems.matcher.AbstractProblemMatcher#getColumn()
	 */
	@Override
	public Column getColumn() {
		return Column.RESOURCE;
	}
}
