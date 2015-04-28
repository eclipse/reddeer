package org.jboss.reddeer.eclipse.jdt.ui;

import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.jface.preference.PreferenceDialog;
import org.jboss.reddeer.swt.api.Menu;
import org.jboss.reddeer.swt.impl.menu.ShellMenu;

/**
 * Workbench Preference Dialog implementation that is open
 * via Window -> Preferences
 *
 * @author Jiri Peterka
 * @author Radoslav Rabara
 * @since 0.6
 */
public class WorkbenchPreferenceDialog extends PreferenceDialog {

	public static final String DIALOG_TITLE = "Preferences";

	private final Logger log = Logger.getLogger(WorkbenchPreferenceDialog.class);

	@Override
	public String getTitle() {
		return DIALOG_TITLE;
	}

	@Override
	protected void openImpl() {
		log.info("Open Preferences by menu");
		Menu menu = new ShellMenu("Window", "Preferences");
		menu.select();
	}
}
