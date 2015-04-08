package org.jboss.reddeer.eclipse.ui.problems.matcher;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.core.IsEqual;
import org.jboss.reddeer.eclipse.ui.problems.ProblemsView.Column;
import org.jboss.reddeer.core.matcher.AbstractWidgetWithTextMatcher;

/**
 *
 * Abstract problem matcher is a parent matcher for a specific columns in Problems view.
 * Matching works either as precise match on a specified string or by passing proper matcher for matching.
 * 
 * @author mlabuda@redhat.com
 * @since 0.7
 *
 */
public abstract class AbstractProblemMatcher extends AbstractWidgetWithTextMatcher {

	protected Matcher<String> matcher;
	
	/**
	 * Creates a new problem matcher matching to whole text of a column.
	 * 
	 * @param text whole column text of a problem to match
	 */
	public AbstractProblemMatcher(String text) {
		this(new IsEqual<String>(text));
	}
	
	/**
	 * Creates a new problem matcher matching with matcher passed as argument.
	 * 
	 * @param matcher matcher to match column of a problem
	 */
	public AbstractProblemMatcher(Matcher<String> matcher) {
		if (matcher == null) {
			throw new IllegalArgumentException("Matcher cannot be null.");
		}
		this.matcher = matcher;
	}
	
	/**
	 * Gets label of a column of a specific problem.
	 * @return label of a column of a specific problem
	 */
	public abstract Column getColumn();
	
	@Override
	public void describeTo(Description description) {
		description.appendDescriptionOf(matcher);
	}

	@Override
	protected boolean matches(String text) {
		return matcher.matches(text);
	}
}
