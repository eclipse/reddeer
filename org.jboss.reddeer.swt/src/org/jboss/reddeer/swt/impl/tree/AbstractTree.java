package org.jboss.reddeer.swt.impl.tree;

import org.apache.log4j.Logger;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.jboss.reddeer.swt.api.Tree;

public class AbstractTree implements Tree {

	protected final Logger logger = Logger.getLogger(this.getClass());
	protected SWTBotTree tree;

	protected SWTBotTree getSWTBotTree(){
		return tree;
	}
}
