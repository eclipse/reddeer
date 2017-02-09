/******************************************************************************* 
 * Copyright (c) 2016 Red Hat, Inc. 
 * Distributed under license by Red Hat, Inc. All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 ******************************************************************************/
package org.jboss.reddeer.workbench.ui.dialogs;

import org.eclipse.ui.dialogs.PreferencesUtil;
import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.common.platform.RunningPlatform;
import org.jboss.reddeer.common.util.Display;
import org.jboss.reddeer.jface.preference.PreferenceDialog;
import org.jboss.reddeer.swt.api.Menu;
import org.jboss.reddeer.swt.impl.menu.ShellMenu;

/**
 * Workbench Preference Dialog implementation that is opened via shell menu
 * Window &gt; Preferences.
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
		if (RunningPlatform.isOSX()) {
			log.info("Open Preferences directly on Mac OSX");
			handleMacMenu();
		} else {
			log.info("Open Preferences by menu");
			Menu menu = new ShellMenu("Window", "Preferences");
			menu.select();
		}
	}

	private void handleMacMenu() {
		Display.asyncExec(new Runnable() {
			@Override
			public void run() {
				org.eclipse.jface.preference.PreferenceDialog dialog = PreferencesUtil.createPreferenceDialogOn(null,
						null, null, null);
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
}
