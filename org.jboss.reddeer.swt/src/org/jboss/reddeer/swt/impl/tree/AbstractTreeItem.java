package org.jboss.reddeer.swt.impl.tree;

import org.apache.log4j.Logger;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.jboss.reddeer.swt.api.TreeItem;

public abstract class AbstractTreeItem implements TreeItem {
	protected final Logger logger = Logger.getLogger(this.getClass());
	SWTBotTree tree;
	SWTBotTreeItem item;
	
	@Override
	public void select() {
		item.select();
	}
	
	@Override
	public String getText() {
		String text = item.getText();
		return text;
	}
	
	@Override
	public String getToolTipText() {
		String toolTipText = item.getToolTipText();
		return toolTipText;
	}
}
