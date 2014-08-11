package org.jboss.reddeer.workbench.preference;

import org.eclipse.swt.widgets.Shell;
import org.jboss.reddeer.jface.preference.PreferencePage;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.swt.api.Menu;
import org.jboss.reddeer.swt.api.TreeItem;
import org.jboss.reddeer.swt.impl.menu.ShellMenu;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.swt.impl.tree.DefaultTreeItem;
import org.jboss.reddeer.swt.lookup.ShellLookup;
import org.jboss.reddeer.swt.util.Display;
import org.jboss.reddeer.swt.util.ResultRunnable;

/**
 * Represents a general preference page that is open via Window -> Preferences. Subclasses
 * should represent the concrete preference page.
 * 
 * @author Lucia Jelinkova
 * @since 0.6 
 */
public class WorkbenchPreferencePage extends PreferencePage {

	public static final String DIALOG_TITLE = "Preferences";

	protected final Logger log = Logger.getLogger(this.getClass());

	private String[] path;

	/**
	 * Constructor sets path to specific preference item. 
	 * @param path path in preference shell tree to specific preference
	 */
	public WorkbenchPreferencePage(String... path) {
		super();
		this.path = path;
	}

	/**
	 * Open preference shell and specific preference page in preference shell defined by path given in constructor.
	 * @deprecated since 0.6, WorkbenchPreferenceDialog#open should be used
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
}
