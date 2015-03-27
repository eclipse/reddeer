package org.jboss.reddeer.swt.handler;

import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.MenuItem;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.core.exception.SWTLayerException;
import org.jboss.reddeer.core.util.Display;
import org.jboss.reddeer.core.util.ResultRunnable;

/**
 * Contains methods for handling UI operations on {@link MenuItem} widgets.
 * 
 * @author Jiri Peterka
 *
 */
public class MenuHandler {
	private static final Logger log = Logger.getLogger(MenuHandler.class);

	private static MenuHandler instance = null;
	
	private MenuHandler() { }
	
	/**
	 * Returns instance of MenuHandler.
	 * @return instance of MenuHandler.
	 */
	public static MenuHandler getInstance() {
		if (instance == null) {
			instance = new MenuHandler();
		}
		return instance;
	}
	
	/**
	 * Gets selection state of MenuItem.
	 * @param item given menuitem
	 * @return true if menuItem is selected
	 */
	public boolean isSelected(final MenuItem item) {
		return Display.syncExec(new ResultRunnable<Boolean>() {

			@Override
			public Boolean run() {
				if (((item.getStyle() & SWT.RADIO) != 0)
						|| ((item.getStyle() & SWT.CHECK) != 0)) {
					return item.getSelection();
				}
				return false;
			}
		});
	}

	/**
	 * Selects (click) for ActionContributionItem.
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
	 * Selects (click) for MenuItem.
	 * @param item given item which is going to be selected (clicked)
	 */
	public void select(final MenuItem item) {

		Boolean enabled = Display.syncExec(new ResultRunnable<Boolean>() {

			@Override
			public Boolean run() {
				return isMenuEnabled(item);
			}
		});

		if (!enabled) {
			throw new SWTLayerException("Menu item is not enabled");
		} else {
			Display.syncExec(new Runnable() {

				@Override
				public void run() {
					if (((item.getStyle() & SWT.RADIO) != 0)
							|| ((item.getStyle() & SWT.CHECK) != 0)) {
						item.setSelection(!item.getSelection());
					}
				}
			});

			Display.asyncExec(new Runnable() {

				@Override
				public void run() {
					final Event event = new Event();
					event.time = (int) System.currentTimeMillis();
					event.widget = item;
					event.item = item;
					event.display = item.getDisplay();
					event.type = SWT.Selection;

					log.info("Click on menu item: " + item.getText());
					item.notifyListeners(SWT.Selection, event);
				}
			});
			Display.syncExec(new Runnable() {
				@Override
				public void run() {

				}
			});
		}
	}
	
	/**
	 * Check weather or not menuitem is enabled.
	 * @param menuItem given MenuItem 
	 * @return true if MenuItem is enabled
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
	 * Returns menuitem text.
	 * @param i given MenuItem
	 * @return text string of given MenuItem
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
	 * Returns true if given MenuItem is enabled.
	 * 
	 * @param item
	 *            given MenuItem
	 * @return true if given MenuItem is enabled.
	 */

	public boolean isEnabled(final MenuItem item) {
		return Display.syncExec(new ResultRunnable<Boolean>() {
			@Override
			public Boolean run() {
				return item.isEnabled();
			}
		});
	}

}
