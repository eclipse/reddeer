package org.jboss.reddeer.swt.impl.tree;

import static org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory.allOf;
import static org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory.widgetOfType;

import java.util.List;

import org.hamcrest.Matcher;
import org.jboss.reddeer.swt.api.Tree;
import org.jboss.reddeer.swt.api.TreeItem;
import org.jboss.reddeer.swt.lookup.impl.ShellLookup;

/**
 * Default tree implementation.
 * 
 * @author Jiri Peterka
 *
 */
public class ShellTree extends AbstractTree implements Tree {

	public ShellTree() {
		this(0);
	}
	
	@SuppressWarnings("unchecked")
	public ShellTree(int index) {
		this(allOf(widgetOfType(org.eclipse.swt.widgets.Tree.class)), index);
	}
	
	public ShellTree(Matcher<org.eclipse.swt.widgets.Tree> matcher) {
		this(matcher, 0);
	}
	
	public ShellTree(Matcher<org.eclipse.swt.widgets.Tree> matcher, int index) {
		super(matcher, new ShellLookup().getActiveShell(), index);
	}
	
	public List<TreeItem> getItems(){
		return super.getItems(true);
	}

}
