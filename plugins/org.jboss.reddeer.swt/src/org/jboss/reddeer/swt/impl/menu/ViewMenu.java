package org.jboss.reddeer.swt.impl.menu;

import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.swt.widgets.MenuItem;
import org.hamcrest.Matcher;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.swt.handler.ActionContributionItemHandler;
import org.jboss.reddeer.swt.matcher.WithMnemonicTextMatchers;

/**
 * ViewMenu implementation.
 * 
 * @author rhopp
 *
 */

public class ViewMenu extends AbstractMenu {

	private static final Logger LOGGER = Logger.getLogger(ToolbarMenu.class);

	private ActionContributionItem item;

	/**
	 * Constructor for ViewMenu with given path as array of strings.
	 * 
	 * @param path
	 *            path to desired menu item.
	 */

	public ViewMenu(String... path) {
		this(new WithMnemonicTextMatchers(path).getMatchers());
	}

	/**
	 * Constructor for ViewMenu with given path as array of string matchers.
	 * 
	 * @param matchers
	 *            path to desired menu item.
	 */

	public ViewMenu(Matcher<String>... matchers) {
		item = ml.lookFor(ml.getToolbarMenus(), matchers);
		this.matchers = matchers;
	}

	@Override
	public void select() {
		LOGGER.info("Select toolbar menu with text " + getText());
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

	/**
	 * Returns SWT widget representing this menu item.
	 * 
	 * @return SWT widget.
	 */

	public MenuItem getSWTWidget() {
		return null;
	}

	@Override
	public boolean isEnabled() {
		return ActionContributionItemHandler.getInstance().isEnabled(item);
	}

}
