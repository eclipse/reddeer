package org.jboss.reddeer.swt.impl.menu;

import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.swt.widgets.MenuItem;
import org.hamcrest.Matcher;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.swt.api.Menu;
import org.jboss.reddeer.core.handler.ActionContributionItemHandler;
import org.jboss.reddeer.core.matcher.WithMnemonicTextMatchers;

/**
 * ToolbarMenu implementation
 * @author Rastislav Wagner
 *
 */
public class ToolbarMenu extends AbstractMenu implements Menu{
	
	private static final Logger log = Logger.getLogger(ToolbarMenu.class);
	
	private ActionContributionItem item;
	
	public ToolbarMenu(String... path){
		this(new WithMnemonicTextMatchers(path).getMatchers());
	}
	
	@SuppressWarnings("unchecked")
	public ToolbarMenu(Matcher<String>... matchers){
		item = ml.lookFor(ml.getToolbarMenus(), matchers);
		this.matchers = matchers;
	}

	@Override
	public void select() {
		log.info("Select toolbar menu with text " + getText());
		mh.select(item);
	}
	
	@Override
	public boolean isSelected() {
		return false;
	}

	@Override
	public String getText() {
		return item.getAction().getText().replace("&", "");
	}
	
	public MenuItem getSWTWidget(){
		return null;
	}
	
	@Override
	public boolean isEnabled() {
		return ActionContributionItemHandler.getInstance().isEnabled(item);
	}

	
}
