/*******************************************************************************
 * Copyright (c) 2022 Red Hat, Inc and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Red Hat, Inc - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.requirements.test.preferences;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.lang.annotation.Annotation;

import org.eclipse.reddeer.direct.preferences.Preferences;
import org.eclipse.reddeer.junit.runner.RedDeerSuite;
import org.eclipse.reddeer.requirements.preferences.OomphRecorderRequirement;
import org.eclipse.reddeer.requirements.preferences.OomphRecorderRequirement.DisableOomphRecorder;
import org.eclipse.reddeer.swt.impl.toolbar.DefaultToolBar;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(RedDeerSuite.class)
public class OomphRecorderRequirementTest {
	
	private static final String OOMPH_PLUGIN_ID = "org.eclipse.oomph.setup.ui";
	private static final String OOMPH_ENABLE_RECORDER_KEY = "enable.preference.recorder";
	
	private OomphRecorderRequirement requirementEnabled;
	private OomphRecorderRequirement requirementDisabled;
	
	@Before
	public void setupRequirement() {
		requirementEnabled = new OomphRecorderRequirement();
		requirementEnabled.setDeclaration(createDisableOomphRecorderInstance(true));
		requirementDisabled = new OomphRecorderRequirement();
		requirementDisabled.setDeclaration(createDisableOomphRecorderInstance(false));
	}
	
	public DefaultToolBar getPreferencesToolBar() {
		return new DefaultToolBar();
	}
	
	@Test
	public void testEnableOomphRecorder() {
		requirementEnabled.fulfill();
		String actualValue = Preferences.get(OOMPH_PLUGIN_ID, OOMPH_ENABLE_RECORDER_KEY);
		assertTrue(Boolean.valueOf(actualValue));
	}
	
	@Test
	public void testDisableOomphRecorder() {
		requirementDisabled.fulfill();
		String actualValue = Preferences.get(OOMPH_PLUGIN_ID, OOMPH_ENABLE_RECORDER_KEY);
		assertFalse(Boolean.valueOf(actualValue));
	}
	
	private DisableOomphRecorder createDisableOomphRecorderInstance(boolean enabled) {
		return new DisableOomphRecorder() {
			
			@Override
			public Class<? extends Annotation> annotationType() {
				return DisableOomphRecorder.class;
			}
			
			@Override
			public boolean enabled() {
				return enabled;
			}
		};
	}

	
}
