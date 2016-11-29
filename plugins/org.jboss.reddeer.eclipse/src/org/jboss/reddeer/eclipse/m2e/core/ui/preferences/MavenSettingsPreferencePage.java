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
package org.jboss.reddeer.eclipse.m2e.core.ui.preferences;

import org.eclipse.swt.widgets.Control;
import org.jboss.reddeer.common.util.Display;
import org.jboss.reddeer.common.util.ResultRunnable;
import org.jboss.reddeer.common.wait.TimePeriod;
import org.jboss.reddeer.common.wait.WaitUntil;
import org.jboss.reddeer.common.wait.WaitWhile;
import org.jboss.reddeer.core.reference.ReferencedComposite;
import org.jboss.reddeer.jface.preference.PreferencePage;
import org.jboss.reddeer.swt.impl.button.PushButton;
import org.jboss.reddeer.swt.impl.text.DefaultText;
import org.jboss.reddeer.workbench.core.condition.JobIsRunning;

/**
 * Class representing "Maven->User Settings" preference page.
 * 
 * @author rhopp
 *
 */

public class MavenSettingsPreferencePage extends PreferencePage {

	private static final String UPDATE_SETTINGS = "Update Settings";
	private static final String REINDEX = "Reindex";

	/**
	 * Construct the preference page with "Maven" > "User Settings".
	 */
	public MavenSettingsPreferencePage() {
		super(new String[] {"Maven", "User Settings"});
	}

	/**
	 * Pushes "Update Settings" button and waits for all jobs to be finished
	 * (max. 1 minute)
	 * 
	 */

	public void updateSettings() {
		new PushButton(UPDATE_SETTINGS).click();
		new WaitUntil(new JobIsRunning(), TimePeriod.LONG);
	}

	/**
	 * Sets path to users setting.xml file
	 * 
	 * @param path Path to settings.xml file
	 */
	
	public void setUserSettingsLocation(String path) {
		getSettingsXMLTextWidget().setText(path);
	}

	/**
	 * Gets the user settings location.
	 *
	 * @return content of User Settings location
	 */
	
	public String getUserSettingsLocation() {
		return getSettingsXMLTextWidget().getText();
	}

	/**
	 * Presses "Reindex" button and waits until all jobs are finished (max. 5
	 * minutes)
	 * 
	 */

	public void reindex() {
		new PushButton(REINDEX).click();
		new WaitWhile(new JobIsRunning(), TimePeriod.VERY_LONG);
	}

	
	/**
	 * Retrieves first DefaultText relative to parent of "Update Settings" push button.
	 * 
	 * @return
	 */
	
	private DefaultText getSettingsXMLTextWidget() {
		final PushButton button = new PushButton(UPDATE_SETTINGS);
		return Display.syncExec(new ResultRunnable<DefaultText>() {

			@Override
			public DefaultText run() {
				return new DefaultText(new ReferencedComposite() {

					@Override
					public Control getControl() {
						return button.getSWTWidget().getParent();
					}
				}, 1);
			}
		});
	}
}
