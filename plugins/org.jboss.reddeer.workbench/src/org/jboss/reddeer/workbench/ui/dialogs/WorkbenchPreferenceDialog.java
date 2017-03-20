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
import org.jboss.reddeer.common.matcher.RegexMatcher;
import org.jboss.reddeer.common.platform.RunningPlatform;
import org.jboss.reddeer.common.util.Display;
import org.jboss.reddeer.core.matcher.WithTextMatcher;
import org.jboss.reddeer.swt.impl.shell.DefaultShell;
import org.jboss.reddeer.workbench.impl.shell.WorkbenchShell;
import org.jboss.reddeer.workbench.topmenu.TopMenuPreferencesDialog;

/**
 * Workbench Preference Dialog implementation that is opened via shell menu
 * Window &gt; Preferences.
 *
 * @author Jiri Peterka
 * @author Radoslav Rabara
 * @since 0.6
 */
public class WorkbenchPreferenceDialog extends TopMenuPreferencesDialog {

	private final Logger log = Logger.getLogger(WorkbenchPreferenceDialog.class);
	
	public WorkbenchPreferenceDialog() {
		super(new WithTextMatcher(new RegexMatcher("Preferences.*")),"Window", "Preferences");
	}

	public void open() {
		if(!isOpen()){
			if (RunningPlatform.isOSX()) {
				log.info("Open Preferences directly on Mac OSX");
				new WorkbenchShell();
				handleMacMenu();
				setShell(new DefaultShell(getShellMatcher()));
			} else {
				super.open();
			}
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

	@Override
	public Class<?> getEclipseClass() {
		return org.eclipse.ui.internal.dialogs.WorkbenchPreferenceDialog.class;
	}
}
