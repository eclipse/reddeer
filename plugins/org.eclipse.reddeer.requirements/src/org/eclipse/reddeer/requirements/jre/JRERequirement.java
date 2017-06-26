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
package org.eclipse.reddeer.requirements.jre;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.eclipse.reddeer.common.logging.Logger;
import org.eclipse.reddeer.eclipse.jdt.debug.ui.jres.JREsPreferencePage;
import org.eclipse.reddeer.junit.requirement.ConfigurableRequirement;
import org.eclipse.reddeer.requirements.jre.JRERequirement.JRE;
import org.eclipse.reddeer.requirements.property.RequirementPropertyExpandor;
import org.eclipse.reddeer.workbench.ui.dialogs.WorkbenchPreferenceDialog;

/**
 * Requirement for specific JRE. This requirement will add new JRE to eclipse
 * using Preferences &gt; Java &gt; Installed JRE's
 * 
 * @author rhopp
 *
 */
public class JRERequirement implements ConfigurableRequirement<JREConfiguration, JRE> {

	private Logger log = Logger.getLogger(JRERequirement.class);
	private JRE jre;
	private JREConfiguration configuration;

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE)
	public @interface JRE {

		/**
		 * Value.
		 *
		 * @return the double
		 */
		double value() default -1;

		/**
		 * Cleanup.
		 *
		 * @return true, if successful
		 */
		boolean cleanup() default false;
	}

	/**
	 * Adds new JRE using Preferences &gt; Java &gt; Installed JRE's, Add JRE
	 * wizard.
	 */
	@Override
	public void fulfill() {
		log.info("JRE Requirement fulfill performed");
		log.debug("Configuration (name,version,path): %s, %s, %s", configuration.getName(), configuration.getVersion(),
				configuration.getPath());

		WorkbenchPreferenceDialog dialog = new WorkbenchPreferenceDialog();
		dialog.open();
		JREsPreferencePage page = new JREsPreferencePage(dialog);
		dialog.select(page);
		page.addJRE(getPath(), configuration.getName());
		dialog.ok();
	}

	@Override
	public void setDeclaration(JRE declaration) {
		this.jre = declaration;

	}

	@Override
	public void cleanUp() {
		if (jre.cleanup()) {
			WorkbenchPreferenceDialog dialog = new WorkbenchPreferenceDialog();
			dialog.open();
			JREsPreferencePage page = new JREsPreferencePage(dialog);
			dialog.select(page);
			page.deleteJRE(configuration.getName());
			dialog.ok();
		}
	}

	@Override
	public Class<JREConfiguration> getConfigurationClass() {
		return JREConfiguration.class;
	}

	@Override
	public void setConfiguration(JREConfiguration config) {
		this.configuration = config;

	}

	@Override
	public JRE getDeclaration() {
		return jre;
	}

	/**
	 * Gets JRE path from configuration. It can be either a specific value or a
	 * property. Property would be expanded to its real value.
	 * 
	 * @return path to JRE
	 */
	public String getPath() {
		return RequirementPropertyExpandor.getProperty(configuration.getPath());
	}

	@Override
	public JREConfiguration getConfiguration() {
		return configuration;
	}

	@Override
	public String getDescription() {
		return configuration.getId();
	}
}
