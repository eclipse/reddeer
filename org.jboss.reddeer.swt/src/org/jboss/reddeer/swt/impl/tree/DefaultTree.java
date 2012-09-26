package org.jboss.reddeer.swt.impl.tree;

import static org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory.allOf;
import static org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory.widgetOfType;

import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.hamcrest.Matcher;
import org.jboss.reddeer.swt.exception.WidgetNotAvailableException;
import org.jboss.reddeer.swt.util.Bot;

/**
 * Default tree implementation.
 * 
 * @author Jiri Peterka
 *
 */
public class DefaultTree extends AbstractTree {

	public DefaultTree() {
		tree = Bot.get().tree();
		tree.setFocus();
	}

	@SuppressWarnings("unchecked")
	public DefaultTree(Matcher<Widget> matcher){
		try {
			tree = new SWTBotTree((org.eclipse.swt.widgets.Tree) Bot.get().widget(allOf(widgetOfType(Tree.class), matcher)));
		} catch (WidgetNotFoundException e){
			throw new WidgetNotAvailableException("No matching tree available");
		}
	}
}
