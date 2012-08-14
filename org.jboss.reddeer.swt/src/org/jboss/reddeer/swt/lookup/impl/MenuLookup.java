package org.jboss.reddeer.swt.lookup.impl;

import org.apache.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Widget;
import org.hamcrest.Matcher;
import org.jboss.reddeer.swt.exception.WidgetNotAvailableException;
import org.jboss.reddeer.swt.util.Display;
import org.jboss.reddeer.swt.util.ResultRunnable;

/**
 * Menu lookup provides menu and contextmenu routines for menuitems lookup Works
 * also with dynamic menus (inspired by bot.ext helper)
 * 
 * @author Jiri Peterka
 * 
 */
public class MenuLookup {

	Logger log = Logger.getLogger(this.getClass());

	/**
	 * Look for menu with given path matching pattern
	 * 
	 * @param path
	 */
	public MenuItem lookFor(MenuItem[] topItems, Matcher<String>... matchers) {		
		Control c = getFocusControl();
		MenuItem lastMenuItem = getMatchingMenuPath(topItems, matchers);
		// Menu lastMenu = getMenuFromMenuItem(lastMenuItem);
		// sendHide(lastMenu, true);
		setFocusControl(c);
		if (lastMenuItem == null) throw new WidgetNotAvailableException("");
		return lastMenuItem;
	}

	public void select(MenuItem[] topItems, Matcher<String>... matchers) {
		MenuItem lastMenuItem = getMatchingMenuPath(topItems, matchers);
		if (lastMenuItem == null) {
			throw new WidgetNotAvailableException("Menu not found");
		}
		clickMenuItem(lastMenuItem);
	}

	public MenuItem[] getMenuBarItems(final Shell s) {

		MenuItem[] items = Display.syncExec(new ResultRunnable<MenuItem[]>() {

			@Override
			public MenuItem[] run() {
				MenuItem[] items = s.getMenuBar().getItems();
				return items;
			}
		});
		return items;
	}

	/**
	 * Return control with focus
	 * 
	 * @return
	 */
	private void setFocusControl(final Control c) {
		Display.syncExec(new Runnable() {

			@Override
			public void run() {
				c.setFocus();
			}

		});
	}

	/**
	 * Return top menuitem widget
	 * 
	 * @param control
	 * @return
	 */
	public Menu getTopMenuWidget(final Control control) {

		Menu topMenu = null;
		topMenu = Display.syncExec(new ResultRunnable<Menu>() {

			@Override
			public Menu run() {
				return control.getMenu();
			}
		});

		if (topMenu == null) {
			throw new WidgetNotAvailableException(
					"Could not find top menu, menu doesn't exist or wrong focus");
		}

		return topMenu;
	}
	
	/**
	 * Return top menuitem widget
	 * 
	 * @param control
	 * @return
	 */
	public MenuItem[] getTopMenuMenuItemsFromFocus() {

		final Control control  = getFocusControl();
		MenuItem[] items = null;
		final Menu menu = getControlMenu(control);
		
		items = Display.syncExec(new ResultRunnable<MenuItem[]>() {
			@Override
			public MenuItem[] run() {
				sendShow(menu);				
				MenuItem[] items = menu.getItems();
				return items;
			}
		});

		if (items == null) {
			throw new WidgetNotAvailableException(
					"Could not find top menu items, menu doesn't exist or wrong focus");
		}

		return items;
	}

	
	private Menu getControlMenu(final Control c) {

		Menu menu = Display.syncExec(new ResultRunnable<Menu>() {

			@Override
			public Menu run() {
				Menu m = c.getMenu();
				
				return m;
			}
		});

		if (menu == null) {
			throw new WidgetNotAvailableException(
					"No menu");
		}

		return menu;	
	}
	
	
	
	public MenuItem[] getActiveShellTopMenuItems() {
		ShellLookup sl = new ShellLookup();
		Shell activeShell = sl.getActiveShell();
		return getMenuBarItems(activeShell);
	}


	/**
	 * Goes through given path through menus
	 * 
	 * @param topMenu
	 * @param path
	 * @return
	 */
	private MenuItem getMatchingMenuPath(final MenuItem[] topItems,
			final Matcher<String>... matchers) {

		MenuItem i = Display.syncExec(new ResultRunnable<MenuItem>() {

			@Override
			public MenuItem run() {

				Menu currentMenu = null;
				MenuItem currentItem = null;;
				MenuItem[] menuItems = topItems;
				for (Matcher<String> m : matchers) {
					currentItem = null;
					for (MenuItem i : menuItems) {
						String normalized = i.getText().replace("&", "");
						log.debug("Found menu:" + normalized);
						if (m.matches(normalized)) {
							log.info("Item match:" + normalized);
							currentItem = i;
							currentMenu = i.getMenu();
							break;
						} 
					}
					if (m != matchers[matchers.length-1]) {
						currentMenu = currentItem.getMenu();
						sendShow(currentMenu);
						menuItems = currentMenu.getItems();
					} 
				}
				return currentItem;
			}
		});
		return i;
	}

	/**
	 * Sends SWT.Show Event on a Widget
	 * 
	 * @param menuItem
	 * @return
	 */
	private void sendShow(Widget widget) {
		widget.notifyListeners(SWT.Show, new Event());
	}

	private Menu getMenuFromMenuItem(final MenuItem menuItem) {
		Menu menu = Display.syncExec(new ResultRunnable<Menu>() {

			@Override
			public Menu run() {
				return menuItem.getParent();
			}
		});
		return menu;
	}

	/**
	 * Hide menu
	 * 
	 * @param menu
	 * @param recur
	 */
	private void sendHide(final Menu menu, final boolean recur) {
		Display.syncExec(new Runnable() {

			@Override
			public void run() {

				if (menu != null) {
					menu.notifyListeners(SWT.Hide, new Event());
					if (recur) {
						if (menu.getParentMenu() != null) {
							sendHide(menu.getParentMenu(), recur);
						} else {
							menu.setVisible(false);
						}
					}
				}
			}

		});

	}

	/**
	 * Check wheather or not menuitem is enabled
	 * 
	 * @param menuItem
	 */
	private boolean isMenuEnabled(final MenuItem menuItem) {
		boolean enabled = Display.syncExec(new ResultRunnable<Boolean>() {
			public Boolean run() {
				return menuItem.getEnabled();
			}
		});

		return enabled;
	}

	/**
	 * Click on menuitem when enable, throw an exception when not
	 * 
	 * @param menuItem
	 */
	private void clickMenuItem(final MenuItem menuItem) {
		Display.asyncExec(new Runnable() {

			@Override
			public void run() {

				if (isMenuEnabled(menuItem)) {
					final Event event = new Event();
					event.time = (int) System.currentTimeMillis();
					event.widget = menuItem;
					event.display = menuItem.getDisplay();
					event.type = SWT.Selection;

					log.info("Click on menu item: " + menuItem.getText());
					menuItem.notifyListeners(SWT.Selection, event);

				} else {
					new WidgetNotAvailableException("Menu item is not enabled");
				}
			}
		});
	}

	/**
	 * Return control with focus
	 * 
	 * @return
	 */
	private Control getFocusControl() {
		Control c = Display.syncExec(new ResultRunnable<Control>() {

			@Override
			public Control run() {
				Control focusControl = Display.getDisplay().getFocusControl();
				return focusControl;
			}

		});
		return c;
	}

}