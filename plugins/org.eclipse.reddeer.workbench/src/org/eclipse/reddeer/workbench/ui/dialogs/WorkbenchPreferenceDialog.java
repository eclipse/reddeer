/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Red Hat Inc. - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.workbench.ui.dialogs;

import org.eclipse.ui.dialogs.PreferencesUtil;
import org.eclipse.reddeer.common.logging.Logger;
import org.eclipse.reddeer.common.matcher.RegexMatcher;
import org.eclipse.reddeer.common.platform.RunningPlatform;
import org.eclipse.reddeer.common.util.Display;
import org.eclipse.reddeer.core.matcher.WithTextMatcher;
import org.eclipse.reddeer.swt.impl.shell.DefaultShell;
import org.eclipse.reddeer.workbench.impl.shell.WorkbenchShell;
import org.eclipse.reddeer.workbench.workbenchmenu.WorkbenchMenuPreferencesDialog;

/**
 * Workbench Preference Dialog implementation that is opened via shell menu
 * Window &gt; Preferences.
 *
 * @author Jiri Peterka
 * @author Radoslav Rabara
 * @since 0.6
 */
public class WorkbenchPreferenceDialog extends WorkbenchMenuPreferencesDialog {

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
				setShell(new DefaultShell(matcher));
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
