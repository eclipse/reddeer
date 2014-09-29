package org.jboss.reddeer.swt.matcher;

import org.eclipse.swt.widgets.TreeItem;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsEqual;
import org.jboss.reddeer.swt.handler.TreeItemHandler;

/**
 * Matches {@link TreeItem} with specified text on specified index. 
 * 
 * @author Lucia Jelinkova
 *
 */
public class TreeItemTextMatcher extends TypeSafeMatcher<TreeItem> {

	private Matcher<String> expectedTextMatcher;
	
	private int index = 0;
	
	public TreeItemTextMatcher(String expectedText) {
		this(expectedText, 0);
	}
	
	public TreeItemTextMatcher(String expectedText, int index) {
		this(new IsEqual<String>(expectedText), index);
	}
	
	public TreeItemTextMatcher(Matcher<String> expectedText) {
		this(expectedText, 0);
	}
	
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
