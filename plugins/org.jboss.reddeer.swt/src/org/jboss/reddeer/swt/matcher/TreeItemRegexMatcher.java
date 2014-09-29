package org.jboss.reddeer.swt.matcher;

import org.eclipse.swt.widgets.TreeItem;

/**
 * Matches text of {@link TreeItem} with specified regex on specified index. 
 * 
 * @author Lucia Jelinkova
 *
 */
public class TreeItemRegexMatcher extends TreeItemTextMatcher {

	public TreeItemRegexMatcher(String expectedText) {
		this(expectedText, 0);
	}
	
	public TreeItemRegexMatcher(String regex, int index) {
		super(new RegexMatcher(regex), index);
	}
}
