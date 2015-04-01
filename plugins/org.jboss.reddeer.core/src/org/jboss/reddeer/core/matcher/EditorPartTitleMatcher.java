package org.jboss.reddeer.core.matcher;


import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.part.EditorPart;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

/**
 * Matcher checking if the {@link EditorPart} name, title 
 * or tooltip matches the specified matcher.
 * 
 * @author Lucia Jelinkova
 */
public class EditorPartTitleMatcher extends TypeSafeMatcher<IEditorPart> {

	private Matcher<String> titleMatcher;
	
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
