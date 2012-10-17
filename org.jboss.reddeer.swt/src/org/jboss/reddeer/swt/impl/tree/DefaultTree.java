package org.jboss.reddeer.swt.impl.tree;

import static org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory.allOf;
import static org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory.widgetOfType;

import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.hamcrest.Matcher;
import org.jboss.reddeer.swt.exception.SWTLayerException;
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
			throw new SWTLayerException("No matching tree available");
		}
	}

	public DefaultTree(int index){
		try {
			tree = Bot.get().tree(index);
		} catch (WidgetNotFoundException e){
			throw new SWTLayerException("No matching tree available");
		}
		tree.setFocus();
	}
	
	/**
	 * Tree with given index in given Group
	 * @param index of tree
	 * @param inGroup in group
	 */
	public DefaultTree(String inGroup, int index){
		tree = Bot.get().treeInGroup(inGroup, index);
	}
}
