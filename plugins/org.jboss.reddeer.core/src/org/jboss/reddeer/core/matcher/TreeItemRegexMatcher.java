package org.jboss.reddeer.core.matcher;

import org.eclipse.swt.widgets.TreeItem;
import org.jboss.reddeer.common.matcher.RegexMatcher;

/**
 * Matcher matching text of {@link TreeItem} with specified RegEx text on specified index. 
 * 
 * @author Lucia Jelinkova
 *
 */
public class TreeItemRegexMatcher extends TreeItemTextMatcher {

	/**
	 * Creates new TreeItemRegexMatcher matching text of {@link TreeItem} to RegEx 
	 * matcher matching with specified RegEx text on index 0.
	 * 
	 * @param expectedText RegEx text to match tree item
	 */
	public TreeItemRegexMatcher(String expectedText) {
		this(expectedText, 0);
	}
	
	/**
	 * Creates new TreeItemRegexMatcher matching text of {@link TreeItem} located at cell on specified index to
	 * RegEx matcher matching with specified RegEx text.
	 * 
	 * @param regex text of RegEx to match text of tree item
	 * @param index index of cell of text to match
	 */
	public TreeItemRegexMatcher(String regex, int index) {
		super(new RegexMatcher(regex), index);
	}
}
