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

import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.jface.preference.PreferenceDialog;
import org.jboss.reddeer.swt.api.Menu;
import org.jboss.reddeer.swt.impl.menu.ShellMenu;

/**
 * Workbench Preference Dialog implementation that is opened
 * via shell menu Window -> Preferences.
 *
 * @author Jiri Peterka
 * @author Radoslav Rabara
 * @since 0.6
 */
public class WorkbenchPreferenceDialog extends PreferenceDialog {

	public static final String DIALOG_TITLE = "Preferences";

	private final Logger log = Logger.getLogger(WorkbenchPreferenceDialog.class);

	/* (non-Javadoc)
	 * @see org.jboss.reddeer.jface.preference.PreferenceDialog#getTitle()
	 */
	@Override
	public String getTitle() {
		return DIALOG_TITLE;
	}

	/* (non-Javadoc)
	 * @see org.jboss.reddeer.jface.preference.PreferenceDialog#openImpl()
	 */
	@Override
	protected void openImpl() {
		log.info("Open Preferences by menu");
		Menu menu = new ShellMenu("Window", "Preferences");
		menu.select();
	}
}
