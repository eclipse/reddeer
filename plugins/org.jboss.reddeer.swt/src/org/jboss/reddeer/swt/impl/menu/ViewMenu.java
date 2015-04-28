package org.jboss.reddeer.swt.impl.menu;

import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.swt.widgets.MenuItem;
import org.hamcrest.Matcher;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.core.handler.ActionContributionItemHandler;
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

	public ViewMenu(String... path) {
		this(new WithMnemonicTextMatchers(path).getMatchers());
	}

	@SuppressWarnings("unchecked")
	public ViewMenu(Matcher<String>... matchers) {
		item = ml.lookFor(ml.getViewMenus(), matchers);
		this.matchers = matchers;
	}

	public void select() {
		log.info("Select view menu with text " + getText());
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

	public MenuItem getSWTWidget() {
		return null;
	}

	@Override
	public boolean isEnabled() {
		return ActionContributionItemHandler.getInstance().isEnabled(item);
	}

}
