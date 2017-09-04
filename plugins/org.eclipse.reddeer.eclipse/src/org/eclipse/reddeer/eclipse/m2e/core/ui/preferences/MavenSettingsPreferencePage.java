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
package org.eclipse.reddeer.eclipse.m2e.core.ui.preferences;

import org.eclipse.swt.widgets.Control;
import org.eclipse.reddeer.common.util.Display;
import org.eclipse.reddeer.common.util.ResultRunnable;
import org.eclipse.reddeer.common.wait.TimePeriod;
import org.eclipse.reddeer.common.wait.WaitUntil;
import org.eclipse.reddeer.common.wait.WaitWhile;
import org.eclipse.reddeer.core.reference.ReferencedComposite;
import org.eclipse.reddeer.jface.preference.PreferencePage;
import org.eclipse.reddeer.swt.impl.button.PushButton;
import org.eclipse.reddeer.swt.impl.text.DefaultText;
import org.eclipse.reddeer.workbench.core.condition.JobIsRunning;

/**
 * Class representing "Maven" &gt; "User Settings" preference page.
 * 
 * @author rhopp
 *
 */

public class MavenSettingsPreferencePage extends PreferencePage {

	private static final String UPDATE_SETTINGS = "Update Settings";
	private static final String REINDEX = "Reindex";

	/**
	 * Construct the preference page with "Maven" &gt; "User Settings".
	 */
	public MavenSettingsPreferencePage(ReferencedComposite referencedComposite) {
		super(referencedComposite, new String[] {"Maven", "User Settings"});
	}

	/**
	 * Pushes "Update Settings" button and waits for all jobs to be finished
	 * (max. 1 minute)
	 * 
	 */
	public MavenSettingsPreferencePage updateSettings() {
		new PushButton(this, UPDATE_SETTINGS).click();
		new WaitUntil(new JobIsRunning(), TimePeriod.LONG);
		return this;
	}

	/**
	 * Sets path to users setting.xml file
	 * 
	 * @param path Path to settings.xml file
	 */
	public MavenSettingsPreferencePage setUserSettingsLocation(String path) {
		getSettingsXMLTextWidget().setText(path);
		return this;
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
	public MavenSettingsPreferencePage reindex() {
		new PushButton(this, REINDEX).click();
		new WaitWhile(new JobIsRunning(), TimePeriod.VERY_LONG);
		return this;
	}

	
	/**
	 * Retrieves first DefaultText relative to parent of "Update Settings" push button.
	 * 
	 * @return default text of settings xml
	 */
	private DefaultText getSettingsXMLTextWidget() {
		final PushButton button = new PushButton(this, UPDATE_SETTINGS);
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
