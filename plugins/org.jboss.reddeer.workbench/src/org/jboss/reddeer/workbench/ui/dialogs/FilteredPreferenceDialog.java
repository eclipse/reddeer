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

import static org.hamcrest.MatcherAssert.assertThat;

import org.jboss.reddeer.common.logging.Logger;
import org.jboss.reddeer.jface.preference.PreferenceDialog;
/**
 * Filtered Preference Dialog implementation that has to bve opened
 * before it's methods are called
 *
 * @author Vlado Pakan
 * @since 1.0
 */
public class FilteredPreferenceDialog extends PreferenceDialog {

	public static final String DIALOG_TITLE = "Preferences (Filtered)";

	private final Logger log = Logger.getLogger(FilteredPreferenceDialog.class);

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
		log.info("Open Filtered Preferences");
		// actually only activates already opened dialog
		assertThat("Filetered preferences dialog has to opened already", isOpen());
	}
}
