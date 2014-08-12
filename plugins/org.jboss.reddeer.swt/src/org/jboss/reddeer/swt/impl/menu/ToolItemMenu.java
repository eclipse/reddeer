package org.jboss.reddeer.swt.impl.menu;

import org.eclipse.swt.widgets.MenuItem;
import org.hamcrest.Matcher;
import org.jboss.reddeer.swt.api.ToolItem;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.swt.handler.ToolItemHandler;
import org.jboss.reddeer.swt.matcher.WithMnemonicTextMatchers;

/**
 * This class represents drop down menu for ToolItems.
 * 
 * @author rhopp
 *
 */

public class ToolItemMenu extends AbstractMenu {

	protected ToolItemHandler tih = ToolItemHandler.getInstance();

	/**
	 * Constructor for desired menu for given ToolItem.
	 * 
	 * @param item
	 *            ToolItem with SWT.DROP_DOWN style.
	 * @param path
	 *            Path to desired menu.
	 */
	public ToolItemMenu(ToolItem item, String... path) {
		this(item, new WithMnemonicTextMatchers(path).getMatchers());
	}

	/**
	 * Constructor for desired menu for given ToolItem.
	 * 
	 * @param item
	 *            ToolItem with SWT.DROP_DOWN style.
	 * @param path
	 *            Path to desired menu.
	 */
	public ToolItemMenu(ToolItem item, Matcher<String>... path) {
		if (!tih.isDropDown(item.getSWTWidget())) {
			throw new SWTLayerException(
					"Given ToolItem isn't of style SWT.DROP_DOWN");
		}
		menuItem = ml.lookFor(ml.getToolItemMenuItems(item.getSWTWidget()),
				path);
	}

	@Override
	public MenuItem getSWTWidget() {
		return menuItem;
	}

	@Override
	public boolean isEnabled() {
		return mh.isEnabled(menuItem);
	}

	@Override
	public void select() {
		mh.select(menuItem);
	}

	@Override
	public boolean isSelected() {
		return false;
	}

	@Override
	public String getText() {
		return mh.getMenuItemText(menuItem);
	}
}
