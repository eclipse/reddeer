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
package org.eclipse.reddeer.requirements.preferences;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.eclipse.reddeer.direct.preferences.Preferences;
import org.eclipse.reddeer.common.logging.Logger;
import org.eclipse.reddeer.junit.requirement.AbstractRequirement;
import org.eclipse.reddeer.requirements.preferences.OomphRecorderRequirement.DisableOomphRecorder;

/**
 * Disables oomph recorder using Direct API.
 * 
 * @author odockal@redhat.com
 *
 */
public class OomphRecorderRequirement extends AbstractRequirement<DisableOomphRecorder> {

	private static final Logger log = Logger.getLogger(OomphRecorderRequirement.class);
	
	private static final String OOMPH_PLUGIN_ID = "org.eclipse.oomph.setup.ui";
	
	private static final String OOMPH_ENABLE_RECORDER_KEY = "enable.preference.recorder";
	
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE)
	public @interface DisableOomphRecorder {
		
		/**
		 * Sets whether Oomph recorder is disabled or not.
		 *
		 * @return false if disabled
		 */
		boolean enabled() default false;
	}
	
	@Override
	public void fulfill() {
		log.info(annotation.enabled() ? "Disabling" : "Enabling" + " Oomph preferences recorder");
		Preferences.set(OOMPH_PLUGIN_ID, OOMPH_ENABLE_RECORDER_KEY, Boolean.toString(annotation.enabled()));
	}

	@Override
	public void cleanUp() {
		// nothing to do
	}

}
