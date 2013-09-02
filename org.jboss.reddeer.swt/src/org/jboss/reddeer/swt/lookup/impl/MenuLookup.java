package org.jboss.reddeer.swt.lookup.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotView;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.internal.WorkbenchPartReference;
import org.hamcrest.Matcher;
import org.jboss.reddeer.swt.condition.ShellWithTextIsActive;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.swt.handler.WidgetHandler;
import org.jboss.reddeer.swt.util.Bot;
import org.jboss.reddeer.swt.util.Display;
import org.jboss.reddeer.swt.util.ResultRunnable;
import org.jboss.reddeer.swt.wait.AbstractWait;
import org.jboss.reddeer.swt.wait.TimePeriod;
import org.jboss.reddeer.swt.wait.WaitWhile;

/**
 * Menu lookup provides menu and contextmenu routines for menuitems lookup Works
 * also with dynamic menus (inspired by bot.ext helper)
 * 
 * @author Jiri Peterka
 * @author Rastislav Wagner
 * 
 */
public class MenuLookup {

	private Logger log = Logger.getLogger(this.getClass());
	
	public List<IContributionItem> getToolbarMenus(){
		SWTBotView view = Bot.get().activeView();
		IWorkbenchPart obj = ((WorkbenchPartReference) view.getReference()).getPart(false);
		List<IContributionItem> menuContributionItems = new ArrayList<IContributionItem>();
		IMenuManager m = ((IViewSite) obj.getSite()).getActionBars().getMenuManager();
		if (m instanceof MenuManager) {
			menuContributionItems.addAll(Arrays.asList(((MenuManager) m).getItems()));
		}
		if(menuContributionItems.isEmpty()){
			throw new SWTLayerException("No Menu found in " +view.getTitle());
		}
		return menuContributionItems;
	}
	
	/**
	 * Look for ActionContributionItem matching matchers
	 * @param cintItems items which will be matched with matchers
	 * @param matchers menuitem text matchers
	 * @return final ActionContibutionItem
	 */
	public ActionContributionItem lookFor(final List<IContributionItem> contItems, final Matcher<String>... matchers) {	
		ActionContributionItem contItem = Display.syncExec(new ResultRunnable<ActionContributionItem>(){

			@Override
			public ActionContributionItem run() {
				ActionContributionItem currentItem = null;
				List<IContributionItem> currentMenuContributionItems = contItems;
				for (Matcher<String> m : matchers) {
					currentItem = null;
					for (IContributionItem i : currentMenuContributionItems) {
						if(i instanceof ActionContributionItem){
							String normalized = ((ActionContributionItem)i).getAction().getText().replace("&", "");
							log.debug("Found item:" + normalized);
							if (m.matches(normalized)) {
								log.info("Item match:" + normalized);
								currentItem =(ActionContributionItem)i;
								break;
							} 
						} else if(i instanceof MenuManager){
							String normalized =((MenuManager)i).getMenuText().replace("&", "");
							log.debug("Found Menu Manager:" + normalized);
							if (m.matches(normalized)) {
								log.debug("Menu Manager match:" + normalized);
								currentMenuContributionItems = Arrays.asList(((MenuManager) i).getItems());
							}
						}
					}
			
				}
				return currentItem;
			}
		});
		return contItem;
	}

	
	/**
	 * Returns ContributionItems from focused control
	 * Use if menu can contain dynamic menu from e4
	 */
	public List<IContributionItem> getMenuContributionItems() {
		List<IContributionItem> contItems = new ArrayList<IContributionItem>();
		final Control control  = WidgetLookup.getInstance().getFocusControl();
		final Menu menu = getControlMenu(control);
		
		contItems = Display.syncExec(new ResultRunnable<List<IContributionItem>>() {
			@Override
			public List<IContributionItem> run() {
				List<IContributionItem> contItemsRun = new ArrayList<IContributionItem>();
				sendHide(menu, true);
				sendShowUI(menu);
				if(menu.getData() != null && menu.getData() instanceof MenuManager){
					contItemsRun.addAll(Arrays.asList(((MenuManager)menu.getData()).getItems()));
					log.info("Menu manager found");
				} else {
					log.info("Menu manager not found");
				}
	
				return contItemsRun;
			}
		});
		return contItems;
	}
	
	

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
	 * Selects (click) for MenuItem
	 * @param item to click
	 */
	public void select(final MenuItem item) {
		
		Boolean enabled = Display.syncExec(new ResultRunnable<Boolean>() {
			
			@Override
			public Boolean run(){
				return isMenuEnabled(item);
			}
		});
		
		if(!enabled){
			throw new SWTLayerException("Menu item is not enabled");
		} else {
			Display.asyncExec(new Runnable() {

				@Override
				public void run() {
					final Event event = new Event();
					event.time = (int) System.currentTimeMillis();
					event.widget = item;
					event.display = item.getDisplay();
					event.type = SWT.Selection;

					log.info("Click on menu item: " + item.getText());
					item.notifyListeners(SWT.Selection, event);
				}
			});
		}
	}
	/**
	 * Selects (click) for ActionContributionItem
	 * @param item to click
	 */
	public void select(final ActionContributionItem item){
		String actionNormalized = item.getAction().getText().replace("&", "");
		if(!item.isEnabled()){
			throw new SWTLayerException("Menu item " +actionNormalized+" is not enabled");
		} else if(!item.isVisible()){
			throw new SWTLayerException("Menu item " +actionNormalized+" is not visible");
		} else{
			log.info("Click on contribution item: " + actionNormalized);
			Display.asyncExec(new Runnable() {
				@Override
				public void run() {
					item.getAction().run();
				}
			});
		}
	}


	/**
	 * Returns top level menuitems from focused controls
	 * Does not work with dynamic menus from e4 @see MenuLookup.getMenuContributionItems()
	 *
	 * @return
	 */
	public MenuItem[] getTopMenuMenuItemsFromFocus() {

		final Control control  = WidgetLookup.getInstance().getFocusControl();
		MenuItem[] items = null;
		final Menu menu = getControlMenu(control);
		
		items = Display.syncExec(new ResultRunnable<MenuItem[]>() {
			@Override
			public MenuItem[] run() {
				sendHide(menu, true);
				sendShowUI(menu);				
				return menu.getItems();
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
		int i= 0;
		while(i<5){
			if(activeShell != null){
				break;
			}
			i++;
			AbstractWait.sleep(100);
			activeShell = sl.getActiveShell();
		}
		if(activeShell == null){
			throw new SWTLayerException("Cannot find menu bar because there's no active shell");
		}
		String activeShellText = WidgetHandler.getInstance().getText(activeShell);
		MenuItem[] result = null;
		try{
			result = getMenuBarItems(activeShell);	
		} catch (SWTLayerException swtle) {
			// there is a chance that some non expected shell was opened
			// e.g. Progress Dialog
			new WaitWhile(new ShellWithTextIsActive(activeShellText),TimePeriod.NORMAL,false);
			activeShell = sl.getActiveShell();
			if (!activeShellText.equals(WidgetHandler.getInstance().getText(activeShell))){
				result = getMenuBarItems(activeShell);
			}
			else{
				throw swtle;
			}
		}		
		return result;		
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
				log.info("Getting Menu Bar of shell " + s.getText());
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
							log.info("Item match:" + normalized);
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

}
