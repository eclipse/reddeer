package org.jboss.reddeer.swt.impl.menu;

import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.swt.widgets.MenuItem;
import org.hamcrest.Matcher;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.swt.api.Menu;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.swt.handler.ActionContributionItemHandler;
import org.jboss.reddeer.swt.handler.WidgetHandler;
import org.jboss.reddeer.swt.lookup.MenuLookup;
import org.jboss.reddeer.swt.matcher.WithMnemonicTextMatchers;
import org.jboss.reddeer.swt.util.Display;
import org.jboss.reddeer.swt.util.ResultRunnable;

/**
 * Context Menu implementation for all context menu related to some Control.
 * Control must have focus to provide context menu
 * 
 * @author Jiri Peterka
 * @author Rastislav Wagner
 * 
 */
public class ContextMenu extends AbstractMenu implements Menu {
	
	private static final Logger log = Logger.getLogger(ContextMenu.class);
	private ActionContributionItem item;
	private MenuItem menuItem;
	

	/**
	 * Context menu given by String path
	 * Uses WithMnemonicMatcher to match menu item label. It means that all ampersands
	 * and shortcuts within menu item label are ignored when searching for menu
	 * @param path
	 */
	public ContextMenu(String... path) {
		this(new WithMnemonicTextMatchers(path).getMatchers());		
	}
	
	/**
	 * Context menu given by matchers
	 * @param matchers
	 */
	public ContextMenu(Matcher<String>... matchers) {
		MenuLookup l = new MenuLookup();
		menuItem = l.lookFor(l.getTopMenuMenuItemsFromFocus(),matchers);
		if(menuItem == null){
			log.info("No menu item found, looking for contribution item");
			item = l.lookFor(l.getMenuContributionItems(), matchers);
			if (item == null){
				throw new SWTLayerException("Contribution item not found");
			}
		}
		this.matchers = matchers;
		
	}	
	
	@Override
	public void select() {
		log.info("Select context menu item with text " + getText());
		MenuLookup l = new MenuLookup();
		if(menuItem != null){
			l.select(menuItem);
		} else {
			l.select(item);
		}
	}
	
	@Override
	public boolean isSelected() {
		MenuLookup l = new MenuLookup();
		if(menuItem != null){
			return l.isSelected(menuItem);
		} else {
			return false;
		}
	}
	
	
	@Override
	public String getText() {
		if(item != null){
			return item.getAction().getText().replace("&", "");
		} else {
			return Display.syncExec(new ResultRunnable<String>() {
				
				@Override
				public String run() {
					return menuItem.getText().replace("&", "");
					
				}
			});
		}
	}

	@Override
	public MenuItem getSWTWidget() {
		return menuItem;
	}
	
	@Override
	public boolean isEnabled() {
		if(menuItem != null){
			return WidgetHandler.getInstance().isEnabled(menuItem);
		}
		return ActionContributionItemHandler.getInstance().isEnabled(item);
	}
}
