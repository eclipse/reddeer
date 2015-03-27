package org.jboss.reddeer.swt.impl.tree;

import java.util.List;

import org.eclipse.swt.widgets.TreeItem;
import org.hamcrest.Matcher;
import org.jboss.reddeer.common.logging.LoggingUtils;
import org.jboss.reddeer.core.exception.SWTLayerException;
import org.jboss.reddeer.swt.handler.TreeItemHandler;

/**
 * Exception thrown when the requested tree item cannot be found. 
 * 
 * @author Lucia Jelinkova
 *
 */
public class TreeItemNotFoundException extends SWTLayerException {
	
	private static final long serialVersionUID = 1L;

	public TreeItemNotFoundException(List<TreeItem> foundItems, int requestedIndex, Matcher<TreeItem>...matchers) {
		super("Specified index (" + requestedIndex + ") is bigger or equal as the number of found items (" + foundItems.size() + ")");
		addMessageDetail(getMatchersPart(matchers));
	}
	
	public TreeItemNotFoundException(List<TreeItem> currentLevel, Matcher<TreeItem> currentMatcher, Matcher<TreeItem>...matchers){
		super("No tree item found. Please see exception details");
		addMessageDetail(getCurrentMatcherPart(currentMatcher));
		addMessageDetail(getCurrentLevelPart(currentLevel));
		addMessageDetail(getMatchersPart(matchers));
	}
	
	private String getCurrentMatcherPart(Matcher<TreeItem> currentMatcher) {
		return "Current level matcher: " + currentMatcher;
	}

	private String getCurrentLevelPart(List<TreeItem> currentLevel) {
		StringBuilder builder = new StringBuilder("Current level tree items: ");
		Object[] items = new Object[currentLevel.size()];
		for (int i = 0; i < items.length; i++){
			items[i] = TreeItemHandler.getInstance().getText(currentLevel.get(i), 0);
		}
		builder.append(LoggingUtils.format(items));
		return builder.toString();
	}

	private String getMatchersPart(Matcher<TreeItem>[] matchers) {
		StringBuilder builder = new StringBuilder("Tree item path matchers: ");
		builder.append(LoggingUtils.format(matchers));
		return builder.toString();
	}
}