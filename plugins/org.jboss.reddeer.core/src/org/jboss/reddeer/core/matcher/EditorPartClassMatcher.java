package org.jboss.reddeer.core.matcher;

import org.eclipse.ui.IEditorPart;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

/**
 * Matcher matching specified descendant class of {@link IEditorPart} to class of specified object of 
 * {@link EditorPartClassMatcher#matches(Object)} method. 
 * 
 * @author Lucia Jelinkova
 *
 */
public class EditorPartClassMatcher extends TypeSafeMatcher<IEditorPart> {

	private Class<? extends IEditorPart> expectedClass;
	
	/**
	 * Creates new EditorPartClassMatcher matching classes of editor parts.
	 * 
	 * @param expectedClass expected superclass of matching
	 */
	public EditorPartClassMatcher(Class<? extends IEditorPart> expectedClass) {
		this.expectedClass = expectedClass;
	}
	
	/* (non-Javadoc)
	 * @see org.hamcrest.TypeSafeMatcher#matchesSafely(java.lang.Object)
	 */
	@Override
	protected boolean matchesSafely(IEditorPart item) {
		return expectedClass.isAssignableFrom(item.getClass());
	}

	/* (non-Javadoc)
	 * @see org.hamcrest.SelfDescribing#describeTo(org.hamcrest.Description)
	 */
	@Override
	public void describeTo(Description description) {
		description.appendText("EditorPart is a subclass of " + expectedClass);
	}
}
