package org.jboss.reddeer.core.matcher;

import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.part.EditorPart;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

/**
 * Matcher checking if the {@link EditorPart} can be assigned to 
 * the specified class. 
 * 
 * @author Lucia Jelinkova
 *
 */
public class EditorPartClassMatcher extends TypeSafeMatcher<IEditorPart> {

	private Class<? extends IEditorPart> expectedClass;
	
	public EditorPartClassMatcher(Class<? extends IEditorPart> expectedClass) {
		this.expectedClass = expectedClass;
	}
	
	@Override
	protected boolean matchesSafely(IEditorPart item) {
		return expectedClass.isAssignableFrom(item.getClass());
	}

	@Override
	public void describeTo(Description description) {
		description.appendText("EditorPart is a subclass of " + expectedClass);
	}
}
