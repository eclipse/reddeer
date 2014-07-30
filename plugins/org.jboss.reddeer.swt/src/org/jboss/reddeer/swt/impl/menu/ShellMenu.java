package org.jboss.reddeer.swt.impl.menu;

import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.ui.dialogs.PreferencesUtil;
import org.eclipse.ui.internal.dialogs.AboutDialog;
import org.hamcrest.Matcher;
import org.jboss.reddeer.common.platform.RunningPlatform;
import org.jboss.reddeer.swt.api.Menu;
import org.jboss.reddeer.swt.api.Shell;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.swt.handler.MenuHandler;
import org.jboss.reddeer.swt.handler.WidgetHandler;
import org.jboss.reddeer.swt.lookup.MenuLookup;
import org.jboss.reddeer.swt.lookup.ShellLookup;
import org.jboss.reddeer.swt.matcher.WithMnemonicTextMatchers;
import org.jboss.reddeer.swt.util.Display;

/**
 * Shell menu implementation
 * @author Jiri Peterka
 *
 */
@SuppressWarnings("restriction")
public class ShellMenu extends AbstractMenu implements Menu {
	
	private MenuItem menuItem;
	private boolean isSubmenuOfMacEclipseMenu = false;
	private MacEclipseMenuCommand macEclipseMenuCommand = null;
	
	private enum MacEclipseMenuCommand {
		PREFERENCES("Preferences"), ABOUT("About");
		public String text;

		MacEclipseMenuCommand(String text) {
			this.text = text;
		}
		
	}
	/**
	 * Create Menu instance from menu of given shell
	 * @deprecated - should not be used at all, will be removed during 0.6
	 * @param shell
	 */
	public ShellMenu(Shell shell) {
	}
	
	/**
	 * Uses WithMnemonicMatcher to match menu item label. It means that all ampersands
	 * and shortcuts within menu item label are ignored when searching for menu
	 * @param path
	 */
	public ShellMenu(final String... path) {
		this(new WithMnemonicTextMatchers(path).getMatchers());
	}
	
	
	public ShellMenu(final Matcher<String>... matchers) {
		this.matchers = matchers;
		setMacOsMenuProperties();
		if (!isSubmenuOfMacEclipseMenu) {
			getSWTWidget();
		}
	}
	
	@Override
	public void select() {
		if (!isSubmenuOfMacEclipseMenu){
			MenuHandler.getInstance().select(getSWTWidget());
		} else {
			if (macEclipseMenuCommand.equals(MacEclipseMenuCommand.PREFERENCES)){
				openPreferencesDialog();
			}else if (macEclipseMenuCommand.equals(MacEclipseMenuCommand.ABOUT)){
				openAboutDialog();
			} else {
				throw new SWTLayerException("Unsupported Mac Eclispe menu command: " + macEclipseMenuCommand);
			}
		}
	}
	
	@Override
	public boolean isSelected() {
		if(getSWTWidget() != null){
			return MenuHandler.getInstance().isSelected(getSWTWidget());
		} else {
			return false;
		}
	}
	

	private void openAboutDialog() {
		Display.asyncExec(new Runnable() {
			@Override
			public void run() {
				 new AboutDialog(ShellLookup.getInstance().getActiveShell()).open();
			}
		});
		Display.syncExec(new Runnable() {
			@Override
			public void run() {
			}
		});
	}

	private void openPreferencesDialog() {
		Display.asyncExec(new Runnable() {
			@Override
			public void run() {
				 PreferenceDialog dialog = PreferencesUtil.createPreferenceDialogOn(null, null, null, null);
				 dialog.open();
			}
		});
		Display.syncExec(new Runnable() {
			@Override
			public void run() {
				// do nothing just process UI events
			}
		});
	}

	@Override
	public String getText() {
		if (!isSubmenuOfMacEclipseMenu){
			MenuLookup ml = new MenuLookup();
			String text = ml.getMenuItemText(getSWTWidget());
			return text;
		}
		else{
			return "&" + macEclipseMenuCommand.text;
		}
	}
	
	private void setMacOsMenuProperties(){
		isSubmenuOfMacEclipseMenu = false;
		macEclipseMenuCommand = null;
		if (RunningPlatform.isOSX() && 
			matchers.length == 2){
			if (matchers[0].matches("Window") && matchers[1].matches(MacEclipseMenuCommand.PREFERENCES.text)){
				isSubmenuOfMacEclipseMenu = true;
				macEclipseMenuCommand = MacEclipseMenuCommand.PREFERENCES;
			}else if(matchers[0].matches("Help") && matchers[1].matches(MacEclipseMenuCommand.ABOUT.text)){
				isSubmenuOfMacEclipseMenu = true;
				macEclipseMenuCommand = MacEclipseMenuCommand.ABOUT;				
			}
		}
	}
	
	public MenuItem getSWTWidget(){
		if (menuItem == null || menuItem.isDisposed()){
			MenuLookup ml = new MenuLookup();
			menuItem = ml.lookFor(ml.getActiveShellTopMenuItems(), matchers);
		}
		return menuItem;
	}
	
	@Override
	public boolean isEnabled() {
		return WidgetHandler.getInstance().isEnabled(getSWTWidget());
	}
}
