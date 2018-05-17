/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Red Hat, Inc - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.swt.impl.menu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.reddeer.core.handler.MenuHandler;
import org.eclipse.reddeer.core.lookup.MenuItemLookup;
import org.eclipse.reddeer.core.matcher.WithMnemonicTextMatcher;
import org.eclipse.reddeer.core.matcher.WithMnemonicTextMatchers;
import org.eclipse.reddeer.core.reference.ReferencedComposite;
import org.eclipse.reddeer.swt.api.MenuItem;
import org.eclipse.reddeer.swt.widgets.AbstractWidget;
import org.eclipse.swt.widgets.Menu;
import org.hamcrest.Matcher;

/**
 * Abstract class for all Menu implementations
 * @author rawagner
 *
 */
public abstract class AbstractMenu extends AbstractWidget<Menu> implements org.eclipse.reddeer.swt.api.Menu{
	
	public AbstractMenu(Menu swtMenu) {
		super(swtMenu);
	}

	protected AbstractMenu(Class<Menu> widgetClass, ReferencedComposite refComposite, int index,
			Matcher<?>[] matchers) {
		super(widgetClass, refComposite, index, matchers);
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean hasItem(String... path) {
		Matcher<String>[] matchers = new Matcher[path.length];
		for (int i = 0; i < path.length; i++) {
			matchers[i] = new WithMnemonicTextMatcher(path[i]);
		}
		return hasItem(matchers);
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean hasItem(Matcher<String>... matchers) {
		if (matchers == null || matchers.length == 0) {
			throw new IllegalArgumentException("Path must contain at least one item");
		}
		Matcher<String> matcher = matchers[0];
		for (MenuItem menuItem : getItems()) {
			if (matcher.matches(menuItem.getText())) {
				if (matchers.length > 1) {
					return menuItem.getMenu().hasItem(Arrays.copyOfRange(matchers, 1, matchers.length));
				}
				return true;
			}
		}
		return false;
	}

	@Override
	public List<MenuItem> getItems() {
		List<org.eclipse.swt.widgets.MenuItem> swtItems =  MenuHandler.getInstance().getItems(swtWidget);
		if(swtItems == null) {
			return new ArrayList<>();
		}
		return swtItems.stream().map(DefaultMenuItem::new).collect(Collectors.toList());
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public MenuItem getItem(Matcher<String>... matchers) {
		org.eclipse.swt.widgets.MenuItem swtItem = MenuItemLookup.getInstance().lookFor(getSWTWidget(),
				matchers);
		return new DefaultMenuItem(swtItem);
	}
	
	@Override
	public MenuItem getItem(String... path) {
		return getItem(new WithMnemonicTextMatchers(path).getMatchers());
	}
	
	@Override
	public boolean isEnabled() {
		return MenuHandler.getInstance().isEnabled(swtWidget);
	}
	
	@Override
	public boolean isVisible() {
		return MenuHandler.getInstance().isVisible(swtWidget);
	}
	
	@Override
	public org.eclipse.reddeer.swt.api.Menu getParentMenu() {
		Menu parentMenu =  MenuHandler.getInstance().getParentMenu(swtWidget);
		if(parentMenu == null) {
			return null;
		}
		return new DefaultMenu(parentMenu);
	}

}
