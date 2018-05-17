/*******************************************************************************
 * Copyright (c) 2017 Red Hat, Inc and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Red Hat, Inc - initial API and implementation
 *******************************************************************************/
package org.eclipse.reddeer.requirements.server.apache.tomcat;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.eclipse.reddeer.common.logging.Logger;
import org.eclipse.reddeer.eclipse.wst.server.ui.wizard.NewServerWizard;
import org.eclipse.reddeer.eclipse.wst.server.ui.wizard.NewServerWizardPage;
import org.eclipse.reddeer.junit.requirement.ConfigurableRequirement;
import org.eclipse.reddeer.requirements.property.RequirementPropertyExpandor;
import org.eclipse.reddeer.requirements.server.AbstractServerRequirement;
import org.eclipse.reddeer.requirements.server.ServerRequirementState;
import org.eclipse.reddeer.requirements.server.apache.tomcat.ApacheTomcatServerRequirement.ApacheTomcatServer;
import org.eclipse.reddeer.swt.impl.text.DefaultText;

/**
 * 
 * @author Pavol Srna
 *
 */
public class ApacheTomcatServerRequirement extends AbstractServerRequirement
		implements ConfigurableRequirement<ApacheTomcatServerConfiguration, ApacheTomcatServer> {

	private static final Logger LOGGER = Logger.getLogger(ApacheTomcatServerRequirement.class);

	private ApacheTomcatServerConfiguration config;
	private ApacheTomcatServer server;

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE)
	public @interface ApacheTomcatServer {

		/**
		 * State.
		 *
		 * @return the server req state
		 */
		ServerRequirementState state()

		default ServerRequirementState.RUNNING;

		/**
		 * Cleanup.
		 *
		 * @return true, if successful
		 */
		boolean cleanup() default true;
	}

	@Override
	public void fulfill() {
		if (!isLastConfiguredServerPresent()) {
			LOGGER.info("Setup Apache Tomcat server");
			setupServerAdapter();
		}
		setupServerState(server.state());
	}

	@Override
	public String getServerName() {
		return "Apache Tomcat v"+ config.getVersion()+" Server at localhost";
	}

	@Override
	public String getRuntimeName() {
		return "Apache Tomcat v"+ config.getVersion()+" Runtime";
	}

	private void setupServerAdapter() {

		NewServerWizard swd = new NewServerWizard();
		swd.open();

		NewServerWizardPage swpage = new NewServerWizardPage(swd);

		swpage.selectType("Apache","Tomcat v"+config.getVersion()+" Server");
		swpage.setName(getServerName());
		swd.next();

		new DefaultText(0).setText(getRuntimeName());
		new DefaultText(1).setText(getRuntime());

		swd.finish();
	}

	/**
	 * Gets runtime name. If name is a property in configuration, then it is
	 * expanded to its real value. Otherwise value in configuration is returned.
	 * 
	 * @return runtime name, if it is property, then expanded, otherwise String
	 *         from configuration
	 */
	public String getRuntime() {
		return RequirementPropertyExpandor.getProperty(config.getRuntime());
	}

	@Override
	public void setDeclaration(ApacheTomcatServer server) {
		this.server = server;
	}

	@Override
	public Class<ApacheTomcatServerConfiguration> getConfigurationClass() {
		return ApacheTomcatServerConfiguration.class;
	}

	@Override
	public void setConfiguration(ApacheTomcatServerConfiguration config) {
		this.config = config;
	}

	@Override
	public void cleanUp() {
		if (server.cleanup()) {
			removeServerAndRuntime();
		}
	}

	@Override
	public ApacheTomcatServer getDeclaration() {
		return server;
	}

	@Override
	public ApacheTomcatServerConfiguration getConfiguration() {
		return config;
	}
}