package org.jboss.reddeer.swt.impl.menu;

import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.swt.widgets.MenuItem;
import org.hamcrest.Matcher;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.core.handler.ActionContributionItemHandler;
import org.jboss.reddeer.core.handler.WidgetHandler;
import org.jboss.reddeer.core.matcher.WithMnemonicTextMatchers;
import org.jboss.reddeer.swt.api.Menu;

/**
 * Implementation for menu of view.
 * 
 * @author Rastislav Wagner, rhopp
 *
 */

public class ViewMenu extends AbstractMenu implements Menu {

	private static final Logger log = Logger.getLogger(ViewMenu.class);

	private ActionContributionItem item;

	/**
	 * Instantiates a new view menu.
	 *
	 * @param path the path
	 */
	public ViewMenu(String... path) {
		this(new WithMnemonicTextMatchers(path).getMatchers());
	}

	/**
	 * Instantiates a new view menu.
	 *
	 * @param matchers the matchers
	 */
	@SuppressWarnings("unchecked")
	public ViewMenu(Matcher<String>... matchers) {
		item = ml.lookFor(ml.getViewMenus(), matchers);
		this.matchers = matchers;
	}

	/* (non-Javadoc)
	 * @see org.jboss.reddeer.swt.impl.menu.AbstractMenu#select()
	 */
	public void select() {
		log.info("Select view menu with text " + getText());
		mh.select(item);
	}

	/* (non-Javadoc)
	 * @see org.jboss.reddeer.swt.impl.menu.AbstractMenu#isSelected()
	 */
	@Override
	public boolean isSelected() {
		return false;
	}

	/* (non-Javadoc)
	 * @see org.jboss.reddeer.swt.impl.menu.AbstractMenu#getText()
	 */
	@Override
	public String getText() {
		return item.getAction().getText().replace("&", "");
	}

	/* (non-Javadoc)
	 * @see org.jboss.reddeer.swt.api.Menu#getSWTWidget()
	 */
	public MenuItem getSWTWidget() {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.jboss.reddeer.swt.widgets.Widget#isEnabled()
	 */
	@Override
	public boolean isEnabled() {
		return ActionContributionItemHandler.getInstance().isEnabled(item);
	}
	
	/* (non-Javadoc)
	 * @see org.jboss.reddeer.swt.widgets.Widget#isDisposed()
	 */
	@Override
	public boolean isDisposed() {
		//actionItem doesnt have isDisposed method, so just return false
		return false;
	}

}
