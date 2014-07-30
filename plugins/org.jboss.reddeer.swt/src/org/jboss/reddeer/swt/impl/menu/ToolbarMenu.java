package org.jboss.reddeer.swt.impl.menu;

import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.swt.widgets.MenuItem;
import org.hamcrest.Matcher;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.swt.api.Menu;
import org.jboss.reddeer.swt.handler.ActionContributionItemHandler;
import org.jboss.reddeer.swt.lookup.MenuLookup;
import org.jboss.reddeer.swt.matcher.WithMnemonicTextMatchers;

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
	
	public ToolbarMenu(Matcher<String>... matchers){
		MenuLookup l = new MenuLookup();
		item = l.lookFor(l.getToolbarMenus(), matchers);
		this.matchers = matchers;
	}

	@Override
	public void select() {
		log.info("Select toolbar menu with text " + getText());
		MenuLookup l = new MenuLookup();
		l.select(item);
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
