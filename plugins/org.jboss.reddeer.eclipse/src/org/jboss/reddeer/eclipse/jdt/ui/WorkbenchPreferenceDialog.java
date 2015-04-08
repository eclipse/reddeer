package org.jboss.reddeer.eclipse.jdt.ui;

import org.eclipse.swt.widgets.Shell;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.swt.api.Menu;
import org.jboss.reddeer.swt.api.TreeItem;
import org.jboss.reddeer.swt.condition.CLabelWithTextIsAvailable;
import org.jboss.reddeer.core.condition.ShellWithTextIsActive;
import org.jboss.reddeer.core.condition.ShellWithTextIsAvailable;
import org.jboss.reddeer.core.handler.ShellHandler;
import org.jboss.reddeer.core.handler.WidgetHandler;
import org.jboss.reddeer.swt.impl.button.CancelButton;
import org.jboss.reddeer.swt.impl.button.OkButton;
import org.jboss.reddeer.swt.impl.clabel.DefaultCLabel;
import org.jboss.reddeer.swt.impl.menu.ShellMenu;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.swt.impl.tree.DefaultTreeItem;
import org.jboss.reddeer.core.lookup.ShellLookup;
import org.jboss.reddeer.common.wait.TimePeriod;
import org.jboss.reddeer.common.wait.WaitUntil;
import org.jboss.reddeer.common.wait.WaitWhile;
import org.jboss.reddeer.workbench.preference.WorkbenchPreferencePage;

/**
 * Workbench Preference Dialog implementation.
 *
 * @author Jiri Peterka
 * @author Radoslav Rabara
 * @since 0.6
 */
public class WorkbenchPreferenceDialog {

	public static final String DIALOG_TITLE = "Preferences";

	protected final Logger log = Logger.getLogger(this.getClass());

	/**
	 * Opens Preference Dialog.
	 */
	public void open() {
		// if preferences dialog is not open, open it
		log.info("Open Preferences dialog");

		if (isOpen()){
			log.debug("Preferences dialog was already opened.");
		}
		else{
			log.debug("Preferences dialog was not already opened. Opening via menu.");
			Menu menu = new ShellMenu("Window", "Preferences");
			menu.select();
		}
		
		new DefaultShell(DIALOG_TITLE);
	}

	/**
	 * Selects the specified workbench preference page <var>page</var>.
	 * @param page preference page to be opened
	 */
	public void select(WorkbenchPreferencePage page) {
		if (page == null) {
			throw new IllegalArgumentException("page can't be null");
		}
		select(page.getPath());
	}

	/**
	 * Selects preference page with the specified <var>path</var>.
	 * @param path path in preference shell tree to specific preference page
	 */
	public void select(String... path) {
		if (path == null) {
			throw new IllegalArgumentException("path can't be null");
		}
		if (path.length == 0) {
			throw new IllegalArgumentException("path can't be empty");
		}
		TreeItem t = new DefaultTreeItem(path);
		t.select();
		
		new WaitUntil(new CLabelWithTextIsAvailable(path[path.length-1]), TimePeriod.NORMAL, false);
	}

	/**
	 * Get name of the given preference page.
	 * 
	 * @return name of preference page
	 */
	public String getPageName() {
		DefaultCLabel cl = new DefaultCLabel();
		return cl.getText();
	}
	
	/**
	 * Presses Ok button on Preference Dialog. 
	 */
	public void ok() {
		final String parentShellText = WidgetHandler.getInstance().getText(
			ShellHandler.getInstance().getParentShell(new DefaultShell(DIALOG_TITLE).getSWTWidget()));
		OkButton ok = new OkButton();
		ok.click();
		new WaitWhile(new ShellWithTextIsAvailable(DIALOG_TITLE)); 
		new WaitUntil(new ShellWithTextIsActive(parentShellText));
	}

	/**
	 * Presses Cancel button on Preference Dialog. 
	 */
	public void cancel() {
		final String parentShellText = WidgetHandler.getInstance().getText(
			ShellHandler.getInstance().getParentShell(new DefaultShell(DIALOG_TITLE).getSWTWidget()));
		CancelButton cancel = new CancelButton();
		cancel.click();
		new WaitWhile(new ShellWithTextIsAvailable(DIALOG_TITLE));
		new WaitUntil(new ShellWithTextIsActive(parentShellText));
	}
	
	/**
	 * Checks if Workbench Preference dialog is opened.
	 * @return true if the dialog is opened, false otherwise
	 */
	public boolean isOpen() {
		Shell shell = ShellLookup.getInstance().getShell(DIALOG_TITLE,TimePeriod.SHORT);
		return (shell != null);		
	}
}
