package org.jboss.reddeer.core.matcher;

import org.eclipse.swt.widgets.TreeItem;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsEqual;
import org.jboss.reddeer.core.handler.TreeItemHandler;

/**
 * Matcher matching text of {@link TreeItem}. 
 * 
 * @author Lucia Jelinkova
 *
 */
public class TreeItemTextMatcher extends TypeSafeMatcher<TreeItem> {

	private Matcher<String> expectedTextMatcher;
	
	private int index = 0;
	
	/**
	 * Creates new TreeItemTextMatcher matching text of {@link TreeItem} located at cell with index 0 
	 * to specified text. 
	 * 
	 * @param expectedText text to match text of {@link TreeItem}
	 */
	public TreeItemTextMatcher(String expectedText) {
		this(expectedText, 0);
	}
	
	
	/**
	 * Creates new TreeItemTextMatcher matching text of {@link TreeItem} located at cell with specified index 
	 * to specified text.
	 * 
	 * @param expectedText text to match text of {@link TreeItem}
	 * @param index index of cell containing text to match
	 */
	public TreeItemTextMatcher(String expectedText, int index) {
		this(new IsEqual<String>(expectedText), index);
	}
	
	/**
	 * Creates new TreeItemTextMatcher matching text of {@link TreeItem} located at cell with index 0 
	 * to specified text matcher. 
	 * 
	 * @param expectedTextMatcher text matcher to match text of {@link TreeItem}
	 */
	public TreeItemTextMatcher(Matcher<String> expectedTextMatcher) {
		this(expectedTextMatcher, 0);
	}
	
	/**
	 * Creates new TreeItemTextMatcher matching text of {@link TreeItem} located at cell with specified index 
	 * to specified text matcher.
	 * 
	 * @param expectedTextMatcher text matcher to match text of {@link TreeItem}
	 * @param index index of cell containing text to match
	 */
	public TreeItemTextMatcher(Matcher<String> expectedTextMatcher, int index) {
		this.expectedTextMatcher = expectedTextMatcher;
		this.index = index;
	}
	
	@Override
	protected boolean matchesSafely(TreeItem item) {
		return expectedTextMatcher.matches(TreeItemHandler.getInstance().getText(item, index));
	}

	@Override
	public void describeTo(Description description) {
		description.appendText("tree item text on position ");
		description.appendValue(index);
		description.appendText(" matches ");
		description.appendDescriptionOf(expectedTextMatcher);
	}
}
