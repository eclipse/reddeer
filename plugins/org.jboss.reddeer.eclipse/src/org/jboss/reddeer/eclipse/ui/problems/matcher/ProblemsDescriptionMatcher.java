package org.jboss.reddeer.eclipse.ui.problems.matcher;

import org.hamcrest.Matcher;
import org.jboss.reddeer.eclipse.ui.problems.ProblemsView.Column;

/**
 * Problem matcher for column Descripton of a problem.
 * 
 * @author mlabuda@redhat.com
 * @since 0.7
 */
public class ProblemsDescriptionMatcher extends AbstractProblemMatcher {

	/**
	 * Creates a new problem matcher matching to whole text of Description column.
	 * 
	 * @param text whole Description column text of a problem to match
	 */
	public ProblemsDescriptionMatcher(String text) {
		super(text);
	}
	
	/**
	 * Creates a new problem matcher matching with matcher for Description column.
	 * 
	 * @param matcher matcher to match Description column of a problem
	 */
	public ProblemsDescriptionMatcher(Matcher<String> matcher) {
		super(matcher);
	}
	
	@Override
	public Column getColumn() {
		return Column.DESCRIPTION;
	}
}
