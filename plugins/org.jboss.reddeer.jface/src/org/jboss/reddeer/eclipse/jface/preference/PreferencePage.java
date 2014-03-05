package org.jboss.reddeer.eclipse.jface.preference;

import org.jboss.reddeer.junit.logging.Logger;
import org.eclipse.swt.widgets.Shell;
import org.jboss.reddeer.swt.api.Button;
import org.jboss.reddeer.swt.api.Menu;
import org.jboss.reddeer.swt.api.TreeItem;
import org.jboss.reddeer.swt.impl.button.PushButton;
import org.jboss.reddeer.swt.impl.clabel.DefaultCLabel;
import org.jboss.reddeer.swt.impl.menu.ShellMenu;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.swt.impl.tree.DefaultTreeItem;
import org.jboss.reddeer.swt.lookup.ShellLookup;
import org.jboss.reddeer.swt.util.Display;
import org.jboss.reddeer.swt.util.ResultRunnable;

/**
 * Represents a general preference page in the Preferences shell. Subclasses
 * should represent the concrete preference page.
 * 
 * @author Lucia Jelinkova
 * @since 0.5
 * 
 */
public abstract class PreferencePage {

	public static final String DIALOG_TITLE = "Preferences";

	private String[] path;
	protected final Logger log = Logger.getLogger(this.getClass());

	/**
	 * Constructor set path to specific preference item. 
	 * @param path path in preference shell tree to specific preference
	 */
	public PreferencePage(String... path) {
		this.path = path;
	}

	/**
	 * Open preference shell and specific preference page in preference shell defined by path given in constructor.
	 */
	public void open() {

		// if preferences dialog is not open, open it
		log.info("Open Preferences dialog");
		
		boolean openedShell = false;
		for (Shell s: ShellLookup.getInstance().getShells()) {
			final Shell shell = s;
			String text = Display.syncExec(new ResultRunnable<String>() {
				@Override
				public String run() {
					return shell.getText();
				}
			});
			if (text.equals(DIALOG_TITLE)) {
				log.debug("Preferences dialog was already opened.");
				openedShell = true;
			}
		}
		if (!openedShell) {
			log.debug("Preferences dialog was not already opened. Opening via menu.");
			Menu menu = new ShellMenu("Window", "Preferences");
			menu.select();
		}
		
		new DefaultShell(DIALOG_TITLE);
		TreeItem t = new DefaultTreeItem(path);
		t.select();
		
	}

	/**
	 * Get name of the given preference page.
	 * 
	 * @return name of preference page
	 */
	public String getName() {
		DefaultCLabel cl = new DefaultCLabel();
		return cl.getText();
	}

	/**
	 * Submit OK button and exit preference shell.
	 */
	public void ok() {
		Button b = new PushButton("OK");
		log.info("Close Preferences dialog");
		b.click();
	}

	/**
	 * Submit Cancel button and exit preference shell.
	 */
	public void cancel() {
		Button b = new PushButton("Cancel");
		log.info("Cancel Preferences dialog");
		b.click();
	}

	/**
	 * Apply preference page changes.
	 */
	public void apply() {
		Button b = new PushButton("Apply");
		log.info("Apply changes in Preferences dialog");
		b.click();
	}

	/**
	 * Restore default preference page settings.
	 */
	public void restoreDefaults() {
		Button b = new PushButton("Restore Defaults");
		log.info("Restore default values in Preferences dialog");
		b.click();
	}
	
}
