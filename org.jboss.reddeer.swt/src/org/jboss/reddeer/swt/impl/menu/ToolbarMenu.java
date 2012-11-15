package org.jboss.reddeer.swt.impl.menu;
import org.eclipse.jface.action.ActionContributionItem;
import org.hamcrest.Matcher;
import org.jboss.reddeer.swt.api.Menu;
import org.jboss.reddeer.swt.lookup.impl.MenuLookup;
import org.jboss.reddeer.swt.matcher.WithMnemonicMatchers;

/**
 * ToolbarMenu implementation
 * @author Rastislav Wagner
 *
 */
public class ToolbarMenu extends AbstractMenu implements Menu{
	
	private ActionContributionItem item;
	
	public ToolbarMenu(String... path){
		this(new WithMnemonicMatchers(path).getMatchers());
	}
	
	public ToolbarMenu(Matcher<String>... matchers){
		MenuLookup l = new MenuLookup();
		item = l.lookFor(l.getToolbarMenus(), matchers);
		this.matchers = matchers;
	}

	@Override
	public void select() {
		MenuLookup l = new MenuLookup();
		l.select(item);
		
	}



	@Override
	public String getText() {
		return item.getAction().getText().replace("&", "");
	}

	
}
