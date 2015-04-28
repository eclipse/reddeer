package org.jboss.reddeer.core.matcher;


import org.eclipse.ui.IEditorPart;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

/**
 * Matcher matching name, title or tooltip of specified {@link IEditorPart}.
 * 
 * @author Lucia Jelinkova
 */
public class EditorPartTitleMatcher extends TypeSafeMatcher<IEditorPart> {

	private Matcher<String> titleMatcher;
	
	/**
	 * Creates new EditorPartTitleMatcher with specified string matcher.
	 * String matcher can match title, name or tooltip of {@link IEditorPart}.
	 * 
	 * @param titleMatcher matcher to match title, name or tooltip
	 */
	public EditorPartTitleMatcher(Matcher<String> titleMatcher) {
		this.titleMatcher = titleMatcher;
	}
	
	@Override
	protected boolean matchesSafely(IEditorPart item) {
		if (titleMatcher.matches(item.getTitle())) {
			return true;
		} 
		
		if (titleMatcher.matches(item.getEditorInput().getName())) {
			return true;
		} 
		
		if (titleMatcher.matches(item.getEditorInput().getToolTipText())) {
			return true;
		}
		
		return false;
	}

	@Override
	public void describeTo(Description description) {
		description.appendText("EditorPart title matches");
		description.appendDescriptionOf(titleMatcher);
	}
}
