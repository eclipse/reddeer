package org.jboss.reddeer.swt.handler;

import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.MenuItem;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.swt.util.Display;
import org.jboss.reddeer.swt.util.ResultRunnable;

/**
 * Contains methods for handling UI operations on {@link MenuItem} widgets.
 * 
 * @author Lucia Jelinkova
 *
 */
public class MenuHandler {
	
	private static MenuHandler instance;

	private MenuHandler() {

	}

	/**
	 * Gets instance of MenuHandler.
	 * 
	 * @return instance of MenuHandler
	 */
	public static MenuHandler getInstance() {
		if (instance == null) {
			instance = new MenuHandler();
		}
		return instance;
	}
	
	public boolean isSelected(final MenuItem item){
		return Display.syncExec(new ResultRunnable<Boolean>() {

			@Override
			public Boolean run() {
				if(((item.getStyle() & SWT.RADIO) != 0) || ((item.getStyle() & SWT.CHECK) != 0)){
					return item.getSelection();
				}
				return false;
			}
		});
	}
	
	/**
	 * Selects (click) for MenuItem
	 * @param item to click
	 */
	public void select(final MenuItem item) {
		
		if(!isMenuEnabled(item)){
			throw new SWTLayerException("Menu item is not enabled");
		} else {
			Display.syncExec(new Runnable() {

				@Override
				public void run() {
					if(((item.getStyle() & SWT.RADIO) != 0) || ((item.getStyle() & SWT.CHECK) != 0)){
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
			Display.asyncExec(new Runnable() {
				@Override
				public void run() {
					item.getAction().run();
				}
			});
		}
	}
	
	/**
	 * 
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
