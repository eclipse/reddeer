package org.jboss.reddeer.swt.impl.menu;

import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.ui.dialogs.PreferencesUtil;
import org.eclipse.ui.internal.dialogs.AboutDialog;
import org.hamcrest.Matcher;
import org.jboss.reddeer.swt.api.Menu;
import org.jboss.reddeer.swt.api.Shell;
import org.jboss.reddeer.swt.exception.SWTLayerException;
import org.jboss.reddeer.swt.lookup.ShellLookup;
import org.jboss.reddeer.swt.lookup.MenuLookup;
import org.jboss.reddeer.swt.matcher.WithMnemonicMatchers;
import org.jboss.reddeer.swt.util.Display;
import org.jboss.reddeer.swt.util.OS;
import org.jboss.reddeer.swt.util.Utils;

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
	 * 
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
		this(new WithMnemonicMatchers(path).getMatchers());
	}
	
	
	public ShellMenu(final Matcher<String>... matchers) {
		this.matchers = matchers;
		setMacOsMenuProperties();
		if (!isSubmenuOfMacEclipseMenu) {
			MenuLookup ml = new MenuLookup();
			menuItem = ml.lookFor(ml.getActiveShellTopMenuItems(), matchers);
		}
	}
	
	@Override
	public void select() {
		if (!isSubmenuOfMacEclipseMenu){
			MenuLookup ml = new MenuLookup();
			ml.select(menuItem);
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
			MenuItem i = ml.lookFor(ml.getActiveShellTopMenuItems(), matchers);
			String text = ml.getMenuItemText(i);
			return text;
		}
		else{
			return "&" + macEclipseMenuCommand.text;
		}
	}
	
	private void setMacOsMenuProperties(){
		isSubmenuOfMacEclipseMenu = false;
		macEclipseMenuCommand = null;
		if (Utils.isRunningOS(OS.MACOSX)&& 
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
}
