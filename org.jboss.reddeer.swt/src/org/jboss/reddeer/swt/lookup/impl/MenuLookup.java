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
import org.jboss.reddeer.swt.exception.SWTLayerException;
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

	private Logger log = Logger.getLogger(this.getClass());

	/**
	 * Look for MenuItem matching matchers starting topLevel menuItems
	 * @param topItems top level MenuItem[]
	 * @param matchers menuitem text matchers
	 * @return final MenuItem
	 */
	public MenuItem lookFor(MenuItem[] topItems, Matcher<String>... matchers) {		
		MenuItem lastMenuItem = getMatchingMenuPath(topItems, matchers);
		if (lastMenuItem == null) throw new SWTLayerException("");
		return lastMenuItem;
	}

	/**
	 * Selects (click) for MenuItem matching matchers starting topLevel menuItems
	 * @param topItems top level MenuItem[]
	 * @param matchers menuitem text matchers
	 * @return final MenuItem
	 */
	public void select(MenuItem[] topItems, Matcher<String>... matchers) {
		MenuItem lastMenuItem = getMatchingMenuPath(topItems, matchers);
		if (lastMenuItem == null) {
			throw new SWTLayerException("Menu not found");
		}
		clickMenuItem(lastMenuItem);
	}


	/**
	 * Returns top level menuitems from focused controls
	 * @return
	 */
	public MenuItem[] getTopMenuMenuItemsFromFocus() {

		final Control control  = getFocusControl();
		MenuItem[] items = null;
		final Menu menu = getControlMenu(control);
		
		items = Display.syncExec(new ResultRunnable<MenuItem[]>() {
			@Override
			public MenuItem[] run() {
				sendShowUI(menu);				
				MenuItem[] items = menu.getItems();
				return items;
			}
		});

		if (items == null) {
			throw new SWTLayerException(
					"Could not find top menu items, menu doesn't exist or wrong focus");
		}

		return items;
	}
	
	/**
	 * Returns menuitems from active shell menubar
	 * @return
	 */
	public MenuItem[] getActiveShellTopMenuItems() {
		ShellLookup sl = new ShellLookup();
		Shell activeShell = sl.getActiveShell();
		return getMenuBarItems(activeShell);
	}

	
	/**
	 * Returns menuitem text
	 * @param i
	 * @return
	 */
	public String getMenuItemText(final MenuItem i) {
		String text = Display.syncExec(new ResultRunnable<String>() {
			@Override
			public String run() {
				return i.getText();
			}
		});
		log.debug("Queried MenuItem text:\"" + text + "\"");
		return text;
	}
	
	/**
	 * Returns menubar items
	 * @param s
	 * @return
	 */
	private MenuItem[] getMenuBarItems(final Shell s) {

		MenuItem[] items = Display.syncExec(new ResultRunnable<MenuItem[]>() {

			@Override
			public MenuItem[] run() {
				Menu menu = s.getMenuBar();
				if (menu == null){
					throw new SWTLayerException("Cannot find a menu bar of shell " + s.getText());
				}
				MenuItem[] items = menu.getItems();
				return items;
			}
		});
		return items;
	}

	/**
	 * Returns Menu of the given control
	 * @param c
	 * @return
	 */
	private Menu getControlMenu(final Control c) {

		Menu menu = Display.syncExec(new ResultRunnable<Menu>() {

			@Override
			public Menu run() {
				Menu m = c.getMenu();
				
				return m;
			}
		});

		if (menu == null) {
			throw new SWTLayerException(
					"No menu");
		}

		return menu;	
	}
	
	/**
	 * Goes through menus path and returns matching menu
	 * @param topItems
	 * @param matchers
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
							log.debug("Item match:" + normalized);
							currentItem = i;
							currentMenu = i.getMenu();
							break;
						} 
					}
					if (m != matchers[matchers.length-1]) {
						currentMenu = currentItem.getMenu();
						sendShowUI(currentMenu);
						menuItems = currentMenu.getItems();
					} 
				}
				return currentItem;
			}
		});
		return i;
	}


	/**
	 * Sends SWT.Show on widget
	 * @param widget
	 */
	private void sendShowUI(Widget widget) {
		widget.notifyListeners(SWT.Show, new Event());
	}
		

	/**
	 * Hide menu
	 * 
	 * @param menu
	 * @param recur
	 */
	@SuppressWarnings("unused")
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
	 * Check weather or not menuitem is enabled
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
					new SWTLayerException("Menu item is not enabled");
				}
			}
		});
	}

	/**
	 * Return control with focus
	 * 
	 * @return control with focus
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