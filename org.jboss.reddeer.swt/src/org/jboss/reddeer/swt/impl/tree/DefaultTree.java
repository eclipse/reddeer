package org.jboss.reddeer.swt.impl.tree;

import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.jboss.reddeer.swt.api.Tree;
import org.jboss.reddeer.swt.exception.WidgetNotAvailableException;
import org.jboss.reddeer.swt.util.Bot;

/**
 * First available Tree implementation
 * @author Jiri Peterka
 *
 */
public class DefaultTree extends BasicTree implements Tree {

	public DefaultTree() {
		try {
			tree = Bot.get().tree();
		} catch (WidgetNotFoundException e) {
			throw new WidgetNotAvailableException("No tree is available");
		}
	}
}
