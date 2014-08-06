package org.jboss.reddeer.swt.impl.menu;

import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.swt.widgets.MenuItem;
import org.hamcrest.Matcher;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.swt.api.Menu;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.swt.handler.ActionContributionItemHandler;
import org.jboss.reddeer.swt.handler.WidgetHandler;
import org.jboss.reddeer.swt.matcher.WithMnemonicTextMatchers;

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
	private ActionContributionItem actionItem;
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

		menuItem = ml.lookFor(ml.getTopMenuMenuItemsFromFocus(),matchers);
		if(menuItem == null){
			log.info("No menu item found, looking for contribution item");
			actionItem = ml.lookFor(ml.getMenuContributionItems(), matchers);
			if (actionItem == null){
				throw new SWTLayerException("Contribution item not found");
			}
		}
		this.matchers = matchers;
		
	}	
	
	@Override
	public void select() {
		log.info("Select context menu item with text " + getText());
		if(menuItem != null){
			mh.select(menuItem);
		} else {
			mh.select(actionItem);
		}
	}
	
	@Override
	public boolean isSelected() {
		if(menuItem != null){
			return mh.isSelected(menuItem);
		} else {
			return false;
		}
	}
	
	
	@Override
	public String getText() {
		if(actionItem != null){
			return actionItem.getAction().getText().replace("&", "");
		} else {
			String ret = mh.getMenuItemText(menuItem).replace("&", "");
			return ret;
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
		return ActionContributionItemHandler.getInstance().isEnabled(actionItem);
	}
}
